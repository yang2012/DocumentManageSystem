package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.dao.DocumentExtraPropertyDao;
import dmsystem.dao.DocumentTypeDao;
import dmsystem.dao.DocumentWithExtraPropertyDao;
import dmsystem.entity.*;
import dmsystem.util.Wrapper.DocumentExtraPropertyWrapper;

import java.util.Date;
import java.util.List;

/**
 * Created by justinyang on 13-12-23.
 */
public class DocumentServiceImpl implements DocumentService {

    private DocumentDao documentDao;
    private DocumentTypeDao documentTypeDao;
    private DocumentExtraPropertyDao documentExtraPropertyDao;
    private DocumentWithExtraPropertyDao documentWithExtraPropertyDao;

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
        this.documentTypeDao = documentTypeDao;
    }

    public void setDocumentExtraPropertyDao(DocumentExtraPropertyDao documentExtraPropertyDao) {
        this.documentExtraPropertyDao = documentExtraPropertyDao;
    }

    public void setDocumentWithExtraPropertyDao(DocumentWithExtraPropertyDao documentWithExtraPropertyDao) {
        this.documentWithExtraPropertyDao = documentWithExtraPropertyDao;
    }

    public List<Document> getAll() {
        List<Document> documents = null;
        try {
            documents = this.documentDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    public Document get(Integer docId) {
        Document document = null;
        try {
            document = this.documentDao.findById(docId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public Document upload(User user, Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) {
        Document persistentDocument = null;

        if (user == null || documentTypeId == null || transientDocument == null) {
            return persistentDocument;
        }

        try {
            DocumentType documentType = this.documentTypeDao.findById(documentTypeId);

            persistentDocument = new Document();
            persistentDocument.updateInfo(transientDocument);
            persistentDocument.setUser(user);
            persistentDocument.setDocumentType(documentType);
            persistentDocument.setCreateTime(new Date());

            this.documentDao.add(persistentDocument);

            // Set extra properties
            this._updateExtraProperties(persistentDocument, documentExtraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

    public Document update(Integer documentId, Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) {
        Document persistentDocument = null;

        if (documentTypeId == null || transientDocument == null) {
            return persistentDocument;
        }

        try {
            persistentDocument = this.documentDao.findById(documentId);

            // Update document type
            DocumentType documentType = this.documentTypeDao.findById(documentTypeId);
            persistentDocument.setDocumentType(documentType);

            // Update document basic info
            persistentDocument.updateInfo(transientDocument);

            this.documentDao.update(persistentDocument);

            // update extra properties
            this._updateExtraProperties(transientDocument, documentExtraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

	public DocumentWithExtraProperty getDocumentExtraProperty(
			Document document, DocumentExtraProperty documentExtraProperty) {
		try {
			return this.documentWithExtraPropertyDao.find(document, documentExtraProperty);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    private void _updateExtraProperties(Document document, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) throws Exception {
        if (document == null || documentExtraPropertyWrappers == null) {
            return;
        }

        // Delete old extra properties
        this._removeExtraProperties(document);

        // Add new extra properties
        if (documentExtraPropertyWrappers != null && !documentExtraPropertyWrappers.isEmpty()) {
            this._addNextExtraProperties(document, documentExtraPropertyWrappers);
        }
    }

    private void _removeExtraProperties(Document document) throws Exception {
        this.documentWithExtraPropertyDao.remove(document.getExtraProperties());
    }

    private void _addNextExtraProperties(Document document, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) throws Exception {
        for (DocumentExtraPropertyWrapper documentExtraPropertyWrapper : documentExtraPropertyWrappers) {
            String extraPropertyValue = documentExtraPropertyWrapper.getExtraPropertyValue();
            // check whether the value is valid
            if (extraPropertyValue == null || extraPropertyValue.isEmpty()) {
                continue;
            }

            DocumentExtraProperty extraProperty = this.documentExtraPropertyDao.findById(documentExtraPropertyWrapper.getExtraPropertyId());
            DocumentWithExtraProperty documentWithExtraProperty = this.documentWithExtraPropertyDao.find(document, extraProperty);

            if (documentWithExtraProperty == null) {
                documentWithExtraProperty = new DocumentWithExtraProperty();

                documentWithExtraProperty.setDocument(document);
                document.getExtraProperties().add(documentWithExtraProperty);
                documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
                extraProperty.getExtraProperties().add(documentWithExtraProperty);

                documentWithExtraProperty.setPropertyValue(extraPropertyValue);

                this.documentWithExtraPropertyDao.add(documentWithExtraProperty);
            } else {
                documentWithExtraProperty.setPropertyValue(extraPropertyValue);

                this.documentWithExtraPropertyDao.update(documentWithExtraProperty);
            }
        }
    }
}
