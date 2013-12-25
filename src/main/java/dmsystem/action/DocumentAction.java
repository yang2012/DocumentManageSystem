package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.ExtraPropertyWrapper;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;

import java.util.List;

/**
 * Created by justinyang on 13-12-25.
 */
public class DocumentAction extends ActionSupport {

    private DocumentService documentService;

    private Integer docId;

    private User user;

    private Document document;

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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String showInfo() {
        user = (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);

        this.document = this.documentService.get(this.docId);

        if (this.document == null) {
            return ERROR;
        } else {
            return SUCCESS;
        }
    }
}
