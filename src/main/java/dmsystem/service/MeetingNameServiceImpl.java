package dmsystem.service;

import java.util.List;

import dmsystem.dao.MeetingNameDao;
import dmsystem.entity.MeetingName;

public class MeetingNameServiceImpl implements MeetingNameService {
	private MeetingNameDao meetingNameDao;
	public MeetingNameDao getMeetingNameDao() {
		return meetingNameDao;
	}
	public void setMeetingNameDao(MeetingNameDao meetingNameDao) {
		this.meetingNameDao = meetingNameDao;
	}
	public List<MeetingName> getAll() {
		// TODO Auto-generated method stub
		return this.meetingNameDao.getAll();
	}

}
