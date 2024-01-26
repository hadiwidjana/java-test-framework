package JavaDemo.Integrations.Utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static JavaDemo.Integrations.Assertion.Assertions.assertTrue;


public class DateAndTime 
{

	public static String getDateTime(int dateOffset,String dateFormat) {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.DATE, dateOffset);
		SimpleDateFormat DF = new SimpleDateFormat(dateFormat);
		Date date = new Date(current.getTimeInMillis());
		return DF.format(date);
	}

	//convert from eproc API
	public static String convertDate(String date, String formatBefore, String formatAfter) {
		DateTimeFormatter dateFormatter
				= DateTimeFormatter.ofPattern(formatBefore);
		DateTimeFormatter dateFormatterNew
				= DateTimeFormatter.ofPattern(formatAfter);
		// string to LocalDateTime
		LocalDateTime ldateTime = LocalDateTime.parse(date, dateFormatter);
		return dateFormatterNew.format(ldateTime.plusHours(7));
	}
	public static void assertDateFormatSlash(String date){

		String day = date.substring(0,2);
		String dayMonthSeparator = date.substring(2,3);
		String month = date.substring(3,5);
		String monthYearSeparator = date.substring(5,6);
		String year = date.substring(6,10);

		assertTrue(day.matches("0[1-9]|[12][0-9]|3[01]"));
		assertTrue(dayMonthSeparator.equals("/"));
		assertTrue(month.matches("0[1-9]|1[0-2]"));
		assertTrue(monthYearSeparator.equals("/"));
		assertTrue(year.matches("(19|20)\\d\\d"));
	}
	public static void assertDateFormatMins(String date){

		String day = date.substring(0,2);
		String dayMonthSeparator = date.substring(2,3);
		String month = date.substring(3,5);
		String monthYearSeparator = date.substring(5,6);
		String year = date.substring(6,10);

		assertTrue(day.matches("0[1-9]|[12][0-9]|3[01]"));
		assertTrue(dayMonthSeparator.equals("-"));
		assertTrue(month.matches("0[1-9]|1[0-2]"));
		assertTrue(monthYearSeparator.equals("-"));
		assertTrue(year.matches("(19|20)\\d\\d"));
	}
}
