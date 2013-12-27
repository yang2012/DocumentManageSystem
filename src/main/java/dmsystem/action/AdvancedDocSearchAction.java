package dmsystem.action;

import java.util.List;

import dmsystem.entity.Document;
import dmsystem.service.DocSearchService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class AdvancedDocSearchAction {

	private String documentType;
	private String title;
	private String author;
	private String tag;
	private String keywords;
	private String publisher;
	private String publishYear;

	private DocSearchService docSearchService;

	public List<Document> getDocList() {
		return this.docSearchService
				.getDocList(documentType, this.initParams());
	}

	public String[] initParams() {
		return new String[] { title, author, tag, keywords, publisher,
				publishYear };
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

	public DocSearchService getDocSearchService() {
		return docSearchService;
	}

	public void setDocSearchService(DocSearchService docSearchService) {
		this.docSearchService = docSearchService;
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
