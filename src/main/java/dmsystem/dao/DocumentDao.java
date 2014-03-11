package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.google.gson.Gson;
import dmsystem.entity.*;
import dmsystem.util.Constants;
import dmsystem.util.HBaseUtil;
import dmsystem.util.StringUtil;

/**
 * Utility object for domain model class Document.
 * 
 * @see dmsystem.entity.Document
 * @author Justin Yang
 */
public class DocumentDao {

    private String table = "Docs";
    private String infoFamily = "Info";
    private String basicInfoQualifier = "Basis";

    private HBaseUtil hBaseUtil;
    private DocumentTypeDao documentTypeDao;
    private DocumentWithExtraPropertyDao documentWithExtraPropertyDao;
    private DocumentExtraPropertyDao documentExtraPropertyDao;

    private Gson gson = new Gson();

    public void sethBaseUtil(HBaseUtil hBaseUtil) {
        this.hBaseUtil = hBaseUtil;
    }

    public DocumentTypeDao getDocumentTypeDao() {
        return documentTypeDao;
    }

    public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
        this.documentTypeDao = documentTypeDao;
    }

    public DocumentWithExtraPropertyDao getDocumentWithExtraPropertyDao() {
        return documentWithExtraPropertyDao;
    }

    public void setDocumentWithExtraPropertyDao(DocumentWithExtraPropertyDao documentWithExtraPropertyDao) {
        this.documentWithExtraPropertyDao = documentWithExtraPropertyDao;
    }

    public DocumentExtraPropertyDao getDocumentExtraPropertyDao() {
        return documentExtraPropertyDao;
    }

    public void setDocumentExtraPropertyDao(DocumentExtraPropertyDao documentExtraPropertyDao) {
        this.documentExtraPropertyDao = documentExtraPropertyDao;
    }

    public Document create(User user, Map<String, String> values) throws Exception {
		if (user == null) {
			throw new Exception();
		}
		
		Document document = new Document();
		
		// Link user with this document
		document.setUser(user);

        // Set document type
        this._updateDocumentType(document, values);

        // Set time
        document.setCreateTime(new Date());

        // Save document
        this.add(document);

		return this.update(document.getId(), values);
	}

	public Document update(String docId, Map<String, String> values)
			throws Exception {
		Document document = this.findById(docId);
		return this._update(document, values);
	}

	public void add(Document transientInstance) throws Exception {
        String rowKey= this._generateRowKey(transientInstance);
        transientInstance.setId(rowKey);
        String json = gson.toJson(transientInstance);
        this.hBaseUtil.put(this.table, rowKey, this.infoFamily, this.basicInfoQualifier, json);
	}

	public void remove(Document persistentInstance) throws Exception {
        this.hBaseUtil.delete(this.table, persistentInstance.getId());
	}

	public void update(Document detachedInstance) throws Exception {
		if (detachedInstance != null) {
            String json = gson.toJson(detachedInstance);
            this.hBaseUtil.put(this.table, detachedInstance.getId(), this.infoFamily, this.basicInfoQualifier, json);
		}
	}

	public Document findById(String id) throws Exception {
        Document document = null;
        String json = this.hBaseUtil.get(this.table, id, this.infoFamily, this.basicInfoQualifier);
        if (json != null) {
            document = this.gson.fromJson(json, Document.class);
        }
		return document;
	}

	public List<Document> getAll() throws Exception {
        List<String> jsons = this.hBaseUtil.getAll(this.table, this.infoFamily, this.basicInfoQualifier);
        List<Document> documents = new ArrayList<Document>();
        Gson gson = new Gson();
        for (String json : jsons) {
            documents.add(gson.fromJson(json, Document.class));
        }
        return documents;
	}

    private String _generateRowKey(Document document) throws NoSuchAlgorithmException {
        String titleMd5 = StringUtil.md5(document.getTitle());
        long reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        return titleMd5 + reverseTimestamp;
    }

    private Document _update(Document document, Map<String, String> values) throws Exception {
        if (document != null) {
            this._updateBasisValues(document, values);
            String json = gson.toJson(document);
            this.hBaseUtil.put(this.table, document.getId(), this.infoFamily, this.basicInfoQualifier, json);
        }

        return document;
    }

	private void _updateBasisValues(Document document,
			Map<String, String> values) throws Exception {
        Set<String> keys = values.keySet();

        for (String key : keys) {
            if (key.equals(Constants.kTitleField)) {
                String title = values.get(key);
                document.setTitle(title);
            } else if (key.equals(Constants.kAuthorField)) {
                String author = values.get(key);
                document.setAuthor(author);
            } else if (key.equals(Constants.kAbstractsField)) {
                String abstracts = values.get(key);
                document.setAbstracts(abstracts);
            } else if (key.equals(Constants.kKeywordsField)) {
                String keywords = values.get(key);
                document.setKeywords(keywords);
            } else if (key.equals(Constants.kUrlField)) {
                String url = values.get(key);
                document.setUrl(url);
            } else if (key.equals(Constants.kPublisherField)) {
                String publisher = values.get(key);
                document.setPublisher(publisher);
            } else if (key.equals(Constants.kPagesField)) {
                String pagesStr = values
                        .get(key);
                Integer pages = 0;
                if (!pagesStr.isEmpty()) {
                    pages =  Integer.parseInt(pagesStr);
                }
                document.setPages(pages);
            } else if (key.equals(Constants.kYearField)) {
                String year = values.get(key);
                document.setYear(year);
            } else {
                String[] components = key.split("-");
                if (components.length == 2) {
                    String extraPropertyId = components[1];

                    DocumentExtraProperty extraProperty = documentExtraPropertyDao.findById(extraPropertyId);
                    DocumentWithExtraProperty documentWithExtraProperty = documentWithExtraPropertyDao.find(document, extraProperty);
                    if (documentWithExtraProperty == null) {
                        documentWithExtraProperty = new DocumentWithExtraProperty();

                        documentWithExtraProperty.setDocument(document);
                        documentWithExtraProperty.setDocumentExtraProperty(extraProperty);
                        documentWithExtraProperty.setPropertyValue(values.get(key));
                        documentWithExtraPropertyDao.add(documentWithExtraProperty);
                    } else {
                        documentWithExtraProperty.setPropertyValue(values.get(key));
                        documentWithExtraPropertyDao.update(documentWithExtraProperty);
                    }
                    document.getExtraProperties();
                }
            }
        }
	}
	
	private void _updateDocumentType(Document document, Map<String, String> values) throws Exception {
        if (values.containsKey(Constants.kDocumentTypeField)) {
            String docTypeId = values.get(Constants.kDocumentTypeField);
            DocumentType docType = documentTypeDao.findById(docTypeId);
            document.setDocumentType(docType);
        }
	}
}
