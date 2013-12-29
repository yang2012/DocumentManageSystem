package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.*;
import dmsystem.service.DocumentService;
import dmsystem.service.EvaluationService;
import dmsystem.util.Wrapper.EvaluationExtraPropertyWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-25.
 */
public class DocumentAction extends ActionSupport {

    private DocumentService documentService;
    private EvaluationService evaluationService;

    private Integer docId;
    private User user;
    private Document document;
    private Evaluation draftEvaluation;
    private List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers;

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Evaluation getDraftEvaluation() {
        return draftEvaluation;
    }

    public void setDraftEvaluation(Evaluation draftEvaluation) {
        this.draftEvaluation = draftEvaluation;
    }

    public List<EvaluationExtraPropertyWrapper> getEvaluationExtraPropertyWrappers() {
        return evaluationExtraPropertyWrappers;
    }

    public void setEvaluationExtraPropertyWrappers(List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers) {
        this.evaluationExtraPropertyWrappers = evaluationExtraPropertyWrappers;
    }

    public String showInfo() {
        this.user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);

        this.document = this.documentService.get(this.docId);

        this.draftEvaluation = this.evaluationService.getSavedDraft(this.user, this.document);
        this.evaluationExtraPropertyWrappers = this._getExtrapropertyWrapper(this.draftEvaluation);

        if (this.document == null) {
            return ERROR;
        } else {
            return SUCCESS;
        }
    }

    private List<EvaluationExtraPropertyWrapper> _getExtrapropertyWrapper(Evaluation evaluation) {
        List<EvaluationExtraProperty> extraProperties = this.evaluationService.getAllExtraProperties();
        return this._convert(evaluation, extraProperties);
    }

    private List<EvaluationExtraPropertyWrapper> _convert(Evaluation evaluation, List<EvaluationExtraProperty> extraProperties) {
        List<EvaluationExtraPropertyWrapper> extraPropertyWrappers = new ArrayList<EvaluationExtraPropertyWrapper>(0);
        for (EvaluationExtraProperty extraProperty : extraProperties) {
            EvaluationExtraPropertyWrapper evaluationExtraPropertyWrapper = new EvaluationExtraPropertyWrapper();
            evaluationExtraPropertyWrapper.setExtraPropertyName(extraProperty.getPropertyName());
            evaluationExtraPropertyWrapper.setExtraPropertyId(extraProperty.getId());

            EvaluationWithExtraProperty evaluationWithExtraProperty = this.evaluationService.getEvaluationWithExtraProperty(evaluation, extraProperty);
            if (evaluationWithExtraProperty != null) {
                evaluationExtraPropertyWrapper.setExtraPropertyValue(evaluationWithExtraProperty.getPropertyValue());
            }

            extraPropertyWrappers.add(evaluationExtraPropertyWrapper);
        }
        return extraPropertyWrappers;
    }
}
