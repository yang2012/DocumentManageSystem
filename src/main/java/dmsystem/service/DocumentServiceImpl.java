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

    @Override
    public Document upload(User user, Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) {
        Document persistentDocument = null;

        if (user == null || documentTypeId == 0 || transientDocument == null) {
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
            this._setExtraProperties(persistentDocument, documentExtraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

    @Override
    public Document update(Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) {
        Document persistentDocument = null;

        if (documentTypeId == 0 || transientDocument == null) {
            return persistentDocument;
        }

        try {
            DocumentType documentType = this.documentTypeDao.findById(documentTypeId);

            persistentDocument = new Document();
            persistentDocument.updateInfo(transientDocument);

            this.documentDao.update(persistentDocument);

            // update extra properties
            this._refreshExtraProperties(persistentDocument, documentExtraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

    private void _setExtraProperties(Document document, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) throws Exception {
        if (documentExtraPropertyWrappers == null) {
            return;
        }
        for (DocumentExtraPropertyWrapper documentExtraPropertyWrapper : documentExtraPropertyWrappers) {
            DocumentExtraProperty extraProperty = this.documentExtraPropertyDao.findById(documentExtraPropertyWrapper.getExtraPropertyId());
            DocumentWithExtraProperty documentWithExtraProperty = new DocumentWithExtraProperty();

            documentWithExtraProperty.setDocument(document);
            documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
            documentWithExtraProperty.setPropertyValue(documentExtraPropertyWrapper.getExtraPropertyValue());
            this.documentWithExtraPropertyDao.add(documentWithExtraProperty);
        }
    }

    private void _refreshExtraProperties(Document document, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) throws Exception {
        if (documentExtraPropertyWrappers == null) {
            return;
        }
        for (DocumentExtraPropertyWrapper documentExtraPropertyWrapper : documentExtraPropertyWrappers) {
            Integer id = documentExtraPropertyWrapper.getExtraPropertyId();
            String value = documentExtraPropertyWrapper.getExtraPropertyValue();
            DocumentExtraProperty extraProperty = this.documentExtraPropertyDao.findById(id);
            DocumentWithExtraProperty documentWithExtraProperty = this.documentWithExtraPropertyDao.find(document, extraProperty);
            if (documentWithExtraProperty == null) {
                documentWithExtraProperty = new DocumentWithExtraProperty();

                documentWithExtraProperty.setDocument(document);
                documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
                documentWithExtraProperty.setPropertyValue(value);
                this.documentWithExtraPropertyDao.add(documentWithExtraProperty);
            } else {
                documentWithExtraProperty.setPropertyValue(value);
                this.documentWithExtraPropertyDao.update(documentWithExtraProperty);
            }
        }
    }
}
