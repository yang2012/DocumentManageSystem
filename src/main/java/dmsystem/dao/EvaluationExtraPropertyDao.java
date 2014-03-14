package dmsystem.dao;

import dmsystem.entity.EvaluationExtraProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationExtraPropertyDao {

    public void add(EvaluationExtraProperty transientInstance) throws Exception {
//        hibernateUtil.persist(transientInstance);
    }

    public void remove(EvaluationExtraProperty persistentInstance) throws Exception {
//    	Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.delete(persistentInstance);
//			ts.commit();
//		} finally {
//			session.close();
//		}
    }

    public void update(EvaluationExtraProperty detachedInstance) throws Exception {
//    	Session session = hibernateUtil.getSessionFactory().openSession();
//		Transaction ts = null;
//		try {
//			ts = session.beginTransaction();
//			session.update(detachedInstance);
//			ts.commit();
//		} finally {
//			session.close();
//		}
    }

    public EvaluationExtraProperty findById(String id) throws Exception {
//        return (EvaluationExtraProperty) hibernateUtil.findById(EvaluationExtraProperty.class, id);
        return null;
    }

    @SuppressWarnings("unchecked")
	public List<EvaluationExtraProperty> getAll() throws Exception {
//        return hibernateUtil.getAll(EvaluationExtraProperty.class, "propertyName", true);
        return new ArrayList<EvaluationExtraProperty>();
    }
}
