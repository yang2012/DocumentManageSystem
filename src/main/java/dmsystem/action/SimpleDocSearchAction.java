package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.Document;
import dmsystem.entity.DocumentType;
import dmsystem.entity.User;
import dmsystem.service.DocumentSearchService;
import dmsystem.service.DocumentTypeService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class SimpleDocSearchAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keywords;
	private User user;
	private DocumentSearchService documentSearchService;
	private List<Document> documents;
	private DocumentTypeService documentTypeService;
	private List<DocumentType> documentTypes;

	public String getdoclist() throws Exception {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		this.documentTypes = this.documentTypeService.getAll();
		this.documents = this.documentSearchService.simpleSearch(keywords);
		if (this.documents == null) {
			return ERROR;
		} else {
			return SUCCESS;
		}
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public DocumentSearchService getDocumentSearchService() {
		return documentSearchService;
	}

	public void setDocumentSearchService(
			DocumentSearchService documentSearchService) {
		this.documentSearchService = documentSearchService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
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
