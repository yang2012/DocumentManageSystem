package dmsystem.service;

import dmsystem.entity.*;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.List;

/**
 * Created by justinyang on 13-12-26.
 */
public interface EvaluationService {

    // Add simple evaluation
    public Evaluation add(User user, Integer documentId, Evaluation transientEvaluation);

    // Add detail evaluation
    public Evaluation add(User user, Integer documentId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers);

    // Update evaluation
    public Evaluation update(Integer evaluationId, Evaluation evaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers);

    public List<EvaluationExtraProperty> getExtraProperties();

    public Evaluation getSavedDraft(User user, Document document);

    public List<EvaluationExtraProperty> getAllExtraProperties();

    public EvaluationWithExtraProperty getEvaluationWithExtraProperty(Evaluation evaluation, EvaluationExtraProperty evaluationExtraProperty);
}
