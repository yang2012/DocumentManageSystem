package dmsystem.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StringUtil {

    public static String md5(String content) throws NoSuchAlgorithmException {
        if (content == null) {
            return null;
        }
        MessageDigest mdEnc = MessageDigest.getInstance("MD5");
        mdEnc.update(content.getBytes(), 0, content.length());
        return new BigInteger(1, mdEnc.digest()).toString(16);
    }

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
