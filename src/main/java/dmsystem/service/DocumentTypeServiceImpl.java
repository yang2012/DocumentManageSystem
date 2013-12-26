package dmsystem.service;

import java.util.List;

import dmsystem.dao.DocumentExtraPropertyDao;
import dmsystem.dao.DocumentTypeDao;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentType;

public class DocumentTypeServiceImpl  implements DocumentTypeService  {
	private DocumentTypeDao documentTypeDao;
	private DocumentExtraPropertyDao documentExtraProperyDao;
	
	public DocumentExtraPropertyDao getDocumentExtraProperyDao() {
		return documentExtraProperyDao;
	}

	public void setDocumentExtraProperyDao(
			DocumentExtraPropertyDao documentExtraProperyDao) {
		this.documentExtraProperyDao = documentExtraProperyDao;
	}

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
	
	public void addDocType(DocumentType documentType){
		try {
			this.documentTypeDao.add(documentType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delDocType(DocumentType documentType){
		try{
			this.documentTypeDao.remove(documentType);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public DocumentType getDocumentTypeById(int id){
		DocumentType dt=null;
		try {
			dt=this.documentTypeDao.findById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	public void addDocumentExtraProperty(DocumentExtraProperty documentExtraType){
		try {
			this.documentExtraProperyDao.add(documentExtraType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delDocumentExtraProperty(DocumentExtraProperty documentExtraType){
		try {
			this.documentExtraProperyDao.remove(documentExtraType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
