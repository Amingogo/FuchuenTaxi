package tw.com.fuchuen.utils;

public class UtilFormat {
	
	public static String getYearFormat(int year) {
		return String.format("%04d", year);
	}
	
	public static String getTwoDigitFormat(int value) {
		return String.format("%02d", value);
	}
	
}
