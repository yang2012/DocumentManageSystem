package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dmsystem.entity.Attachment;
import dmsystem.entity.Document;
import dmsystem.util.FileUtility;
import dmsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;

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

	public Attachment findById(int id) throws Exception {
		return (Attachment) hibernateUtil.findById(Attachment.class, id);
	}

	public void addAttachments(Integer documentId, List<File> files) throws Exception {
		DocumentDao documentDao = new DocumentDao();
		Document document = documentDao.findById(documentId);
		if (document == null) {
			throw new Exception("Cannot find document with id: " + documentId);
		}
		
		List<Attachment> attachments = new ArrayList<Attachment>(0);
		for (File file : files) {
			Attachment attachment = new Attachment();
			attachment.setName(file.getName());
			attachment.setUrl(FileUtility.getFileUrl(file.getName()));
			attachment.setDocument(document);
			
			attachments.add(attachment);
		}
		
		hibernateUtil.persist(attachments);
	}
	
	public void removeAttachments(List<Integer> attachmentIds) throws Exception {
		for (Integer attachmentId : attachmentIds) {
			Attachment attachment = this.findById(attachmentId);
			this.remove(attachment);
		}
	}
}
