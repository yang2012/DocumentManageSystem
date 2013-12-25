package dmsystem.action;

import java.util.List;

import dmsystem.entity.Document;
import dmsystem.service.DocSearchService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class SimpleDocSearchAction {
	private String keywords;

	private DocSearchService docSearchService;

	public List<Document> getDocList() {
		return this.docSearchService.getDocList(keywords);
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public DocSearchService getDocSearchService() {
		return docSearchService;
	}

	public void setDocSearchService(DocSearchService docSearchService) {
		this.docSearchService = docSearchService;
	}
	
}
