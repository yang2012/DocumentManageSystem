package dmsystem.util;

import org.apache.struts2.ServletActionContext;

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
        return ServletActionContext.getServletContext()
                .getRealPath("/") + File.separator + "upload";
    }

    public static String getFileUrl(String filename) {
        return kFileContaner + File.separator + filename;
    }
}
