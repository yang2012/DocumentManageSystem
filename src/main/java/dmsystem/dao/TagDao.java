package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.Tag;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * Utility object for domain model class Tag.
 * @see dmsystem.entity.Tag
 * @author Justin Yang
 */
public class TagDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(Tag transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(Tag persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(Tag detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public Tag findById(int id) throws Exception {
		return (Tag) hibernateUtil.findById(Tag.class, id);
	}
}
