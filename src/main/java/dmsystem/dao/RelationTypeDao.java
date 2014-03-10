package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.util.List;

import dmsystem.entity.RelationType;
import dmsystem.util.HibernateUtil;

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
//		Session session = getHibernateUtil().getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.save(transientInstance);
//			ts.commit();
//		} finally {
//			session.close();
//		}
	}

	public void remove(RelationType persistentInstance) throws Exception {
//		Session session = getHibernateUtil().getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.delete(persistentInstance);
//			ts.commit();
//		} finally {
//			session.close();
//		}
	}

	public void update(RelationType detachedInstance) throws Exception {
//		Session session = getHibernateUtil().getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.update(detachedInstance);
//			ts.commit();
//		} finally {
//			session.close();
//		}
	}

	public RelationType findById(int id) throws Exception {
		return (RelationType) getHibernateUtil().findById(RelationType.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<RelationType> getAll(){
		try {
			return getHibernateUtil().getAll(RelationType.class, "id", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public RelationType getRelationTypeById(Integer id) {
		// TODO Auto-generated method stub
		try {
			return (RelationType)getHibernateUtil().findById(RelationType.class,id );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
