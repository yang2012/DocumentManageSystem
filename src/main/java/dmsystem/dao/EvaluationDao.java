package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.Evaluation;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

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
}
