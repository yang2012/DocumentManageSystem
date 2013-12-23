package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;

import java.util.List;

/**
 * Created by justinyang on 13-12-23.
 */
public class UserAction extends ActionSupport {

    private User user;
    private List<Document> documents;

    private DocumentService documentService;

    public String index() {
        user = (User)ActionContext.getContext().getSession().get(User.SESSION_KEY);
        documents = this.documentService.getAll();
        return "user";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }
}
