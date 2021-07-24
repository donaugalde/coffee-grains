package com.coffeegrains.serviceutils.util;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.coffeegrains.serviceutils.constants.RestApiConstants;

@Component
public class DateUtil {

	/**
	 * Method to validate a date with format defined in RestApiConstants.STANDARD_SIMPLE_DATE_FORMAT.
	 * 
	 * @param email
	 * @return
	 */
	public boolean validateDate(final String date) {
		try {
			return RestApiConstants.STANDARD_SIMPLE_DATE_FORMAT.format(RestApiConstants.STANDARD_SIMPLE_DATE_FORMAT.parse(date)).equals(date);
		} catch (ParseException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
