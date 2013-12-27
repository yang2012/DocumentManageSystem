package dmsystem.service;

import java.util.List;

import dmsystem.entity.MeetingName;

public interface MeetingNameService {
	public List<MeetingName> getAll();
	public void addMeetingName(MeetingName meetingName);
	public void delMeetingName(MeetingName meetingName);
}
