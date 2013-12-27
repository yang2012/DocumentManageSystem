package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.dao.EvaluationDao;
import dmsystem.dao.EvaluationExtraPropertyDao;
import dmsystem.dao.EvaluationWithExtraPropertyDao;
import dmsystem.entity.*;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationServiceImpl implements EvaluationService {

    private DocumentDao documentDao;
    private EvaluationDao evaluationDao;
    private EvaluationExtraPropertyDao evaluationExtraPropertyDao;
    private EvaluationWithExtraPropertyDao evaluationWithExtraPropertyDao;

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public void setEvaluationDao(EvaluationDao evaluationDao) {
        this.evaluationDao = evaluationDao;
    }

    public void setEvaluationExtraPropertyDao(EvaluationExtraPropertyDao evaluationExtraPropertyDao) {
        this.evaluationExtraPropertyDao = evaluationExtraPropertyDao;
    }

    public void setEvaluationWithExtraPropertyDao(EvaluationWithExtraPropertyDao evaluationWithExtraPropertyDao) {
        this.evaluationWithExtraPropertyDao = evaluationWithExtraPropertyDao;
    }

    @Override
    public Evaluation add(User user, Integer documentId, Evaluation transientEvaluation) {
        Evaluation persistentEvaluation = null;

        if (user == null || documentId == null || transientEvaluation == null) {
            return persistentEvaluation;
        }

        try {
            Document document = this.documentDao.findById(documentId);

            persistentEvaluation = new Evaluation();
            // Basis information
            persistentEvaluation.updateBaseInfo(transientEvaluation);
            // Build up relationship
            persistentEvaluation.setDocument(document);
            persistentEvaluation.setUser(user);

            this.evaluationDao.add(persistentEvaluation);
        } catch (Exception ex) {
            ex.printStackTrace();

            persistentEvaluation = null;
        }

        return persistentEvaluation;
    }

    @Override
    public Evaluation add(User user, Integer documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        Evaluation persistentEvaluation = this.add(user, documentId, transientEvaluation);

        if (persistentEvaluation != null) {
            // Set extra properties
            try {
                this._setExtraProperties(persistentEvaluation, evaluationExtraPropertyWrappers);
            } catch (Exception e) {
                e.printStackTrace();

                // Remove persistent evaluation
                try {
                    this.evaluationDao.remove(persistentEvaluation);
                } catch (Exception e1) {
                    e1.printStackTrace();

                    persistentEvaluation = null;
                }
            }
        }

        return persistentEvaluation;
    }

    @Override
    public List<EvaluationExtraProperty> getExtraProperties() {
        List<EvaluationExtraProperty> evaluationExtraProperties = null;
        try {
            evaluationExtraProperties = this.evaluationExtraPropertyDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();

            evaluationExtraProperties = new ArrayList<EvaluationExtraProperty>(0);
        }

        return evaluationExtraProperties;
    }

    private void _setExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (evaluationExtraPropertyWrappers == null) {
            return;
        }
        for (EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper : evaluationExtraPropertyWrappers) {
            EvaluationExtraProperty extraProperty = this.evaluationExtraPropertyDao.findById(evaluationExtraPropertyWrapper.getExtraPropertyId());
            EvaluationWithExtraProperty evaluationWithExtraProperty = new EvaluationWithExtraProperty();

            evaluationWithExtraProperty.setEvaluation(evaluation);
            evaluationWithExtraProperty.setEvaluationExtraProperty(extraProperty);
            evaluationWithExtraProperty.setPropertyValue(evaluationExtraPropertyWrapper.getExtraPropertyValue());
            this.evaluationWithExtraPropertyDao.add(evaluationWithExtraProperty);
        }
    }
}
