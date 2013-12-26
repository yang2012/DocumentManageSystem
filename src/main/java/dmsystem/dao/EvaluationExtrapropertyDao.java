package dmsystem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.util.HibernateUtil;

public class EvaluationExtrapropertyDao {
	private HibernateUtil hibernateUtil;

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	@SuppressWarnings("unchecked")
	public List<EvaluationExtraProperty> getAll() throws Exception {
		return hibernateUtil.getAll(EvaluationExtraProperty.class, "id", false);
	}

	public void addEvaluationExtraProperty(
			EvaluationExtraProperty transientInstance) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(transientInstance);
			ts.commit();
		} finally {
			session.close();
		}
	}
	
	public void delEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty){
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.delete(evaluationExtraProperty);
			ts.commit();
		} finally {
			session.close();
		}
	}
	
	public void modEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty){
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(evaluationExtraProperty);
			ts.commit();
		} finally {
			session.close();
		}
	}
}
