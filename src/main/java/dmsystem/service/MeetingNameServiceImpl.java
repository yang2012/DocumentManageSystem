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
	public void addMeetingName(MeetingName meetingName) {
		// TODO Auto-generated method stub
		try {
			this.meetingNameDao.add(meetingName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delMeetingName(MeetingName meetingName){
		try {
			this.meetingNameDao.remove(meetingName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
