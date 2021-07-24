package com.coffeegrains.auth.constants;

import com.coffeegrains.serviceutils.response.ResponseEntityError;
import com.coffeegrains.serviceutils.response.ResponseEntityErrorEnumerable;

public enum ErrorCodes implements ResponseEntityErrorEnumerable {
	
	// Register user
	REG_USR_MISSING_NAME("reg_usr_missing_name", "Missing name"),
	REG_USR_MISSING_LAST_NAME("reg_usr_missing_last_name", "Missing last name"),
	REG_USR_MISSING_EMAIL("reg_usr_missing_email", "Missing email"),
	REG_USR_INVALID_EMAIL("reg_usr_missing_email", "Invalid email"),
	REG_USR_EMAIL_ALREADY_EXISTS("reg_usr_email_already_exists", "Email already exists"),
	REG_USR_MISSING_PASSWORD("reg_usr_missing_email", "Missing password"),
	REG_USR_INVALID_BIRTHDATE("reg_usr_invalid_birthdate", "Invalid birthdate"),
	REG_USR_INVALID_GENDER("reg_usr_invalid_gender", "Invalid gender"),
	
	// Login errors
	LOGIN_MISSING_EMAIL("login_missing_email", "The email is mandatory"),
	LOGIN_MISSING_PASSWORD("login_missing_email", "The password is mandatory"),
	LOGIN_ACCOUNT_DISABLED("login_account_disabled", "The account is disabled"),
	LOGIN_ACCOUNT_NOT_VERIFIED("login_account_not_verified", "The account has not been verified"),
	LOGIN_BAD_CREDENTIALS("login_bad_credentials", "Bad credentials"),
	
	// Default
	DEFAULT("default_error", "An error has ocurred while trying to process this request. Please try again later");
	
	private String errorCode;
	private String description;
	
	ErrorCodes(String errorCode, String description) {
		this.errorCode = errorCode;
		this.description = description;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public ResponseEntityError getResponseEntityError() {
		return new ResponseEntityError(this.getErrorCode(), this.getDescription());
	}

}
