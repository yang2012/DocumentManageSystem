package dmsystem.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.TagDefined;
import dmsystem.entity.User;
import dmsystem.service.TagDefinedService;
import dmsystem.util.Constants;

public class TagDefinedAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private List<TagDefined> definedTags;
	private TagDefinedService tagDefinedService;
	private String name;
	private int id;
	private ArrayList<String> tagsJson;

	public ArrayList<String> getTagsJson() {
		return tagsJson;
	}

	public void setTagsJson(ArrayList<String> tagsJson) {
		this.tagsJson = tagsJson;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TagDefined> getDefinedTags() {
		return definedTags;
	}

	public void setDefinedTags(List<TagDefined> definedTags) {
		this.definedTags = definedTags;
	}

	public TagDefinedService getTagDefinedService() {
		return tagDefinedService;
	}

	public void setTagDefinedService(TagDefinedService tagDefinedService) {
		this.tagDefinedService = tagDefinedService;
	}

	public String gettagconf() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			definedTags = this.tagDefinedService.getAll();
			result = SUCCESS;
		} else {
			result = LOGIN;
		}
		return result;
	}

	public String gettagjson() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		definedTags = this.tagDefinedService.getAll();
		tagsJson = new ArrayList<String>();
		for (int i = 0; i < definedTags.size(); i++) {
			tagsJson.add(definedTags.get(i).getName());
		}
		result = SUCCESS;
		return result;
	}

	public String addDefinedTag() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			TagDefined tagDefined = new TagDefined();
			tagDefined.setName(name);
			this.tagDefinedService.add(tagDefined);
			result = SUCCESS;
		} else {
			result = LOGIN;
		}
		return result;
	}

	public String delDefinedTag() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			TagDefined tagDefined = new TagDefined();
			tagDefined.setId(id);
			this.tagDefinedService.del(tagDefined);
			result = SUCCESS;
		} else {
			result = LOGIN;
		}
		return result;
	}

	public String modDefinedTag() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			TagDefined tagDefined = new TagDefined();
			tagDefined.setId(id);
			tagDefined.setName(name);
			this.tagDefinedService.mod(tagDefined);
			result = SUCCESS;
		} else {
			result = LOGIN;
		}
		return result;
	}
}
