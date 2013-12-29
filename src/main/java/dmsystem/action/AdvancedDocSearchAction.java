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
public class AdvancedDocSearchAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String documentType;
	private String title;
	private String author;
	private String tag;
	private String keywords;
	private String publisher;
	private String publishYear;

	private User user;
	private DocumentSearchService documentSearchService;
	private List<Document> documents;

	public String getdoclist() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}

		this.documents = this.documentSearchService.getDocList(documentType,
				this.initParams());
		if (this.documents == null) {
			return ERROR;
		} else {
			return SUCCESS;
		}
	}

	public String[] initParams() {
		return new String[] { title, author, tag, keywords, publisher,
				publishYear };
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

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}

	public DocumentSearchService getDocumentSearchService() {
		return documentSearchService;
	}

	public void setDocumentSearchService(DocumentSearchService docSearchService) {
		this.documentSearchService = docSearchService;
	}

	public static void main(String[] args) {
		AdvancedDocSearchAction obj = new AdvancedDocSearchAction();
		obj.setDocumentType("Book");
		obj.setAuthor("Allen");
		obj.setKeywords("access control");
		obj.setPublisher("Turing");
		obj.setPublishYear("2013");
		obj.setTag("network security");
		obj.setTitle("role based access control");
		String[] result = obj.initParams();
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
}
