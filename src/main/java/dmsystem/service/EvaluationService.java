package dmsystem.service;

import dmsystem.entity.*;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.List;

/**
 * Created by justinyang on 13-12-26.
 */
public interface EvaluationService {

    public Evaluation saveEvaluation(User user, Integer documentId, Integer evaluationId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers);

    public Evaluation saveDraft(User user, Integer documentId, Integer evaluationId, Evaluation transientEvaluation, List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers);

    public List<EvaluationExtraProperty> getExtraProperties();

    public Evaluation getSavedDraft(User user, Document document);

    public List<EvaluationExtraProperty> getAllExtraProperties();

    public EvaluationWithExtraProperty getEvaluationWithExtraProperty(Evaluation evaluation, EvaluationExtraProperty evaluationExtraProperty);
}
