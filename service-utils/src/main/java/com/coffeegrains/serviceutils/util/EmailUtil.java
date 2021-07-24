package com.coffeegrains.serviceutils.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	/**
	 * Method to validate an email integrity.
	 * 
	 * @param email
	 * @return
	 */
	public boolean validateEmail(final String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	/**
	 * Method to send an email.
	 * 
	 * @param destinataries
	 * @param subject
	 * @param bodyHtml
	 * @param attachments
	 * @return
	 */
	public boolean sendEmail(String[] destinataries, String subject, String bodyHtml, File[] attachments) {
		// TODO
		return true;
	}

}
