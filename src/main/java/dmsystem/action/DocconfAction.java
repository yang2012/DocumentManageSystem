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
	private int id;
	private int docTypeId;
	private String propertyName;
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
	public String delDocType(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			DocumentType documentType=new DocumentType();
			documentType.setId(id);
			this.documentTypeService.delDocType(documentType);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String addExtraProperty(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			DocumentExtraProperty documentExtraProperty=new DocumentExtraProperty();
			DocumentType documentType=documentTypeService.getDocumentTypeById(docTypeId);
			documentExtraProperty.setDocumentType(documentType);
			documentExtraProperty.setPropertyName(propertyName);
			this.documentTypeService.addDocumentExtraProperty(documentExtraProperty);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String delExtraProperty(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			DocumentExtraProperty documentExtraProperty=new DocumentExtraProperty();
			documentExtraProperty.setId(id);
			this.documentTypeService.delDocumentExtraProperty(documentExtraProperty);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}

}
