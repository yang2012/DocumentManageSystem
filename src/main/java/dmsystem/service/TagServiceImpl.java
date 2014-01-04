package dmsystem.service;

import dmsystem.dao.TagDao;
import dmsystem.entity.Document;
import dmsystem.entity.Tag;

/**
 * 
 * @author bryant zhang
 * 
 */
public class TagServiceImpl implements TagService {
	private TagDao tagDao;

	public void createTag(String tagname, Document doc) {
		Tag tag = new Tag();
		tag.setDocument(doc);
		tag.setName(tagname);
		try {
			this.tagDao.add(tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTag(int tagId) {
		try {
			this.tagDao.remove(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
}
