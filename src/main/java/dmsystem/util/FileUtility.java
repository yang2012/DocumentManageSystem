package dmsystem.util;

import java.io.File;

/**
 * Created by justinyang on 13-12-19.
 */
public class FileUtility {
    private static final String kFileContaner = File.separator + "documents";

    public static String getFileDirectory() {
        return FileUtility.getResourceDirectory() + kFileContaner;
    }

    public static String getResourceDirectory() {
        return System.getProperty("user.home")
                + File.separator + "Developer" + File.separator + "DMSystemResource";
    }

    public static String getFileUrl(String filename) {
        return kFileContaner + File.separator + filename;
    }
}
