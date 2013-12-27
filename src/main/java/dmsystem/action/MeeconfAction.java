package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.MeetingName;
import dmsystem.entity.User;
import dmsystem.service.MeetingNameService;
import dmsystem.util.Constants;

public class MeeconfAction extends ActionSupport{
	private User user;
	private MeetingNameService meetingNameService;
	private List<MeetingName> meetingNames;
	public String getmeeconf(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			meetingNames=this.meetingNameService.getAll();
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
}
