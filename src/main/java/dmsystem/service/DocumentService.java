package dmsystem.service;

import dmsystem.entity.Document;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.ExtraPropertyWrapper;
import dmsystem.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-23.
 */
public interface DocumentService {

    public List<Document> getAll();

    public Document get(Integer docId);

    public Document upload(User user, Integer documentTypeId, Document transientDocument, List<ExtraPropertyWrapper> extraPropertyWrappers);

    public Document update(Integer documentTypeId, Document transientDocument, List<ExtraPropertyWrapper> extraPropertyWrappers);

    public Set<DocumentExtraProperty> getExtraProperties(Integer documentTypeId);
}
