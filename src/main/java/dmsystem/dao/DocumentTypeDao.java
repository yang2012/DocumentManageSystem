package dmsystem.dao;

import java.util.ArrayList;
import java.util.List;

import dmsystem.entity.DocumentType;
import dmsystem.util.HBaseUtil;

public class DocumentTypeDao {

    private HBaseUtil hBaseUtil;

    public void sethBaseUtil(HBaseUtil hBaseUtil) {
        this.hBaseUtil = hBaseUtil;
    }

	public void add(DocumentType transientInstance) throws Exception {
//		Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.save(transientInstance);
//			ts.commit();
//		} finally {
//			session.close();
//			}
	}

	public void remove(DocumentType persistentInstance) throws Exception {
//		Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.delete(persistentInstance);
//			ts.commit();
//		} finally {
//			session.close();
//			}
//
	}

	public void update(DocumentType detachedInstance) throws Exception {
//		if (detachedInstance != null) {
//			hibernateUtil.update(detachedInstance);
//		}
	}

	public DocumentType findById(String id) throws Exception {
//		return (DocumentType) hibernateUtil.findById(DocumentType.class, id);
        return null;
	}

    public DocumentType findByName(String name) throws Exception {
//        Session session = hibernateUtil.getSessionFactory().openSession();
//
//        session.beginTransaction();
//        Query query = session
//                .createQuery("from DocumentType dt where dt.name=?");
//        Object dbResult = query.setString(0, name).uniqueResult();
//
//        if (dbResult != null) {
//            return (DocumentType) dbResult;
//        } else {
//            return null;
//        }
        return null;
    }
    
	@SuppressWarnings("unchecked")
	public List<DocumentType> getAll() throws Exception {
//		return hibernateUtil.getAll(DocumentType.class, "id", false);
        return new ArrayList<DocumentType>();
	}

}
