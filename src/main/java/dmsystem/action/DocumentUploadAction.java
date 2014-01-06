package dmsystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dmsystem.entity.Document;
import dmsystem.entity.DocumentExtraProperty;
import dmsystem.entity.DocumentType;
import dmsystem.entity.DocumentWithExtraProperty;
import dmsystem.entity.Operation;
import dmsystem.util.Constants;
import dmsystem.util.DateUtil;
import dmsystem.util.Wrapper.DocumentExtraPropertyWrapper;
import dmsystem.entity.User;
import dmsystem.service.DocumentService;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.OperationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by justinyang on 13-12-25.
 */
public class DocumentUploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5051870815157756643L;
	private DocumentService documentService;
	private DocumentTypeService documentTypeService;
	private OperationService operationService;

	private Integer docId;

	private User user;

	private Document document;

	private Integer documentTypeId;
	private List<DocumentType> documentTypes;
	private List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers;

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Integer documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public List<DocumentExtraPropertyWrapper> getDocumentExtraPropertyWrappers() {
		return documentExtraPropertyWrappers;
	}

	public void setDocumentExtraPropertyWrappers(
			List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers) {
		this.documentExtraPropertyWrappers = documentExtraPropertyWrappers;
	}

	public String showUpload() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		documentTypes = this.documentTypeService.getAll();
		return SUCCESS;
	}

	public String commitUpload() throws Exception {
		this.user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		Document persistentDocument = this.documentService.upload(this.user,
				documentTypeId, this.document,
				this.documentExtraPropertyWrappers);

		Operation operation = new Operation();
		operation.setExpression(Constants.importDocExpression);
		operation.setTime(DateUtil.getCurrentDate());
		operation.setType(Constants.importDocType);
		operation.setUser(user);
		this.operationService.addOperation(operation);

		if (persistentDocument != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public OperationService getOperationService() {
		return operationService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public String showModification() {
		this.user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		this.documentTypes = this.documentTypeService.getAll();
		this.document = this.documentService.get(this.docId);

		if (this.document == null) {
			return ERROR;
		} else {
			return SUCCESS;
		}
	}

	public String commitModification() {
		Document persistentDocument = this.documentService.update(this.docId,
				this.documentTypeId, this.document,
				this.documentExtraPropertyWrappers);
		return SUCCESS;
	}

	public String getExtraproperties() {
		Set<DocumentExtraProperty> extraProperties = this.documentTypeService
				.getExtraProperties(this.documentTypeId);
		this.documentExtraPropertyWrappers = this._convert(extraProperties);
		return SUCCESS;
	}

	private List<DocumentExtraPropertyWrapper> _convert(
			Set<DocumentExtraProperty> extraProperties) {
		List<DocumentExtraPropertyWrapper> documentExtraPropertyWrappers = new ArrayList<DocumentExtraPropertyWrapper>(
				0);
		for (DocumentExtraProperty extraProperty : extraProperties) {

			DocumentExtraPropertyWrapper documentExtraPropertyWrapper = new DocumentExtraPropertyWrapper();
			documentExtraPropertyWrapper.setExtraPropertyName(extraProperty
					.getPropertyName());
			if (this.docId != null) {
				this.document = new Document();
				document.setId(docId);
				DocumentWithExtraProperty documentWithExtraProperty = this.documentService
						.getDocumentExtraProperty(this.document, extraProperty);
				if (documentWithExtraProperty != null) {
					documentExtraPropertyWrapper
							.setExtraPropertyValue(documentWithExtraProperty
									.getPropertyValue());
				}
			}

			documentExtraPropertyWrapper.setExtraPropertyId(extraProperty
					.getId());

			documentExtraPropertyWrappers.add(documentExtraPropertyWrapper);
		}
		return documentExtraPropertyWrappers;
	}
}
