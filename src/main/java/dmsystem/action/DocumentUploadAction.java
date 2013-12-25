package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.ExtraPropertyWrapper;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-25.
 */
public class DocumentUploadAction extends ActionSupport {

    private DocumentService documentService;

    private Integer docId;

    private User user;

    private Document document;

    private Integer documentTypeId;
    private List<ExtraPropertyWrapper> extraPropertyWrappers;

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
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

    public Integer getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<ExtraPropertyWrapper> getExtraPropertyWrappers() {
        return extraPropertyWrappers;
    }

    public void setExtraPropertyWrappers(List<ExtraPropertyWrapper> extraPropertyWrappers) {
        this.extraPropertyWrappers = extraPropertyWrappers;
    }

    public String showUpload() {
        user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);

        return SUCCESS;
    }

    public String commitUpload() {
        user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
        Document persistentDocument = this.documentService.upload(user, documentTypeId, this.document, this.extraPropertyWrappers);

        if (persistentDocument != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String showModification() {
        user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);

        this.document = this.documentService.get(this.docId);

        if (this.document == null) {
            return ERROR;
        } else {
            return SUCCESS;
        }
    }

    public String commitModification() {
        System.out.println(this.document.getTitle());
        return SUCCESS;
    }

    public String getExtraproperties() {
        Set<DocumentExtraProperty> extraProperties = this.documentService.getExtraProperties(this.documentTypeId);

        this.extraPropertyWrappers = this._convert(extraProperties);

        return SUCCESS;
    }

    private List<ExtraPropertyWrapper> _convert(Set<DocumentExtraProperty> extraProperties) {
        List<ExtraPropertyWrapper> extraPropertyWrappers = new ArrayList<ExtraPropertyWrapper>(0);
        for (DocumentExtraProperty extraProperty : extraProperties) {
            ExtraPropertyWrapper extraPropertyWrapper = new ExtraPropertyWrapper();
            extraPropertyWrapper.setExtraPropertyName(extraProperty.getPropertyName());
            extraPropertyWrapper.setExtraPropertyId(extraProperty.getId());

            extraPropertyWrappers.add(extraPropertyWrapper);
        }
        return extraPropertyWrappers;
    }
}
