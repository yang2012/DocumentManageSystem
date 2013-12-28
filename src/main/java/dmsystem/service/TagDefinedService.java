package dmsystem.service;

import java.util.List;

import dmsystem.entity.TagDefined;

public interface TagDefinedService {
	public List<TagDefined> getAll();
	public void add(TagDefined tagDefined);
	public void del(TagDefined tagDefined);
	public void mod(TagDefined tagDefined);
}
