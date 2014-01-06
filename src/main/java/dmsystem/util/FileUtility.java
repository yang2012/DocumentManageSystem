package dmsystem.util;

import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by justinyang on 13-12-19.
 */
public class FileUtility {
    private static final String kUploadContaner = File.separator + "upload";
    private static final String kBackupContainer = File.separator + "backup";

    public static String getRootDiretoryPath() {
        return ServletActionContext.getServletContext()
                .getRealPath("/");
    }

    public static String getUploadResourceDirectoryPath() {
        return FileUtility.getRootDiretoryPath() + kUploadContaner;
    }

    public static String getBackupDirectoryPath() {
        return FileUtility.getRootDiretoryPath() + kBackupContainer;
    }

    public static String getFileRelativePath(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {
            extension = filename.substring(i+1);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        String newFilename = df.format(new Date()) + "." + extension;
        return kUploadContaner + File.separator + newFilename;
    }

    public static String getAbsoluteFilePath(String relativePath) {
        return FileUtility.getRootDiretoryPath() + File.separator + relativePath;
    }

    public static String getCompisiteBackupFilePath() {
        return FileUtility.getBackupDirectoryPath() + File.separator + "dmsystem.tar";
    }

    public static String getDatabaseBackupFilePath() {
        return FileUtility.getBackupDirectoryPath() + File.separator + "dmsystem.sql";
    }

    public static String getAttachmentBackupFilePath() {
        return FileUtility.getBackupDirectoryPath() + File.separator + "attachment.tar";
    }
}
