package dmsystem.service;

import dmsystem.entity.User;

public interface UserService {
	
	void save(User user);

    public User authenticate(String username, String password);
}
