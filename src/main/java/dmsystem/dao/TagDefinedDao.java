package dmsystem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmsystem.entity.TagDefined;
import dmsystem.util.HibernateUtil;

public class TagDefinedDao {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	@SuppressWarnings("unchecked")
	public List<TagDefined> getAll() {
		try {
			return hibernateUtil.getAll(TagDefined.class, "id", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void add(TagDefined tagDefined) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(tagDefined);
			ts.commit();
		} finally {
			session.close();
		}
	}

	public void del(TagDefined tagDefined) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(tagDefined);
			ts.commit();
		} finally {
			session.close();
		}
	}
	
	public void mod(TagDefined tagDefined) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(tagDefined);
			ts.commit();
		} finally {
			session.close();
		}
	}
}
