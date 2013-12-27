package dmsystem.dao;

import java.util.List;

import dmsystem.entity.MeetingName;
import dmsystem.entity.Tag;
import dmsystem.util.HibernateUtil;

public class MeetingNameDao {
	private HibernateUtil hibernateUtil;
    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(Tag transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(Tag persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(Tag detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public MeetingName findById(int id) throws Exception {
		return (MeetingName) hibernateUtil.findById(MeetingName.class, id);
	}
	
	public List<MeetingName> getAll(){
		try {
			return hibernateUtil.getAll(MeetingName.class, "id", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
