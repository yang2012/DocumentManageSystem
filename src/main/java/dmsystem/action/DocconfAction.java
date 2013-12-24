package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.User;
import dmsystem.service.UserService;
import dmsystem.util.Constants;

public class DocconfAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7832026651557331002L;
	private User user;
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getdocconf() throws Exception{
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}

}
