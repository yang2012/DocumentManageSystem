package dmsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dmsystem.entity.User;
import dmsystem.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean save(User user) {
        try {
            userDao.add(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = getUser(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")   
    public void modify(User user){
    	try {
			userDao.update(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    }
    
    @SuppressWarnings("unchecked")   
    public void delete(User user){
    	try {
			userDao.remove(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
