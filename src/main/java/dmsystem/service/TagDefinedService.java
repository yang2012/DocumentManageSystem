package dmsystem.service;

import java.util.List;

import dmsystem.entity.TagDefined;

public interface TagDefinedService {
	public List<TagDefined> getAll();
	public void add(TagDefined tagDefined);
}
