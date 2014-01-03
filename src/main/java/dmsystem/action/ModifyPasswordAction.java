package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.DocumentType;
import dmsystem.entity.User;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.ModifyPasswordService;
import dmsystem.util.StringUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class ModifyPasswordAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;

	private ModifyPasswordService modifyPasswordService;
	private DocumentTypeService documentTypeService;
	private List<DocumentType> documentTypes;

	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public String modifyPassword() {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		documentTypes = this.documentTypeService.getAll();

		if (user == null) {
			return LOGIN;
		}

		if (StringUtil
				.equals(oldPassword, this.modifyPasswordService
						.retrievePassword(user.getUsername()))
				&& StringUtil.equals(newPassword, newPasswordConfirm)) {
			user.setPassword(newPassword);
			this.modifyPasswordService.updatePassword(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	public ModifyPasswordService getModifyPasswordService() {
		return modifyPasswordService;
	}

	public void setModifyPasswordService(
			ModifyPasswordService modifyPasswordService) {
		this.modifyPasswordService = modifyPasswordService;
	}
}
