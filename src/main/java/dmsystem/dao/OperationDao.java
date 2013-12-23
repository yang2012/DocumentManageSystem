package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.Operation;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * Utility object for domain model class Operation.
 * @see dmsystem.entity.Operation
 * @author Justin Yang
 */
public class OperationDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(Operation transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(Operation persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(Operation detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public Operation findById(int id) throws Exception {
		return (Operation) hibernateUtil.findById(Operation.class, id);
	}
}
