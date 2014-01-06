package dmsystem.service;

import java.util.List;

import dmsystem.entity.Document;

/**
 * 
 * @author bryant zhang
 * 
 */
public interface DocumentSearchService {
	public List<Document> simpleSearch(String keywords) throws Exception;

	public List<Document> advancedSearch(String docType, String[] params)
			throws Exception;
}
