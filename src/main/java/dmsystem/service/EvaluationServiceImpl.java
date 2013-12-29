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

    public Evaluation add(User user, Integer documentId, Evaluation transientEvaluation) {
        Evaluation persistentEvaluation = null;

        if (user == null || documentId == null || transientEvaluation == null) {
            return persistentEvaluation;
        }

        try {
            Document document = this.documentDao.findById(documentId);

            persistentEvaluation = new Evaluation();
            // Basis information
            persistentEvaluation.updateBasicInfo(transientEvaluation);
            persistentEvaluation.setCreateTime(new Date());
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
    public Evaluation update(Integer evaluationId, Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        try {
            Evaluation persistentEvaluation = this.evaluationDao.findById(evaluationId);

            // Update basic info
            persistentEvaluation.updateBasicInfo(evaluation);
            this.evaluationDao.update(persistentEvaluation);

            // Update extra properties
            this._updateExtraProperties(persistentEvaluation, evaluationExtraPropertyWrappers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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

    @Override
    public Evaluation getSavedDraft(User user, Document document) {
        if (user == null || document == null) {
            return null;
        }
        return this.evaluationDao.getDraft(user, document);
    }

    @Override
    public List<EvaluationExtraProperty> getAllExtraProperties() {
        try {
            return this.evaluationExtraPropertyDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();

            return new ArrayList<EvaluationExtraProperty>(0);
        }
    }

    @Override
    public EvaluationWithExtraProperty getEvaluationWithExtraProperty(Evaluation evaluation, EvaluationExtraProperty evaluationExtraProperty) {
        if (evaluation == null || evaluationExtraProperty == null) {
            return null;
        }
        try {
            return this.evaluationWithExtraPropertyDao.find(evaluation, evaluationExtraProperty);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void _setExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (evaluationExtraPropertyWrappers == null) {
            return;
        }
        for (EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper : evaluationExtraPropertyWrappers) {
            String extraPropertyValue = evaluationExtraPropertyWrapper.getExtraPropertyValue();
            // check whether the value is valid
            if (extraPropertyValue == null || extraPropertyValue.isEmpty()) {
                continue;
            }

            EvaluationExtraProperty extraProperty = this.evaluationExtraPropertyDao.findById(evaluationExtraPropertyWrapper.getExtraPropertyId());
            this._addNextExtraProperties(evaluation, extraProperty, extraPropertyValue);
        }
    }

    private void _updateExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (evaluationExtraPropertyWrappers == null) {
            return;
        }

        for (EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper : evaluationExtraPropertyWrappers) {
            String extraPropertyValue = evaluationExtraPropertyWrapper.getExtraPropertyValue();
            // check whether the value is valid
            if (extraPropertyValue == null || extraPropertyValue.isEmpty()) {
                continue;
            }

            EvaluationExtraProperty extraProperty = this.evaluationExtraPropertyDao.findById(evaluationExtraPropertyWrapper.getExtraPropertyId());
            EvaluationWithExtraProperty evaluationWithExtraProperty = this.evaluationWithExtraPropertyDao.find(evaluation, extraProperty);

            if (evaluationWithExtraProperty == null) {
                this._addNextExtraProperties(evaluation, extraProperty, extraPropertyValue);
            } else {
                evaluationWithExtraProperty.setPropertyValue(extraPropertyValue);

                this.evaluationWithExtraPropertyDao.update(evaluationWithExtraProperty);

            }
        }
    }

    private void _addNextExtraProperties(Evaluation evaluation, EvaluationExtraProperty evaluationExtraProperty, String value) throws Exception {
        EvaluationWithExtraProperty evaluationWithExtraProperty = new EvaluationWithExtraProperty();

        evaluationWithExtraProperty.setEvaluation(evaluation);
        evaluationWithExtraProperty.setEvaluationExtraProperty(evaluationExtraProperty);

        evaluationWithExtraProperty.setPropertyValue(value);

        this.evaluationWithExtraPropertyDao.add(evaluationWithExtraProperty);
    }
}
