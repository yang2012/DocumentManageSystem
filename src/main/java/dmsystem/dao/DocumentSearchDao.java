package dmsystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.Document;
import dmsystem.util.HibernateUtil;
import dmsystem.util.StringUtil;

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

	@SuppressWarnings("unchecked")
	public List<Document> simpleSearch(String keywords) throws Exception {
		List<Document> docList = null;
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from Document where title like ? or author like ? or keywords like ? or abstracts like ?");
		query.setString(0, "%" + keywords + "%");
		query.setString(1, "%" + keywords + "%");
		query.setString(2, "%" + keywords + "%");
		query.setString(3, "%" + keywords + "%");
		docList = query.list();

		return docList;
	}

	@SuppressWarnings("unchecked")
	public List<Document> advancedSearch(Map<String, String> paramsMap)
			throws Exception {
		List<Document> docList = null;
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		String sql = "";
		if (StringUtil.equals(paramsMap.get("documentType"), "0")) {
			sql = "from Document where title like ? and author like ? and keywords like ? and publisher like ? and year like ?";
		} else {
			sql = "from Document where docTypeId="
					+ paramsMap.get("documentType")
					+ " and title like ? and author like ? and keywords like ? and publisher like ? and year like ?";
		}
		Query query = session.createQuery(sql);
		query.setString(0, "%" + paramsMap.get("title") == null ? ""
				: paramsMap.get("title") + "%");
		query.setString(1, "%" + paramsMap.get("author") == null ? ""
				: paramsMap.get("author") + "%");
		query.setString(2, "%" + paramsMap.get("keywords") == null ? ""
				: paramsMap.get("keywords") + "%");
		query.setString(3, "%" + paramsMap.get("publisher") == null ? ""
				: paramsMap.get("publisher") + "%");
		query.setString(4, "%" + paramsMap.get("publishYear") == null ? ""
				: paramsMap.get("publishYear") + "%");
		docList = query.list();

		return docList;
	}

	public static void main(String[] args) {
		DocumentSearchDao doc = new DocumentSearchDao();
		List<Document> list = null;
		try {
			list = doc.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(list.size());
	}
}
