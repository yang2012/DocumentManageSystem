package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.Operation;
import dmsystem.util.DateUtil;
import dmsystem.util.HibernateUtil;
import dmsystem.util.StringUtil;

/**
 * Utility object for domain model class Operation.
 * 
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

	@SuppressWarnings("unchecked")
	public List<Integer> retrieveUserIds(Date fromDate, Date toDate) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select distinct op.userId from Operation op where op.time>? && op.time<=?");
		query.setDate(0, fromDate);
		query.setDate(1, toDate);

		return query.list();
	}

	public List<Integer> retrieveUserIds(String time) {
		Date toDate = DateUtil.getCurrentDate();
		Date fromDate = null;
		if (StringUtil.equals(time, "week")) {
			fromDate = DateUtil.getFromDate(-7);
		}
		if (StringUtil.equals(time, "month")) {
			fromDate = DateUtil.getFromDate(-30);
		}
		if (StringUtil.equals(time, "year")) {
			fromDate = DateUtil.getFromDate(-365);
		}

		return this.retrieveUserIds(fromDate, toDate);
	}
}
