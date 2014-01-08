package dmsystem.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmsystem.entity.DocumentRelation;
import dmsystem.util.HibernateUtil;

public class DocumentRelationDao {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public void addDocumentRelationDao(DocumentRelation documentRelation) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(documentRelation);
			ts.commit();
		} finally {
			session.close();
		}
	}
}
