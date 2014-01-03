package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.DocumentType;
import dmsystem.entity.User;
import dmsystem.service.DocumentTypeService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StatisticAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private String time;
	private String operationType;
	private String username;

	private DocumentTypeService documentTypeService;
	private List<DocumentType> documentTypes;

	public String retrievestatistic() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		documentTypes = this.documentTypeService.getAll();
		
		if (user == null) {
			return LOGIN;
		} else
			return SUCCESS;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

}
