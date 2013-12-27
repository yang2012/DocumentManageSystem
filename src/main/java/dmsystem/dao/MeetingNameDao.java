package dmsystem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmsystem.entity.MeetingName;
import dmsystem.entity.Tag;
import dmsystem.util.HibernateUtil;

public class MeetingNameDao {
	private HibernateUtil hibernateUtil;

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public void add(MeetingName meetingName) throws Exception {
		hibernateUtil.persist(meetingName);
	}

	public void remove(MeetingName meetingName) throws Exception {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(meetingName);
			ts.commit();
		} finally {
			session.close();
		}
	}

	public void update(MeetingName detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public MeetingName findById(int id) throws Exception {
		return (MeetingName) hibernateUtil.findById(MeetingName.class, id);
	}

	public List<MeetingName> getAll() {
		try {
			return hibernateUtil.getAll(MeetingName.class, "id", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
