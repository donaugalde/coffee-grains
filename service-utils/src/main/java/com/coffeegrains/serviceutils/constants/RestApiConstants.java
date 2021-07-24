package com.coffeegrains.serviceutils.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class RestApiConstants {

	/**
	 * This time format is valid for MYSQL database.
	 * 
	 * Format is YYYY-MM-DD_HH24:MI:SS
	 * 
	 * e.g.: 1987-05-27_08:05:32
	 */
	public static final String DATABASE_STANDARD_DATE_FORMAT = "%Y-%m-%d_%H:%i:%s";
	
	public static final String DATABASE_LOGICAL_NULL = " ";
	
	public static final String DATABASE_DEFAULT_DATE = "1900-01-01_00:00:00";

	public static final Locale STANDARD_LOCALE = Locale.US;

	/**
	 * This is the simple date format object with same format as STANDARD_DATETIME_FORMAT.
	 */
	public static final DateFormat STANDARD_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", RestApiConstants.STANDARD_LOCALE);

}
