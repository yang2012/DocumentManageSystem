package dmsystem.dao;

import dmsystem.entity.DocumentType;
import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;;

public class DocumentTypeDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

	public void add(DocumentType transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(DocumentType persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
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
}
