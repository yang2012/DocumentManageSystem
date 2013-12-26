package dmsystem.dao;

import java.util.List;

import dmsystem.entity.Document;
import dmsystem.entity.DocumentType;
import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DocumentTypeDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

	public void add(DocumentType transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(DocumentType persistentInstance) throws Exception {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(persistentInstance);
			ts.commit();
		} finally {
			session.close();
			}
		
	}

	public void update(DocumentType detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public DocumentType findById(int id) throws Exception {
		return (DocumentType) hibernateUtil.findById(DocumentType.class, id);
	}

    public DocumentType findByName(String name) throws Exception {
        Session session = hibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        Query query = session
                .createQuery("from DocumentType dt where dt.name=?");
        Object dbResult = query.setString(0, name).uniqueResult();

        if (dbResult != null) {
            return (DocumentType) dbResult;
        } else {
            return null;
        }
    }
    
	@SuppressWarnings("unchecked")
	public List<DocumentType> getAll() throws Exception {
		return hibernateUtil.getAll(DocumentType.class, "id", false);
	}

}
