package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.dao.DocumentExtraPropertyDao;
import dmsystem.dao.DocumentTypeDao;
import dmsystem.dao.DocumentWithExtraPropertyDao;
import dmsystem.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public Document upload(User user, Integer documentTypeId, Document transientDocument, List<ExtraPropertyWrapper> extraPropertyWrappers) {
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
            this._setExtraProperties(persistentDocument, extraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

    public Document update(Integer documentTypeId, Document transientDocument, List<ExtraPropertyWrapper> extraPropertyWrappers) {
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
            this._refreshExtraProperties(persistentDocument, extraPropertyWrappers);

        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            persistentDocument = null;
        }
        return persistentDocument;
    }

    public Set<DocumentExtraProperty> getExtraProperties(Integer documentTypeId) {
        Set<DocumentExtraProperty> extraProperties = null;
        try {
            DocumentType documentType = this.documentTypeDao.findById(documentTypeId);
            extraProperties = documentType.getExtraProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extraProperties;
    }

    private void _setExtraProperties(Document document, List<ExtraPropertyWrapper> extraPropertyWrappers) throws Exception {
        for (ExtraPropertyWrapper extraPropertyWrapper : extraPropertyWrappers) {
            DocumentExtraProperty extraProperty = this.documentExtraPropertyDao.findById(extraPropertyWrapper.getExtraPropertyId());
            DocumentWithExtraProperty documentWithExtraProperty = new DocumentWithExtraProperty();

            documentWithExtraProperty.setDocument(document);
            documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
            documentWithExtraProperty.setPropertyValue(extraPropertyWrapper.getExtraPropertyValue());
            this.documentWithExtraPropertyDao.add(documentWithExtraProperty);
        }
    }

    private void _refreshExtraProperties(Document document, List<ExtraPropertyWrapper> extraPropertyWrappers) throws Exception {
        for (ExtraPropertyWrapper extraPropertyWrapper : extraPropertyWrappers) {
            Integer id = extraPropertyWrapper.getExtraPropertyId();
            String value = extraPropertyWrapper.getExtraPropertyValue();
            DocumentExtraProperty extraProperty = this.documentExtraPropertyDao.findById(id);
            DocumentWithExtraProperty documentWithExtraProperty = this.documentWithExtraPropertyDao.find(document, extraProperty);
            if (documentWithExtraProperty == null) {
                documentWithExtraProperty = new DocumentWithExtraProperty();

                documentWithExtraProperty.setDocument(document);
                documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
                documentWithExtraProperty.setPropertyValue(value);
                documentWithExtraPropertyDao.add(documentWithExtraProperty);
            } else {
                documentWithExtraProperty.setPropertyValue(value);
                documentWithExtraPropertyDao.update(documentWithExtraProperty);
            }
        }
    }
}
