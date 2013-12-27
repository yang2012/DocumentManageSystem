package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Evaluation;
import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.entity.User;
import dmsystem.service.EvaluationService;
import dmsystem.util.Constants;
import dmsystem.util.Wrapper.DocumentExtraPropertyWrapper;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationAction extends ActionSupport {

    private EvaluationService evaluationService;

    private User user;

    private Integer documentId;

    private Evaluation evaluation;

    private List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers;

    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void setEvaluationExtraPropertyWrappers(List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        this.evaluationExtraPropertyWrappers = evaluationExtraPropertyWrappers;
    }

    public List<EvaluationExtraPropertyWrapper> getEvaluationExtraPropertyWrappers() {
        return evaluationExtraPropertyWrappers;
    }

    public String commitEvaluation() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
        this.evaluation.setPublished(true);

        // Persistent evaluation
        this._saveEvaluation();

        if (evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String saveEvaluationDraft() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
        this.evaluation.setPublished(false);

        // Persistent evaluation
        this._saveEvaluation();

        if (evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String deleteEvaluation() {
        return SUCCESS;
    }

    public String getDetailEvaluationForm() {
        List<EvaluationExtraProperty> extraProperties = this.evaluationService.getExtraProperties();
        this.evaluationExtraPropertyWrappers = this._convert(extraProperties);
        return SUCCESS;
    }

    public void _saveEvaluation() {
        if (this.evaluationExtraPropertyWrappers == null || this.evaluationExtraPropertyWrappers.isEmpty()) {
            this.evaluation = this.evaluationService.add(user, this.documentId, this.evaluation);
        } else {
            this.evaluation = this.evaluationService.add(user, this.documentId, this.evaluation, this.evaluationExtraPropertyWrappers);
        }
    }

    private List<EvaluationExtraPropertyWrapper> _convert(List<EvaluationExtraProperty> extraProperties) {
        List<EvaluationExtraPropertyWrapper> documentExtraPropertyWrappers = new ArrayList<EvaluationExtraPropertyWrapper>(0);
        for (EvaluationExtraProperty extraProperty : extraProperties) {
            EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper = new EvaluationExtraPropertyWrapper();
            evaluationExtraPropertyWrapper.setExtraPropertyName(extraProperty.getPropertyName());
            evaluationExtraPropertyWrapper.setExtraPropertyId(extraProperty.getId());

            documentExtraPropertyWrappers.add(evaluationExtraPropertyWrapper);
        }
        return documentExtraPropertyWrappers;
    }
}
