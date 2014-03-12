package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dmsystem.entity.Attachment;
import dmsystem.entity.Document;
import dmsystem.util.FileUtility;
import dmsystem.util.HibernateUtil;

/**
 * Utility object for domain model class Attachment.
 * @see dmsystem.entity.Attachment
 * @author Justin Yang
 */
public class AttachmentDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
	
	public void add(Attachment transientInstance) throws Exception {
		hibernateUtil.persist(transientInstance);
	}

	public void remove(Attachment persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(Attachment detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public Attachment findById(String id) throws Exception {
		return (Attachment) hibernateUtil.findById(Attachment.class, id);
	}
	
	public void removeAttachments(List<String> attachmentIds) throws Exception {
		for (String attachmentId : attachmentIds) {
			Attachment attachment = this.findById(attachmentId);
			this.remove(attachment);
		}
	}
	
}
