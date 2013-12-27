package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.Tag;
import dmsystem.entity.User;
import dmsystem.service.TagService;
import dmsystem.util.Constants;

public class TagAction extends ActionSupport{
	private User user;
	private List<Tag> tags;
	private TagService tagService;
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public TagService getTagService() {
		return tagService;
	}
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String gettagconf(){
		user = (User)ActionContext.getContext().getSession().get(User.SESSION_KEY);
        if (user == null) {
            return LOGIN;
        }
        String result = null;
        if (user.getAuthority().equals(Constants.kAdminAuthority)) {
            tags=this.tagService.getAll();
            result = SUCCESS;
        } else {
            result = LOGIN;
        }
        return result;
	}
}
