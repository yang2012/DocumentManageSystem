package dmsystem.service;

import dmsystem.dao.DocumentDao;
import dmsystem.dao.EvaluationDao;
import dmsystem.dao.EvaluationExtraPropertyDao;
import dmsystem.dao.EvaluationWithExtraPropertyDao;
import dmsystem.entity.*;
import dmsystem.util.Constants;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.io.IOException;
import java.util.*;

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

    public Evaluation saveEvaluation(User user, String documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        Evaluation persistentEvaluation = null;
        try {
            Evaluation persistentDraft;
            if (user.getId() != null && documentId != null) {
                persistentDraft = this.evaluationDao.getDraft(user.getId(), documentId);
                if (persistentDraft != null) {
                    this.evaluationDao.removeDraft(persistentDraft);
                }
            }
            persistentEvaluation = this._addNewEvaluation(user, documentId, transientEvaluation, evaluationExtraPropertyWrappers, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return persistentEvaluation;
    }

    public Evaluation saveDraft(User user, String documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        Evaluation persistentEvaluation = null;
        try {
            persistentEvaluation = this.evaluationDao.getDraft(user.getId(), documentId);
            if (persistentEvaluation == null) {
                persistentEvaluation = this._addNewEvaluation(user, documentId, transientEvaluation, evaluationExtraPropertyWrappers, true);
            } else {
                this._updateDraft(persistentEvaluation, transientEvaluation, evaluationExtraPropertyWrappers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persistentEvaluation;
    }

    public List<EvaluationExtraProperty> getExtraProperties() {
        List<EvaluationExtraProperty> evaluationExtraProperties;
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
        return this.evaluationDao.getDraft(user.getId(), document.getId());
    }

    public Set<Evaluation> getEvaluations(Document document) {
        Set<Evaluation> evaluations = new HashSet<Evaluation>();
        if (document == null) {
            System.err.println("Null parameters when calling getEvaluations");
            return evaluations;
        }

        try {
            evaluations = this.evaluationDao.getEvaluations(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return evaluations;
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

    private Evaluation _updateDraft(Evaluation persistentDraft, Evaluation transientDraft, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        if (persistentDraft == null || transientDraft == null) {
            return persistentDraft;
        }

        persistentDraft.updateBasicInfo(transientDraft);
        // Update extra properties
        this._updateExtraProperties(persistentDraft, evaluationExtraPropertyWrappers);
        this.evaluationDao.updateDraft(persistentDraft);

        return persistentDraft;
    }

    private Evaluation _addNewEvaluation(User user, String documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers, Boolean isDraft) {
        Evaluation persistentEvaluation = null;

        if (user == null || documentId == null || transientEvaluation == null) {
            return persistentEvaluation;
        }

        try {
            persistentEvaluation = new Evaluation();
            // Basis information
            persistentEvaluation.updateBasicInfo(transientEvaluation);
            persistentEvaluation.setCreateTime(new Date());
            // Build up relationship
            persistentEvaluation.setDocId(documentId);
            persistentEvaluation.setUserId(user.getId());
            persistentEvaluation.setUserName(user.getName());

            // Update extra properties
            this._updateExtraProperties(persistentEvaluation, evaluationExtraPropertyWrappers);

            if (isDraft) {
                this.evaluationDao.addDraft(persistentEvaluation);
            } else {
                this.evaluationDao.addEvaluation(persistentEvaluation);
            }

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
        evaluation.getExtraProperties().clear();
    }

    private void _addNextExtraProperties(Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) throws Exception {
        for (EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper : evaluationExtraPropertyWrappers) {
            String extraPropertyValue = evaluationExtraPropertyWrapper.getExtraPropertyValue();
            // check whether the value is valid
            if (extraPropertyValue == null || extraPropertyValue.isEmpty()) {
                continue;
            }

            EvaluationExtraProperty extraProperty = new EvaluationExtraProperty();
            extraProperty.setPropertyName(evaluationExtraPropertyWrapper.getExtraPropertyName());

            EvaluationWithExtraProperty evaluationWithExtraProperty = new EvaluationWithExtraProperty();
            evaluationWithExtraProperty.setEvaluation(evaluation);
            evaluationWithExtraProperty.setPropertyValue(extraPropertyValue);
            evaluationWithExtraProperty.setEvaluationExtraProperty(extraProperty);

            evaluation.getExtraProperties().add(evaluationWithExtraProperty);
        }
    }
 }
