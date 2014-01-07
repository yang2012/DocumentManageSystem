package dmsystem.service;

import dmsystem.entity.User;

/**
 * 
 * @author bryant zhang
 * 
 */
public interface ModifyPasswordService {
	public String retrievePassword(String username);

	public void updatePassword(User user);
}
