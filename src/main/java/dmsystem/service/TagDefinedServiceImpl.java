package dmsystem.service;

import java.util.List;

import dmsystem.dao.TagDefinedDao;
import dmsystem.entity.TagDefined;

public class TagDefinedServiceImpl implements TagDefinedService{

	private TagDefinedDao tagDefinedDao;
	public TagDefinedDao getTagDefinedDao() {
		return tagDefinedDao;
	}
	public void setTagDefinedDao(TagDefinedDao tagDefinedDao) {
		this.tagDefinedDao = tagDefinedDao;
	}
	public List<TagDefined> getAll() {
		return this.tagDefinedDao.getAll();
	}
	public void add(TagDefined tagDefined) {
		this.tagDefinedDao.add(tagDefined);
	}
	public void del(TagDefined tagDefined) {
		this.tagDefinedDao.del(tagDefined);
	}
	public void mod(TagDefined tagDefined) {
		this.tagDefinedDao.mod(tagDefined);
	}
}
