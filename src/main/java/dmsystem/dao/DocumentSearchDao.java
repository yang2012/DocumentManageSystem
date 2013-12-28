package dmsystem.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.Document;
import dmsystem.util.HibernateUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class DocumentSearchDao {
	private HibernateUtil hibernateUtil;

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	@SuppressWarnings("unchecked")
	public List<Document> getAll() throws Exception {
		return hibernateUtil.getAll(Document.class, "createTime", false);
	}

	@SuppressWarnings("unchecked")
	public List<Document> getDocsByType(String docType) throws Exception {
		List<Document> docList = null;
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from Document doc where doc.documentType=?");
		query.setString(0, docType);
		docList = query.list();

		return docList;
	}

	public static void main(String[] args) {
		DocumentSearchDao doc = new DocumentSearchDao();
		List<Document> list = null;
		try {
			list = doc.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(list.size());
	}
}
