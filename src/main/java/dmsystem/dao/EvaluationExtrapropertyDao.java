package dmsystem.dao;

import dmsystem.entity.Evaluation;
import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationExtraPropertyDao {
    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void add(EvaluationExtraProperty transientInstance) throws Exception {
        hibernateUtil.persist(transientInstance);
    }

    public void remove(EvaluationExtraProperty persistentInstance) throws Exception {
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

    public void update(EvaluationExtraProperty detachedInstance) throws Exception {
    	Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(detachedInstance);
			ts.commit();
		} finally {
			session.close();
		}
    }

    public EvaluationExtraProperty findById(int id) throws Exception {
        return (EvaluationExtraProperty) hibernateUtil.findById(EvaluationExtraProperty.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<EvaluationExtraProperty> getAll() throws Exception {
        return hibernateUtil.getAll(EvaluationExtraProperty.class, "propertyName", true);
    }
}
