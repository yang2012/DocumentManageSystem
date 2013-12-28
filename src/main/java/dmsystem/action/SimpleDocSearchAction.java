package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.Document;
import dmsystem.entity.User;
import dmsystem.service.DocumentSearchService;

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

	public String getdoclist() {
		System.out.println("asd");
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}

		this.documents = this.documentSearchService.getDocList(keywords);
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

	public void setDocumentSearchService(DocumentSearchService documentSearchService) {
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

}
