package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;
import dmsystem.service.UserService;
import dmsystem.util.Constants;

import java.util.List;

/**
 * Created by justinyang on 13-12-23.
 */
public class UserAction extends ActionSupport {

	private User user;
	private List<Document> documents;
	private List<User> users;

	private DocumentService documentService;
	private UserService userService;

	public String index() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);

		if (user == null) {
			return LOGIN;
		}

		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			users = this.userService.getAllUsers();
			result = "admin";
		} else {
			documents = this.documentService.getAll();
			result = "user";
		}
		return result;
	}

	public String getAllDocuments() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		documents= this.documentService.getAll();
		result = SUCCESS;
		return result;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
