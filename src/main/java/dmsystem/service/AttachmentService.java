package dmsystem.service;

import dmsystem.entity.Attachment;

import java.util.List;

/**
 * Created by justinyang on 13-12-24.
 */
public interface AttachmentService {

    public Attachment upload(Integer documentId, Attachment attachment);

    public Boolean delete(Attachment attachment);
}
