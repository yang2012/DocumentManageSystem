package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dmsystem.entity.Document;
import dmsystem.entity.User;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentWithExtraProperty;
import dmsystem.entity.DocumentType;
import dmsystem.util.Constants;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * Utility object for domain model class Document.
 * 
 * @see dmsystem.entity.Document
 * @author Justin Yang
 */
public class DocumentDao {

    private HibernateUtil hibernateUtil;
    private DocumentTypeDao documentTypeDao;
    private DocumentWithExtraPropertyDao documentWithExtraPropertyDao;
    private DocumentExtraPropertyDao documentExtraPropertyDao;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
        this.documentTypeDao = documentTypeDao;
    }

    public void setDocumentWithExtraPropertyDao(DocumentWithExtraPropertyDao documentWithExtraPropertyDao) {
        this.documentWithExtraPropertyDao = documentWithExtraPropertyDao;
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
		hibernateUtil.persist(document);

		return this.update(document.getId(), values);
	}

	public Document update(Integer docId, Map<String, String> values)
			throws Exception {
		Document document = this.findById(docId);

		return this._update(document, values);
	}

	public void add(Document transientInstance) throws Exception {
        hibernateUtil.persist(transientInstance);
	}

	public void remove(Document persistentInstance) throws Exception {
        hibernateUtil.remove(persistentInstance);
	}

	public void update(Document detachedInstance) throws Exception {
		if (detachedInstance != null) {
            hibernateUtil.update(detachedInstance);
		}
	}

	public Document findById(int id) throws Exception {
		return (Document) hibernateUtil.findById(Document.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Document> getAll() throws Exception {
		return hibernateUtil.getAll(Document.class, "createTime", false);
	}

    private Document _update(Document document, Map<String, String> values) throws Exception {
        if (document != null) {
            this._updateBasisValues(document, values);

            hibernateUtil.update(document);
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
                    Integer extraPropertyId = Integer.parseInt(components[1]);

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
            Integer docTypeId = Integer.parseInt(values.get(Constants.kDocumentTypeField));
            DocumentType docType = documentTypeDao.findById(docTypeId);
            document.setDocumentType(docType);
        }
	}
}
