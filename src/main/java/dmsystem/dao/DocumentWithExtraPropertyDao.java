package dmsystem.dao;

import dmsystem.entity.Document;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentWithExtraProperty;
import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by justinyang on 13-12-18.
 */
public class DocumentWithExtraPropertyDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

        public void add(DocumentWithExtraProperty transientInstance) throws Exception {
            hibernateUtil.persist(transientInstance);
        }

        public void remove(DocumentWithExtraProperty persistentInstance) throws Exception {
            hibernateUtil.remove(persistentInstance);
        }

        public void update(DocumentWithExtraProperty detachedInstance) throws Exception {
            if (detachedInstance != null) {
                hibernateUtil.update(detachedInstance);
            }
        }

        public DocumentWithExtraProperty find(Document document, DocumentExtraProperty documentExtraProperty) throws Exception {
            Session session = hibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            Query query = session
                    .createQuery("from DocumentWithExtraProperty d where d.pk.document=? and d.pk.documentExtraProperty=?");
            Object dbResult = query.setEntity(0, document).setEntity(1, documentExtraProperty).uniqueResult();

            if (dbResult != null) {
                return (DocumentWithExtraProperty) dbResult;
            } else {
                return null;
            }
        }
}
