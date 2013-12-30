package dmsystem.dao;

import dmsystem.entity.Evaluation;
import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.entity.EvaluationWithExtraProperty;
import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Set;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationWithExtraPropertyDao {
    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void add(EvaluationWithExtraProperty transientInstance) throws Exception {
        hibernateUtil.persist(transientInstance);
    }

    public void remove(EvaluationWithExtraProperty persistentInstance) throws Exception {
        hibernateUtil.remove(persistentInstance);
    }

    public void remove(Set<EvaluationWithExtraProperty> persistentInstances) throws Exception {
        hibernateUtil.remove(persistentInstances);
    }

    public void update(EvaluationWithExtraProperty detachedInstance) throws Exception {
        if (detachedInstance != null) {
            hibernateUtil.update(detachedInstance);
        }
    }

    public EvaluationWithExtraProperty find(Evaluation evaluation, EvaluationExtraProperty evaluationExtraProperty) throws Exception {
        Session session = hibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        Query query = session
                .createQuery("from EvaluationWithExtraProperty d where d.pk.evaluation=? and d.pk.evaluationExtraProperty=?");
        Object dbResult = query.setEntity(0, evaluation).setEntity(1, evaluationExtraProperty).uniqueResult();

        if (dbResult != null) {
            return (EvaluationWithExtraProperty) dbResult;
        } else {
            return null;
        }
    }
}
