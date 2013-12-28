package dmsystem.service;

import java.util.List;

import dmsystem.entity.Document;

/**
 * 
 * @author bryant zhang
 * 
 */
public interface DocumentSearchService {
	public List<Document> getDocList(String keywords);

	public List<Document> getDocList(String docType, String[] params);
}
