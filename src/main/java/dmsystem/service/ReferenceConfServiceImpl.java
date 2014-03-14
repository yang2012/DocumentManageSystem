package dmsystem.service;

import java.util.List;

import dmsystem.dao.RelationTypeDao;
import dmsystem.entity.RelationType;

public class ReferenceConfServiceImpl implements ReferenceConfService {
	RelationTypeDao relationTypeDao;

	public RelationTypeDao getRelationTypeDao() {
		return relationTypeDao;
	}

	public void setRelationTypeDao(RelationTypeDao relationTypeDao) {
		this.relationTypeDao = relationTypeDao;
	}

	public List<RelationType> getRelationType() {
		return relationTypeDao.getAll();
	}

	public void addRelationType(RelationType relationType) {
		try {
			relationTypeDao.add(relationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delRelationType(RelationType relationType) {
		try {
			relationTypeDao.remove(relationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modRelationType(RelationType relationType) {
		try {
			relationTypeDao.update(relationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RelationType getRelationTypeById(String id){
		return relationTypeDao.getRelationTypeById(id);
	}

}
