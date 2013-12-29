package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Evaluation;
import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.entity.User;
import dmsystem.service.EvaluationService;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.*;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationAction extends ActionSupport {

    private EvaluationService evaluationService;

    private User user;

    // Form fields
    private Integer documentId;
    private Integer evalulationId;
    private Evaluation evaluation;

    private List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers;

    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Evaluation getEvaluation() {
        return evaluation;
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

        if (this.evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String saveEvaluationDraft() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
        if (this.evalulationId == null || this.evalulationId == 0) {
            this.evaluation.setPublished(false);

            // Persistent evaluation
            this._saveEvaluation();
        } else {
            this.evaluationService.update(this.evalulationId, this.evaluation, this.evaluationExtraPropertyWrappers);
        }

        if (evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String deleteEvaluation() {
        return SUCCESS;
    }

    public void _saveEvaluation() {
        if (this.evaluationExtraPropertyWrappers == null || this.evaluationExtraPropertyWrappers.isEmpty()) {
            this.evaluation = this.evaluationService.add(user, this.documentId, this.evaluation);
        } else {
            this.evaluation = this.evaluationService.add(user, this.documentId, this.evaluation, this.evaluationExtraPropertyWrappers);
        }
    }
}
