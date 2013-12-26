package dmsystem.service;

import java.util.List;

import dmsystem.entity.DocumentType;


public interface DocumentTypeService {
	public List<DocumentType> getAll();
	public void addDocType(DocumentType documentType);
	public void delDocType(DocumentType documentType);
}
