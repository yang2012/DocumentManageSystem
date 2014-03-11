package dmsystem.service;

import java.util.List;

import dmsystem.entity.RelationType;

public interface ReferenceConfService {
	public List<RelationType> getRelationType();
	public void addRelationType(RelationType relationType);
	public void delRelationType(RelationType relationType);
	public void modRelationType(RelationType relationType);
	public RelationType getRelationTypeById(String id);
}
