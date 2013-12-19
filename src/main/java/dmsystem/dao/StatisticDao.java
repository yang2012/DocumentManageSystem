package dmsystem.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.Operation;
import dmsystem.util.DateUtil;
import dmsystem.util.HibernateUtil;
import dmsystem.util.StringUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StatisticDao {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getAll() {
		try {
			return hibernateUtil.getAll(Operation.class, "id", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Operation> getOperationsByDate(String time) {
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

		return this.getOperationsByDate(fromDate, toDate);
	}

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationsByDate(Date fromDate, Date toDate) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from Operation op where op.time>? && op.time<=?");
		query.setDate(0, fromDate);
		query.setDate(1, toDate);

		return query.list();
	}
}
