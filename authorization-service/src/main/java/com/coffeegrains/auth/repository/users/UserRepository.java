package com.coffeegrains.auth.repository.users;

import java.util.List;
import java.util.Map;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coffeegrains.auth.entity.Gender;
import com.coffeegrains.auth.entity.User;
import com.coffeegrains.auth.service.GenderService;
import com.coffeegrains.serviceutils.constants.RestApiConstants;

@Repository
public class UserRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	@Qualifier(value = "authJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GenderService genderService;
	
	public boolean emailExists(String email) {
		
		Long totalRows = 0L;
		
		Object[] parameters = new Object[] {
				email.trim().toLowerCase()
		};
		
		StringBuilder query = new StringBuilder()
			.append("select count(*) as total_rows from users where trim(lower(email)) = ?");
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query.toString(), parameters);
		
		for (Map<String, Object> result : resultList) {
			totalRows = Long.valueOf(String.valueOf(result.get("total_rows")));
			break;
		}
		return totalRows > 0L;
	}
	
	public int createUser(User user) {
		
		try {
			Object[] parameters = null;
			StringBuilder query = new StringBuilder();
			
			parameters = new Object[] {
				user.getName(),
				user.getLastName(),
				user.getEmail(),
				!StringUtils.isNullOrEmpty(user.getPassword()) && !StringUtils.isWhitespaceOrEmpty(user.getPassword()) ? user.getPassword() : RestApiConstants.DATABASE_LOGICAL_NULL,
				!StringUtils.isNullOrEmpty(user.getBirthDate()) && !StringUtils.isWhitespaceOrEmpty(user.getBirthDate()) ? user.getBirthDate() : RestApiConstants.DATABASE_DEFAULT_DATE,
				RestApiConstants.DATABASE_STANDARD_DATE_FORMAT,
				user.getGender() != null && user.getGender().getId() > 0 ? user.getGender().getId() : 0,
				user.getVerificationPasscode()
			};
			
			query
				.append("insert into users (name, last_name, email, password, birth_date, gender, active, verified, verification_passcode, ")
				.append("verification_date, last_login_date, created_date, last_updated_date, comments) ")
				.append("values (?, ?, ?, ?, str_to_date(?, ?), ?, 1, 0, ?, null, null, now(), now(), null)");
			
			int insertedRecords = jdbcTemplate.update(query.toString(), parameters);
			
			return insertedRecords;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
		
	}
	
	public User findUser(String email, String password) {
		User user = null;
		
		Object[] parameters = new Object[] {
			RestApiConstants.DATABASE_STANDARD_DATE_FORMAT,
			RestApiConstants.DATABASE_STANDARD_DATE_FORMAT,
			RestApiConstants.DATABASE_STANDARD_DATE_FORMAT,
			RestApiConstants.DATABASE_STANDARD_DATE_FORMAT,
			email.trim().toLowerCase(),
			password
		};
		
		StringBuilder query = new StringBuilder()
			.append("select u.id, u.name, u.last_name, u.email, null as password, date_format(u.birth_date, ?) as birth_date, gender, u.active, ")
			.append("u.verified, null as verification_passcode, null as verification_date, date_format(u.last_login_date, ?) as last_login_date, ")
			.append("date_format(u.created_date, ?) as created_date, date_format(last_updated_date, ?) as last_updated_date, comments ")
			.append("from users u")
			.append("left join genders g on u.gender = g.id ")
			.append("where trim(lower(u.email)) = trim(lower(?)) and u.password = ?");
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query.toString(), parameters);
		
		for (Map<String, Object> result : resultList) {
			
			Gender gender = null;
			if (result.get("gender") != null && String.valueOf(result.get("gender")) != null && Integer.valueOf(String.valueOf(result.get("gender"))) > 0) {
				gender = genderService.getGenderById(Integer.valueOf(String.valueOf(result.get("gender"))));
			}
			
			user = new User(
					Long.valueOf(String.valueOf(result.get("id"))),
					String.valueOf(result.get("name")),
					String.valueOf(result.get("last_name")),
					String.valueOf(result.get("email")),
					null,
					String.valueOf(result.get("birth_date")),
					gender,
					Integer.valueOf(String.valueOf(result.get("active"))),
					Integer.valueOf(String.valueOf(result.get("verified"))),
					null,
					null,
					String.valueOf(result.get("last_login_date")),
					String.valueOf(result.get("created_date")),
					String.valueOf(result.get("last_updated_date")),
					String.valueOf(result.get("comments")));
		}
		
		return user;
	}
	
}
