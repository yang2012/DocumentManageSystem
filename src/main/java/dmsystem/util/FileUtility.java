package dmsystem.util;

import org.apache.struts2.ServletActionContext;

import java.io.File;

/**
 * Created by justinyang on 13-12-19.
 */
public class FileUtility {
    private static final String kFileContaner = File.separator + "documents";

    public static String getRootDiretoryPath() {
        return ServletActionContext.getServletContext()
                .getRealPath("/");
    }

    public static String getFileDirectory() {
        return FileUtility.getResourceDirectory() + kFileContaner;
    }

    public static String getResourceDirectory() {
        return FileUtility.getRootDiretoryPath() + File.separator + "upload";
    }

    public static String getFileUrl(String filename) {
        return kFileContaner + File.separator + filename;
    }

    public static String getCompisiteBackupFilePath() {
        return FileUtility.getRootDiretoryPath() + File.separator + "backup" + File.separator + "dmsystem.tar";
    }

    public static String getDatabaseBackupFilePath() {
        return FileUtility.getRootDiretoryPath() + File.separator + "backup" + File.separator + "dmsystem.sql";
    }

    public static String getAttachmentBackupFilePath() {
        return FileUtility.getRootDiretoryPath() + File.separator + "backup" + File.separator + "attachment.tar";
    }
}
