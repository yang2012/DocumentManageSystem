package dmsystem.service;

import dmsystem.dao.AttachmentDao;
import dmsystem.dao.DocumentDao;
import dmsystem.entity.Attachment;
import dmsystem.entity.Document;

import java.util.List;

/**
 * Created by justinyang on 13-12-24.
 */
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentDao attachmentDao;
    private DocumentDao documentDao;

    public void setAttachmentDao(AttachmentDao attachmentDao) {
        this.attachmentDao = attachmentDao;
    }

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    public Attachment upload(Integer documentId, Attachment attachment) {
        Attachment persistentAttachment = attachment;
        try {
            Document document = this.documentDao.findById(documentId);

            persistentAttachment.setDocument(document);
            this.attachmentDao.add(persistentAttachment);
        } catch (Exception e) {
            e.printStackTrace();

            persistentAttachment = null;
        }
        return persistentAttachment;
    }

    @Override
    public Boolean delete(Attachment attachment) {
        Boolean result = false;
        if (attachment == null) {
            return result;
        }

        try {
            Attachment persistentAttachment = this.attachmentDao.findById(attachment.getId());
            this.attachmentDao.remove(persistentAttachment);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
