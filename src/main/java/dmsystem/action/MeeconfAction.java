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
	private String shortname;
	private String fullname;
	private List<MeetingName> meetingNames;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MeetingNameService getMeetingNameService() {
		return meetingNameService;
	}
	public void setMeetingNameService(MeetingNameService meetingNameService) {
		this.meetingNameService = meetingNameService;
	}
	public List<MeetingName> getMeetingNames() {
		return meetingNames;
	}
	public void setMeetingNames(List<MeetingName> meetingNames) {
		this.meetingNames = meetingNames;
	}
	
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
	
	public String addMeetingName(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			MeetingName meetingName=new MeetingName();
			meetingName.setFullName(fullname);
			meetingName.setShortName(shortname);
			this.meetingNameService.addMeetingName(meetingName);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
	
	public String delMeetingName(){
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		if (user == null) {
			return LOGIN;
		}
		String result = null;
		if (user.getAuthority().equals(Constants.kAdminAuthority)) {
			MeetingName meetingName=new MeetingName();
			meetingName.setId(id);
			this.meetingNameService.delMeetingName(meetingName);
			result = SUCCESS;
		} else {
			return LOGIN;
		}
		return result;
	}
}
