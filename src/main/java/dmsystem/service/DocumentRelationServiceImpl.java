package dmsystem.service;

import dmsystem.dao.DocumentRelationDao;
import dmsystem.entity.DocumentRelation;

public class DocumentRelationServiceImpl implements DocumentRelationService{
	private DocumentRelationDao documentRelationDao;
	public DocumentRelationDao getDocumentRelationDao() {
		return documentRelationDao;
	}
	public void setDocumentRelationDao(DocumentRelationDao documentRelationDao) {
		this.documentRelationDao = documentRelationDao;
	}
	public void addDocumentRelation(DocumentRelation documentRelation) {
		this.documentRelationDao.addDocumentRelationDao(documentRelation);
	}
	
}
