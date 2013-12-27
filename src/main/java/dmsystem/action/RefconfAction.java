package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.RelationType;
import dmsystem.entity.User;
import dmsystem.service.ReferenceConfService;
import dmsystem.util.Constants;

public class RefconfAction extends ActionSupport{
	private User user;
	private List<RelationType> relationtypes;
	private ReferenceConfService referenceConfService;
	private String name;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RelationType> getRelationtypes() {
		return relationtypes;
	}
	public void setRelationtypes(List<RelationType> relationtypes) {
		this.relationtypes = relationtypes;
	}
	public ReferenceConfService getReferenceConfService() {
		return referenceConfService;
	}
	public void setReferenceConfService(ReferenceConfService referenceConfService) {
		this.referenceConfService = referenceConfService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getrefconf(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			relationtypes=this.referenceConfService.getRelationType();
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	public String addRelation(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			RelationType relationType=new RelationType();
			relationType.setName(name);
			this.referenceConfService.addRelationType(relationType);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	public String delRelation(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			RelationType relationType=new RelationType();
			relationType.setId(id);
			this.referenceConfService.delRelationType(relationType);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String modRelation(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			RelationType relationType=new RelationType();
			relationType.setId(id);
			relationType.setName(name);
			this.referenceConfService.modRelationType(relationType);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
}
