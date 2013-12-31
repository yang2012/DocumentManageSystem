package dmsystem.service;

import dmsystem.dao.ModifyPasswordDao;
import dmsystem.entity.User;

public class ModifyPasswordServiceImpl implements ModifyPasswordService {
	private ModifyPasswordDao modifyPasswordDao;

	public ModifyPasswordDao getModifyPasswordDao() {
		return modifyPasswordDao;
	}

	public void setModifyPasswordDao(ModifyPasswordDao modifyPasswordDao) {
		this.modifyPasswordDao = modifyPasswordDao;
	}

	public String retrievePassword(String username) {
		return this.modifyPasswordDao.retrievePassword(username);
	}

	public void updatePassword(User user) {
		this.modifyPasswordDao.updateUser(user);
	}

}
