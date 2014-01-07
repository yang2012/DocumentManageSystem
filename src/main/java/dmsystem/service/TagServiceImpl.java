package dmsystem.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dmsystem.dao.TagDao;
import dmsystem.entity.Document;
import dmsystem.entity.Tag;
import dmsystem.util.StringUtil;

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
			if (!this.isTagExist(tagname, doc)) {
				this.tagDao.add(tag);
			}

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

	public boolean isTagExist(String tagName, Document doc) {
		Set<Tag> tagSet = doc.getTags();
		Iterator<Tag> tagIter = tagSet.iterator();
		while (tagIter.hasNext()) {
			if (StringUtil.equals(tagName, tagIter.next().getName())) {
				return true;
			}
		}

		return false;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
}
