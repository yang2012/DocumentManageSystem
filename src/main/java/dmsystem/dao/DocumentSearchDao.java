package dmsystem.dao;

import java.util.List;
import java.util.Map;

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
//		Session session = this.hibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		Query query = session
//				.createQuery("from Document doc where doc.documentType=?");
//		query.setString(0, docType);
//		docList = query.list();

		return docList;
	}

	@SuppressWarnings("unchecked")
	public List<Document> simpleSearch(String keywords) throws Exception {
		List<Document> docList = null;
//		Session session = this.hibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		Query query = session
//				.createQuery("from Document where title like ? or author like ? or keywords like ? or abstracts like ?");
//		query.setString(0, "%" + keywords + "%");
//		query.setString(1, "%" + keywords + "%");
//		query.setString(2, "%" + keywords + "%");
//		query.setString(3, "%" + keywords + "%");
//		docList = query.list();

		return docList;
	}

	@SuppressWarnings("unchecked")
	public List<Document> advancedSearch(Map<String, String> paramsMap)
			throws Exception {
		String title = paramsMap.get("title") == null ? "" : paramsMap
				.get("title");
		String author = paramsMap.get("author") == null ? "" : paramsMap
				.get("author");
		String keywords = paramsMap.get("keywords") == null ? "" : paramsMap
				.get("keywords");
		String publisher = paramsMap.get("publisher") == null ? "" : paramsMap
				.get("publisher");
		String publishYear = paramsMap.get("publishYear") == null ? ""
				: paramsMap.get("publishYear");
		
		List<Document> docList = null;
//		Session session = this.hibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		String sql = "";
//		if (StringUtil.equals(paramsMap.get("documentType"), "0")) {
//			sql = "from Document where title like ? and author like ? and keywords like ? and publisher like ? and year like ?";
//		} else {
//			sql = "from Document where docTypeId='"
//					+ paramsMap.get("documentType")
//					+ "' and title like ? and author like ? and keywords like ? and publisher like ? and year like ?";
//		}
//		Query query = session.createQuery(sql);
//		query.setString(0, "%" + title + "%");
//		query.setString(1, "%" + author + "%");
//		query.setString(2, "%" + keywords + "%");
//		query.setString(3, "%" + publisher + "%");
//		query.setString(4, "%" + publishYear + "%");
//		docList = query.list();

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
