package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.entity.Document;

import java.util.List;

/**
 * Created by justinyang on 13-12-23.
 */
public class DocumentServiceImpl implements DocumentService {
    private DocumentDao documentDao;

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    public List<Document> getAll() {
        List<Document> documents = null;
        try {
            documents = this.documentDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
