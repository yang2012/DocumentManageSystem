package dmsystem.service;

import dmsystem.entity.Attachment;

import java.util.List;

/**
 * Created by justinyang on 13-12-24.
 */
public interface AttachmentService {

    public void save(List<Attachment> attachments);

    public void delete(List<Attachment> attachments);
}
