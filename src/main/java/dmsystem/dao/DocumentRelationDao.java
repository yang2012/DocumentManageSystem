package dmsystem.dao;

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

	public void addDocumentRelation(DocumentRelation documentRelation) {
//		Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.save(documentRelation);
//			ts.commit();
//		} finally {
//			session.close();
//		}
	}
	
	public void delDocumentRelation(DocumentRelation documentRelation){
//		Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.delete(documentRelation);
//			ts.commit();
//		} finally {
//			session.close();
//		}
	}
}
