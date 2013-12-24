package dmsystem.action;

import com.opensymphony.xwork2.ActionSupport;
import dmsystem.service.AttachmentService;
import dmsystem.util.FileUtility;

import java.io.*;

/**
 * Created by justinyang on 13-12-24.
 */
public class AttachmentAction extends ActionSupport {

    private AttachmentService attachmentService;

    private static final int BUFFER_SIZE = 16 * 1024;

    private String title;

    private File upload;

    private String uploadFileName;

    private String uploadContentType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    private static void _copy(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String show() {
        return SUCCESS;
    }

    public String upload() {
        String directory = FileUtility.getFileDirectory();
        String filePath = directory + File.separator + this.uploadFileName;
        File dstFile = new File(filePath);
        File parentFile = dstFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        this._copy(this.upload, dstFile);
        return SUCCESS;
    }

    public String delete() {
        return SUCCESS;
    }
}
