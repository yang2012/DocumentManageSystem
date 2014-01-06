package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Attachment;
import dmsystem.entity.Operation;
import dmsystem.entity.User;
import dmsystem.service.AttachmentService;
import dmsystem.service.OperationService;
import dmsystem.util.Constants;
import dmsystem.util.DateUtil;
import dmsystem.util.FileUtility;

import java.io.*;

/**
 * Created by justinyang on 13-12-24.
 */
public class AttachmentAction extends ActionSupport {

    private AttachmentService attachmentService;
    private OperationService operationService;
    private User user;

	private static final int BUFFER_SIZE = 16 * 1024;

    // Form input
    private Integer documentId;
    private File upload;
    private String attachmentType;


    // Auto fill
    private String uploadFileName;
    private String uploadContentType;

    // Setter and Getter
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
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
    
    public OperationService getOperationService() {
		return operationService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    private void _saveFile() throws Exception {
        String directory = FileUtility.getFileDirectory();
        String filePath = directory + File.separator + this.uploadFileName;
        File dstFile = new File(filePath);
        File parentFile = dstFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(this.upload), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dstFile),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
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
    	this.user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
        try {
            this._saveFile();

            Attachment newAttachment = new Attachment();
            newAttachment.setName(this.uploadFileName);
            newAttachment.setAttachmentType(this.attachmentType);
            newAttachment.setUrl(FileUtility.getFileUrl(this.uploadFileName));

            this.attachmentService.upload(this.documentId, newAttachment);
           
            Operation operation = new Operation();
			operation.setExpression(Constants.uploadAttachmentExpression);
			operation.setTime(DateUtil.getCurrentDate());
			operation.setType(Constants.uploadAttachmentType);
			operation.setUser(user);
			this.operationService.addOperation(operation);
            
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String delete() {
        return SUCCESS;
    }
}
