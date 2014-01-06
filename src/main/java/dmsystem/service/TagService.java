package dmsystem.service;

import dmsystem.entity.Document;

/**
 * 
 * @author bryant zhang
 * 
 */
public interface TagService {
	public void createTag(String tagname, Document doc);
	public void deleteTag(int tagId);
}
