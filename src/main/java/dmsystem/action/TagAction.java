package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.Document;
import dmsystem.entity.DocumentType;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.TagService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class TagAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tagname;
	private int documentId;

	private User user;

	private DocumentService documentService;
	private Document document;

	private DocumentTypeService documentTypeService;
	private List<DocumentType> documentTypes;
	private TagService tagService;

	public String createtag() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}

		this.tagService.createTag(this.tagname,
				this.documentService.get(documentId));
		this.documentTypes = this.documentTypeService.getAll();
		this.document = this.documentService.get(documentId);
		return SUCCESS;
	}

	public String deletetag() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}

		this.tagService.createTag(this.tagname,
				this.documentService.get(documentId));
		this.documentTypes = this.documentTypeService.getAll();
		this.document = this.documentService.get(documentId);
		return SUCCESS;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
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

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
