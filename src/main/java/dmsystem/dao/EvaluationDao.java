package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.Document;
import dmsystem.entity.Evaluation;
import dmsystem.entity.User;
import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Utility object for domain model class Evaluation.
 * @see dmsystem.entity.Evaluation
 * @author Justin Yang
 */
public class EvaluationDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(Evaluation transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(Evaluation persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(Evaluation detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public Evaluation findById(int id) throws Exception {
		return (Evaluation) hibernateUtil.findById(Evaluation.class, id);
	}

    public Evaluation getDraft(User user, Document document) {
        Evaluation draft = null;
        Session session = this.hibernateUtil.getSessionFactory().getCurrentSession();

        Query query = session.createQuery("from Evaluation e where e.user=:user and e.document=:document and e.published=false");
        query.setEntity("user", user);
        query.setEntity("document", document);
        List result = query.list();
        if (result.size() != 0) {
            if (result.size() > 1) {
                System.err.println("Should be only one draft but get " + result.size());
            }
            draft = (Evaluation) result.get(0);
        }

        return draft;
    }
}
