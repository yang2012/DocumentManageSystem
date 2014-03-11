package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;
import dmsystem.service.UserService;
import dmsystem.util.Constants;

public class UserManageAction extends ActionSupport {
	private User user;
	private String username;
	private String name;
	private Integer authority;
	private UserService userService;
	private List<User> users;
	private String id;
	private String success;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		System.out.print("here");
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = Integer.parseInt(authority);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String adduser() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			User newuser = new User();
			newuser.setAuthority(authority);
			newuser.setName(name);
			newuser.setUsername(username);
			newuser.setPassword(username);
			if(userService.save(newuser)){
				success="true";
			}else{
				success="false";
			}
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}

	public String getusers() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			users = this.userService.getAllUsers();
			if(success==null){
				success="true";
			}
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}

	public String moduser() {
		String result = null;
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			User moduser=new User();
			moduser.setAuthority(authority);
			moduser.setId(id);
			moduser.setName(name);
			moduser.setPassword(username);
			moduser.setUsername(username);
			userService.modify(moduser);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String deluser(){
		String result=null;
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if(user==null){
			return LOGIN;
		}
		if(user.getAuthority().equals(Constants.kAdminAuthority)){
			User deluser=new User();
			deluser.setId(id);
			userService.delete(deluser);
			result=SUCCESS;
		}
		return result;
	}
}
