package dmsystem.service;

import dmsystem.entity.User;

import java.util.List;

public interface UserService {
	
	boolean save(User user);
	
	void modify(User user);
	void delete(User user);

    public User authenticate(String username, String password);

    public List<User> getAllUsers();
}
