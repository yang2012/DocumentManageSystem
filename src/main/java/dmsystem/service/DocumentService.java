package dmsystem.service;

import dmsystem.entity.Document;
import dmsystem.util.Wrapper.DocumentExtraPropertyWrapper;
import dmsystem.entity.User;

import java.util.List;

/**
 * Created by justinyang on 13-12-23.
 */
public interface DocumentService {

    public List<Document> getAll();

    public Document get(Integer docId);

    public Document upload(User user, Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers);

    public Document update(Integer documentTypeId, Document transientDocument, List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers);
}
