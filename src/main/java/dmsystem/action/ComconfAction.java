package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.EvaluationExtraProperty;
import dmsystem.entity.User;
import dmsystem.service.CommentConfService;
import dmsystem.util.Constants;

public class ComconfAction extends ActionSupport{
	private CommentConfService commentConfService;
	private User user;
	private List<EvaluationExtraProperty> evaluationExtraProperties;
	private String propertyName;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public User getUser() {
		return user;
	}

	public List<EvaluationExtraProperty> getEvaluationExtraProperties() {
		return evaluationExtraProperties;
	}

	public void setEvaluationExtraProperties(
			List<EvaluationExtraProperty> evaluationExtraProperties) {
		this.evaluationExtraProperties = evaluationExtraProperties;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setEvaluationExtraProperty(
			List<EvaluationExtraProperty> evaluationExtraProperties) {
		this.evaluationExtraProperties = evaluationExtraProperties;
	}

	public CommentConfService getCommentConfService() {
		return commentConfService;
	}

	public void setCommentConfService(CommentConfService commentConfService) {
		this.commentConfService = commentConfService;
	}
	
	public String getcomconf(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			evaluationExtraProperties=this.commentConfService.getAll();
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String addEvaluationExtraProperty(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			EvaluationExtraProperty evaluationExtraProperty=new EvaluationExtraProperty();
			evaluationExtraProperty.setPropertyName(propertyName);
			this.commentConfService.addEvaluationExtraProperty(evaluationExtraProperty);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String delEvaluationExtraProperty(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			EvaluationExtraProperty evaluationExtraProperty=new EvaluationExtraProperty();
			evaluationExtraProperty.setId(id);
			this.commentConfService.delEvaluationExtraProperty(evaluationExtraProperty);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String modEvaluationExtraProperty(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			EvaluationExtraProperty evaluationExtraProperty=new EvaluationExtraProperty();
			evaluationExtraProperty.setId(id);
			evaluationExtraProperty.setPropertyName(propertyName);
			this.commentConfService.modEvaluationExtraProperty(evaluationExtraProperty);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
}
