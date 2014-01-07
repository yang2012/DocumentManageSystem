package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.dao.EvaluationDao;
import dmsystem.dao.EvaluationExtraPropertyDao;
import dmsystem.dao.EvaluationWithExtraPropertyDao;
import dmsystem.entity.*;
import dmsystem.util.Constants;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationServiceImpl implements EvaluationService {

    private OperationService operationService;

    private DocumentDao documentDao;
    private EvaluationDao evaluationDao;
    private EvaluationExtraPropertyDao evaluationExtraPropertyDao;
    private EvaluationWithExtraPropertyDao evaluationWithExtraPropertyDao;

    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }

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

    public Evaluation saveEvaluation(User user, Integer documentId, Integer evaluationId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        Evaluation persistentEvaluation = null;
        try {
            if (evaluationId != null) {
                persistentEvaluation = this.evaluationDao.findById(evaluationId);
            }
            if (persistentEvaluation == null) {
                persistentEvaluation = this._addNewEvaluation(user, documentId, transientEvaluation, evaluationExtraPropertyWrappers);
            } else  {
                // Save from draft, so set it's created time to now
                transientEvaluation.setCreateTime(new Date());
                this._updateEvaluation(persistentEvaluation, transientEvaluation, evaluationExtraPropertyWrappers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return persistentEvaluation;
    }

    public Evaluation saveDraft(User user, Integer documentId, Integer evaluationId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        Evaluation persistentEvaluation = null;
        try {
            if (evaluationId != null) {
                persistentEvaluation = this.evaluationDao.findById(evaluationId);
            }
            if (persistentEvaluation == null) {
                persistentEvaluation = this._addNewEvaluation(user, documentId, transientEvaluation, evaluationExtraPropertyWrappers);
            } else {
                this._updateEvaluation(persistentEvaluation, transientEvaluation, evaluationExtraPropertyWrappers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persistentEvaluation;
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

    public Evaluation getSavedDraft(User user, Document document) {
        if (user == null || document == null) {
            return null;
        }
        return this.evaluationDao.getDraft(user, document);
    }

    public List<EvaluationExtraProperty> getAllExtraProperties() {
        try {
            return this.evaluationExtraPropertyDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();

            return new ArrayList<EvaluationExtraProperty>(0);
        }
    }

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

    private Evaluation _updateEvaluation(Evaluation persistentEvaluation, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (persistentEvaluation == null || transientEvaluation == null) {
            return persistentEvaluation;
        }

        persistentEvaluation.updateBasicInfo(transientEvaluation);
        this.evaluationDao.update(persistentEvaluation);

        // Update extra properties
        this._updateExtraProperties(persistentEvaluation, evaluationExtraPropertyWrappers);
        return persistentEvaluation;
    }

    private Evaluation _addNewEvaluation(User user, Integer documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
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

            // Update extra properties
            this._updateExtraProperties(persistentEvaluation, evaluationExtraPropertyWrappers);

            // Record operation
            if (persistentEvaluation.getType() == Constants.kDetailEvaluation) {
            	this.operationService.addOperation(user, Constants.kDetailedCommentOperationType);
            } else {
            	this.operationService.addOperation(user, Constants.kSimpleCommentOperationType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            persistentEvaluation = null;
        }

        return persistentEvaluation;
    }

    private void _updateExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (evaluation == null || evaluationExtraPropertyWrappers == null) {
            return;
        }

        // Delete old extra properties
        this._removeExtraProperties(evaluation);

        // Add new extra properties
        if (evaluationExtraPropertyWrappers != null && !evaluationExtraPropertyWrappers.isEmpty()) {
            this._addNextExtraProperties(evaluation, evaluationExtraPropertyWrappers);
        }
    }

    private void _removeExtraProperties(Evaluation evaluation) throws Exception {
        this.evaluationWithExtraPropertyDao.remove(evaluation.getExtraProperties());
        evaluation.getExtraProperties().clear();
    }

    private void _addNextExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        for (EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper : evaluationExtraPropertyWrappers) {
            String extraPropertyValue = evaluationExtraPropertyWrapper.getExtraPropertyValue();
            // check whether the value is valid
            if (extraPropertyValue == null || extraPropertyValue.isEmpty()) {
                continue;
            }

            EvaluationExtraProperty extraProperty = this.evaluationExtraPropertyDao.findById(evaluationExtraPropertyWrapper.getExtraPropertyId());
            EvaluationWithExtraProperty evaluationWithExtraProperty = this.evaluationWithExtraPropertyDao.find(evaluation, extraProperty);

            if (evaluationWithExtraProperty == null) {
                evaluationWithExtraProperty = new EvaluationWithExtraProperty();

                evaluationWithExtraProperty.setEvaluation(evaluation);
                evaluation.getExtraProperties().add(evaluationWithExtraProperty);
                evaluationWithExtraProperty.setEvaluationExtraProperty(extraProperty);
                extraProperty.getExtraProperties().add(evaluationWithExtraProperty);

                evaluationWithExtraProperty.setPropertyValue(extraPropertyValue);

                this.evaluationWithExtraPropertyDao.add(evaluationWithExtraProperty);
            } else {
                evaluationWithExtraProperty.setPropertyValue(extraPropertyValue);

                this.evaluationWithExtraPropertyDao.update(evaluationWithExtraProperty);
            }
        }
    }
 }
