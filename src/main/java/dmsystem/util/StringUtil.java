package dmsystem.util;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StringUtil {

	public static boolean equals(String str1, String str2) {

		boolean result = false;
		if (str1 == null || str2 == null)
			return result;

		if (str1 != null && str2 != null && str1.equals(str2)) {
			result = true;
		}

		return result;
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {

		boolean result = false;
		if (str1 == null || str2 == null)
			return result;

		if (str1 != null && str2 != null && str1.equalsIgnoreCase(str2)) {
			result = true;
		}

		return result;
	}

	public static boolean isNullOrEmpty(String str) {

		if (str == null)
			return true;
		if (str.isEmpty())
			return true;

		return false;
	}

}
