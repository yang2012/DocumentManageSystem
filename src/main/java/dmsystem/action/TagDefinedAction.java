package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.TagDefined;
import dmsystem.entity.User;
import dmsystem.service.TagDefinedService;
import dmsystem.util.Constants;

public class TagDefinedAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private List<TagDefined> definedTags;
	private TagDefinedService tagDefinedService;

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

	public String gettagconf(){
		user = (User)ActionContext.getContext().getSession().get(User.SESSION_KEY);
        if (user == null) {
            return LOGIN;
        }
        String result = null;
        if (user.getAuthority().equals(Constants.kAdminAuthority)) {
            definedTags=this.tagDefinedService.getAll();
            result = SUCCESS;
        } else {
            result = LOGIN;
        }
        return result;
	}
}
