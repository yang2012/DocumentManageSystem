package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.*;
import dmsystem.service.DocumentRelationService;
import dmsystem.service.DocumentService;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.EvaluationService;
import dmsystem.service.ReferenceConfService;
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
    private ReferenceConfService relationTypeService;
    private String docId;
    private User user;
    private Document document;
    private Evaluation draftEvaluation;
    private List<EvaluationExtraPropertyWrapper> evaluationExtraPropertyWrappers;
    private List<Attachment> attachments;
    private List<RelationType> relationTypes;
    private DocumentTypeService documentTypeService;
    private List<DocumentType> documentTypes;
    private ArrayList<String> jsonList;
    private List<Document> documents;
    private String refdocument;
    private String relationTypeId;
    private DocumentRelationService documentRelationService;
    private String refereeid;
    private String refererid;


	public String getRefereeid() {
		return refereeid;
	}

	public void setRefereeid(String refereeid) {
		this.refereeid = refereeid;
	}

	public String getRefererid() {
		return refererid;
	}

	public void setRefererid(String refererid) {
		this.refererid = refererid;
	}

	public DocumentRelationService getDocumentRelationService() {
		return documentRelationService;
	}

	public void setDocumentRelationService(
			DocumentRelationService documentRelationService) {
		this.documentRelationService = documentRelationService;
	}

	public String getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(String relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public EvaluationService getEvaluationService() {
		return evaluationService;
	}

	public String getRefdocument() {
		return refdocument;
	}

	public void setRefdocument(String refdocument) {
		this.refdocument = refdocument;
	}

	public ArrayList<String> getJsonList() {
		return jsonList;
	}

	public void setJsonList(ArrayList<String> jsonList) {
		this.jsonList = jsonList;
	}

	public List<RelationType> getRelationTypes() {
		return relationTypes;
	}

	public void setRelationTypes(List<RelationType> relationTypes) {
		this.relationTypes = relationTypes;
	}

	public ReferenceConfService getRelationTypeService() {
		return relationTypeService;
	}

	public void setRelationTypeService(ReferenceConfService relationTypeService) {
		this.relationTypeService = relationTypeService;
	}

	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setEvaluationService(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
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
        this.documentTypes = this.documentTypeService.getAll();
        this.relationTypes=this.relationTypeService.getRelationType();
        this.document = this.documentService.get(this.docId);

        Set<Evaluation> evaluations = this.evaluationService.getEvaluations(this.document);
        if (evaluations != null) {
            this.document.setEvaluations(this.evaluationService.getEvaluations(this.document));
        }

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
    
    public String findAttachments(){
    	List<Attachment> result=new ArrayList<Attachment>();
    	for(Attachment attachment:this.documentService.get(docId).getAttachments()){
    		result.add(attachment);
    	}
    	attachments=result;
    	return SUCCESS;
    }
    
    public String getJson(){
    	jsonList=new ArrayList<String>();
    	this.documents=this.documentService.getAll();
    	for(int i=0;i<documents.size();i++){
    		jsonList.add(documents.get(i).getId()+"_"+documents.get(i).getTitle());
    	}
    	return SUCCESS;
    }
    
    public String addDocumentRelation(){
    	String refid=refdocument.split("_")[0];
    	DocumentRelation documentRelation=new DocumentRelation();
    	Document refer=this.documentService.get(docId);
    	Document refee=this.documentService.get(refid);
    	documentRelation.setReferee(refee);
    	documentRelation.setReferer(refer);
    	RelationType relationType=this.relationTypeService.getRelationTypeById(relationTypeId);
    	documentRelation.setRelationType(relationType);
    	this.documentRelationService.addDocumentRelation(documentRelation);
    	return SUCCESS;
    }
    
    public String delDocumentRelation(){
    	DocumentRelation documentRelation=new DocumentRelation();
    	Document documentrefer=this.documentService.get(refererid);
    	Document documentrefee=this.documentService.get(refereeid);
    	documentRelation.setReferee(documentrefee);
    	documentRelation.setReferer(documentrefer);
    	RelationType relationType=new RelationType();
    	relationType.setId(relationTypeId);
    	documentRelation.setRelationType(relationType);
    	this.documentRelationService.delDocumentRelation(documentRelation);
    	return SUCCESS;
    }
}
