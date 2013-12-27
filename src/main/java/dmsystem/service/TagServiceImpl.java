package dmsystem.service;

import java.util.List;

import dmsystem.dao.TagDao;
import dmsystem.entity.Tag;

public class TagServiceImpl implements TagService{
	private TagDao tagDao;
	public TagDao getTagDao() {
		return tagDao;
	}
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	public List<Tag> getAll() {
		return this.tagDao.getAll();
		// TODO Auto-generated method stub
		
	}

}
