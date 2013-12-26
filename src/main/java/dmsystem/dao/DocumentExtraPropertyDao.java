package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.entity.DocumentExtraProperty;
import dmsystem.util.HibernateUtil;

/**
 * Utility object for domain model class DocumentExtraProperty.
 * @see dmsystem.entity.DocumentExtraProperty
 * @author Justin Yang
 */
public class DocumentExtraPropertyDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(DocumentExtraProperty transientInstance) throws Exception {
        hibernateUtil.persist(transientInstance);
	}

	public void remove(DocumentExtraProperty persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(DocumentExtraProperty detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public DocumentExtraProperty findById(int id) throws Exception {
		return (DocumentExtraProperty) hibernateUtil.findById(DocumentExtraProperty.class, id);
	}
}
