package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.RelationType;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * Utility object for domain model class RelationType.
 * @see dmsystem.entity.RelationType
 * @author Justin Yang
 */
public class RelationTypeDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(RelationType transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(RelationType persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(RelationType detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public RelationType findById(int id) throws Exception {
		return (RelationType) hibernateUtil.findById(RelationType.class, id);
	}
}
