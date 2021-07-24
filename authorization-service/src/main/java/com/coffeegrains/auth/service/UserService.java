package com.coffeegrains.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.coffeegrains.auth.constants.ErrorCodes;
import com.coffeegrains.auth.entity.User;
import com.coffeegrains.auth.repository.users.UserRepository;
import com.coffeegrains.serviceutils.response.ResponseEntityContainer;
import com.coffeegrains.serviceutils.response.ResponseEntityError;
import com.coffeegrains.serviceutils.util.DateUtil;
import com.coffeegrains.serviceutils.util.EmailUtil;
import com.coffeegrains.serviceutils.util.EncryptionUtil;

@Component
public class UserService {

	@Value("${verification.passcode.ascii.limit.min}")
	private Integer verificationPasscodeLimitMin;

	@Value("${verification.passcode.ascii.limit.max}")
	private Integer verificationPasscodeLimitMax;

	@Value("${verification.passcode.ascii.exclusions}")
	private Integer[] verificationPasscodeExclusions;
	
	@Value("${verification.passcode.length}")
	private Integer verificationPasscodeLength;

	@Value("${encryption.secret.key.string}")
	private String encryptionSecretKeyString;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GenderService genderService;
	
	@Autowired
	private EncryptionUtil encryptionUtil;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private DateUtil dateUtil;

	/**
	 * Register user from UI.
	 */
	public ResponseEntity<ResponseEntityContainer> registerUserUI(User user) {
		
		ResponseEntity<ResponseEntityContainer> response = registerUserUIInputValidator(user);
		if (response != null) {
			return response;
		}

		String encryptedPassword = encryptionUtil.encrypt(user.getPassword(), encryptionSecretKeyString);
		
		user.setPassword(encryptedPassword);
		user.setVerificationPasscode(generateVerificationPasscode());
		
		int insertedRecords = userRepository.createUser(user);
		
		if (insertedRecords < 1) {
			List<ErrorCodes> errors = new ArrayList<>();
			errors.add(ErrorCodes.DEFAULT);
			response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(errors),
					new HttpHeaders(),
					HttpStatus.BAD_REQUEST);
			return response;
		}
		
		if (insertedRecords == 1) {
			// TODO: Send email to registered email account to inform the verification passcode
		}
		
		ResponseEntityContainer responseContainer = new ResponseEntityContainer(user);
		ResponseEntity<ResponseEntityContainer> responseEntity = new ResponseEntity<ResponseEntityContainer>(responseContainer, new HttpHeaders(), HttpStatus.OK);
		return responseEntity;
	}
	
	private ResponseEntity<ResponseEntityContainer> registerUserUIInputValidator(User user) {
		
		List<ResponseEntityError> errors = new ArrayList<>();
		
		if (StringUtils.isNullOrEmpty(user.getName())
				|| StringUtils.isWhitespaceOrEmpty(user.getName())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_MISSING_NAME));
		}
		if (StringUtils.isNullOrEmpty(user.getLastName())
				|| StringUtils.isWhitespaceOrEmpty(user.getLastName())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_MISSING_LAST_NAME));
		}
		if (StringUtils.isNullOrEmpty(user.getEmail())
				|| StringUtils.isWhitespaceOrEmpty(user.getEmail())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_MISSING_EMAIL));
		} else if (!emailUtil.validateEmail(user.getEmail().trim().toLowerCase())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_INVALID_EMAIL));
		} else if (userRepository.emailExists(user.getEmail())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_EMAIL_ALREADY_EXISTS));
		}
		if (StringUtils.isNullOrEmpty(user.getPassword())
				|| StringUtils.isWhitespaceOrEmpty(user.getPassword())) {
			errors.add(new ResponseEntityError(ErrorCodes.REG_USR_MISSING_PASSWORD));
		}
		if (!StringUtils.isNullOrEmpty(user.getBirthDate())
				&& !StringUtils.isWhitespaceOrEmpty(user.getBirthDate())) {
			if (!dateUtil.validateDate(user.getBirthDate().trim())) {
				errors.add(new ResponseEntityError(ErrorCodes.REG_USR_INVALID_BIRTHDATE));
			}
		}
		if (user.getGender() != null && user.getGender().getId() > 0) {
			if (!genderService.genderExists(user.getGender().getId())) {
				errors.add(new ResponseEntityError(ErrorCodes.REG_USR_INVALID_GENDER));
			}
		}
		
		if (!errors.isEmpty()) {
			ResponseEntity<ResponseEntityContainer> response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(null, errors),
					new HttpHeaders(),
					HttpStatus.BAD_REQUEST);
			return response;
		}
		
		return null;
	}
	
	/**
	 * This method generates a random verification passcode for email verification of the account.
	 */
	private String generateVerificationPasscode() {
		Random random = new Random();
		StringBuilder verificationPasscode = new StringBuilder();
		
		while (verificationPasscode.toString().length() < verificationPasscodeLength) {
			int randomNumber = random.nextInt((verificationPasscodeLimitMax - verificationPasscodeLimitMin) + 1) + verificationPasscodeLimitMin;
			boolean addNumber = true;
			for (int exclusionIter : verificationPasscodeExclusions) {
				if (randomNumber == exclusionIter) {
					addNumber = false;
					break;
				}
			}
			if (addNumber) {
				verificationPasscode.append((char) randomNumber);
			}
		}
		return verificationPasscode.toString();
	}

	/**
	 * Logins with email.
	 */
	public ResponseEntity<ResponseEntityContainer> loginWithEmail(User user) {
		
		ResponseEntity<ResponseEntityContainer> response = loginWithEmailInputValidator(user);
		if (response != null) {
			return response;
		}
		
		User dbEntityUser = userRepository.findUser(user.getEmail(), encryptionUtil.encrypt(user.getPassword(), encryptionSecretKeyString));
		
		if (dbEntityUser == null) {
			response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(new ResponseEntityError(ErrorCodes.LOGIN_BAD_CREDENTIALS)),
					new HttpHeaders(),
					HttpStatus.NOT_FOUND);
			return response;
		}
		if (dbEntityUser.getVerified() != 1) {
			response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(new ResponseEntityError(ErrorCodes.LOGIN_ACCOUNT_NOT_VERIFIED)),
					new HttpHeaders(),
					HttpStatus.NOT_FOUND);
			return response;
		}
		if (dbEntityUser.getActive() != 1) {
			response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(new ResponseEntityError(ErrorCodes.LOGIN_ACCOUNT_DISABLED)),
					new HttpHeaders(),
					HttpStatus.NOT_FOUND);
			return response;
		}
		
		ResponseEntityContainer responseContainer = new ResponseEntityContainer(user);
		ResponseEntity<ResponseEntityContainer> responseEntity = new ResponseEntity<ResponseEntityContainer>(responseContainer, new HttpHeaders(), HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * Validates data before login with email.
	 */
	private ResponseEntity<ResponseEntityContainer> loginWithEmailInputValidator(User user) {
		
		List<ResponseEntityError> errors = new ArrayList<>();
		
		if (StringUtils.isNullOrEmpty(user.getEmail())
				|| StringUtils.isWhitespaceOrEmpty(user.getEmail())) {
			errors.add(new ResponseEntityError(ErrorCodes.LOGIN_MISSING_EMAIL));
		}
		if (StringUtils.isNullOrEmpty(user.getPassword())
				|| StringUtils.isWhitespaceOrEmpty(user.getPassword())) {
			errors.add(new ResponseEntityError(ErrorCodes.LOGIN_MISSING_PASSWORD));
		}
		
		if (!errors.isEmpty()) {
			ResponseEntity<ResponseEntityContainer> response = new ResponseEntity<ResponseEntityContainer>(
					new ResponseEntityContainer(null, errors),
					new HttpHeaders(),
					HttpStatus.BAD_REQUEST);
			return response;
		}
		return null;
	}

}
