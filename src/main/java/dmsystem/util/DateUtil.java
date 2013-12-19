package dmsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author bryant zhang
 * 
 */
public class DateUtil {

	// set current date
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		return currentDate;
	}

	public static Date getDateFromString(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// set from date
	public static Date getFromDate(int index) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, index);
		return calendar.getTime();
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentDate());
		System.out.println(DateUtil.getFromDate(-5));
		System.out.println(DateUtil.getDateFromString("2013-12-12"));
	}
}