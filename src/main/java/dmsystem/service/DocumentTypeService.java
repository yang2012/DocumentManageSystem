package dmsystem.service;

import java.util.List;

import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentType;


public interface DocumentTypeService {
	public List<DocumentType> getAll();
	public void addDocType(DocumentType documentType);
	public void delDocType(DocumentType documentType);
	public DocumentType getDocumentTypeById(int id);
	public void addDocumentExtraProperty(DocumentExtraProperty documentExtraProperty);
	public void delDocumentExtraProperty(DocumentExtraProperty documentExtraProperty);
}
