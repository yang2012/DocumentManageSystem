package dmsystem.dao;

import dmsystem.entity.Evaluation;
import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.util.HibernateUtil;

import java.util.List;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationExtraPropertyDao {
    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void add(EvaluationExtraProperty transientInstance) throws Exception {
        hibernateUtil.persist(transientInstance);
    }

    public void remove(EvaluationExtraProperty persistentInstance) throws Exception {
        hibernateUtil.remove(persistentInstance);
    }

    public void update(EvaluationExtraProperty detachedInstance) throws Exception {
        if (detachedInstance != null) {
            hibernateUtil.update(detachedInstance);
        }
    }

    public EvaluationExtraProperty findById(int id) throws Exception {
        return (EvaluationExtraProperty) hibernateUtil.findById(EvaluationExtraProperty.class, id);
    }

    public List<EvaluationExtraProperty> getAll() throws Exception {
        return hibernateUtil.getAll(EvaluationExtraProperty.class, "propertyName", true);
    }
}
