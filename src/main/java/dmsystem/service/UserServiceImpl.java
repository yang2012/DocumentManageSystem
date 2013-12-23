package dmsystem.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dmsystem.entity.User;
import dmsystem.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(User user) {
		userDao.addUser(user);
	}

    public User authenticate(String username, String password) {
        User u = getUser(username);
        if (u != null && password.equals(u.getPassword())) {
            return u;
        }
        return null;
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = userDao.getAllUser();
        } catch (Exception e) {
            e.printStackTrace();
            // just return empty list
            users = new ArrayList<User>(0);
        }

        return users;
    }
}
