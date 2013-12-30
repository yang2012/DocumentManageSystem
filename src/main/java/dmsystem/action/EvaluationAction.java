package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Evaluation;
import dmsystem.entity.User;
import dmsystem.service.EvaluationService;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.*;

/**
 * Created by justinyang on 13-12-26.
 */
public class EvaluationAction extends ActionSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6462402298793799592L;

	private EvaluationService evaluationService;

    private User user;

    // Form fields
    private Integer documentId;
    private Integer evalulationId;
    private Evaluation evaluation;

    private List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers;

    public EvaluationService getEvaluationService() {
        return evaluationService;
    }

    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getEvaluationId() {
        return evalulationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evalulationId = evaluationId;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public List<EvaluationExtraPropertyWrapper> getEvaluationExtraPropertyWrappers() {
        return evaluationExtraPropertyWrappers;
    }

    public void setEvaluationExtraPropertyWrappers(List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        this.evaluationExtraPropertyWrappers = evaluationExtraPropertyWrappers;
    }

    public String commitEvaluation() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
        this.evaluation.setPublished(true);

        // Persistent evaluation
        this.evaluation = this.evaluationService.saveEvaluation(this.user, this.documentId, this.evalulationId, this.evaluation, this.evaluationExtraPropertyWrappers);

        if (this.evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String saveEvaluationDraft() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);

        this.evaluation.setPublished(false);
        this.evaluation = this.evaluationService.saveDraft(this.user, this.documentId, this.evalulationId, this.evaluation, this.evaluationExtraPropertyWrappers);

        if (evaluation != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String deleteEvaluation() {
        return SUCCESS;
    }
}
