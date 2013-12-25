package dmsystem.action;

import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentType;
import dmsystem.entity.User;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.UserService;
import dmsystem.util.Constants;

public class DocconfAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7832026651557331002L;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private User user;
	private UserService userService;
	private DocumentTypeService documentTypeService;
	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}
	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}
	private List<DocumentType> documentTypes;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getdocconf() throws Exception{
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			documentTypes=this.documentTypeService.getAll();
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}
	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}
	
	public String addDocType(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			DocumentType documentType=new DocumentType();
			documentType.setName(name);
			this.documentTypeService.addDocType(documentType);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}

}
