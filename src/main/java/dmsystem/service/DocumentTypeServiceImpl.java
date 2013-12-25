package dmsystem.service;

import java.util.List;

import dmsystem.dao.DocumentTypeDao;
import dmsystem.entity.DocumentType;

public class DocumentTypeServiceImpl  implements DocumentTypeService  {
	private DocumentTypeDao documentTypeDao;
	
	public DocumentTypeDao getDocumentTypeDao() {
		return documentTypeDao;
	}

	public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
		this.documentTypeDao = documentTypeDao;
	}

	public List<DocumentType> getAll(){
		List<DocumentType> ld = null;
		try {
			ld=this.documentTypeDao.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ld;
	}
}
