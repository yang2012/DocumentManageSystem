package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.User;
import dmsystem.util.Constants;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility object for domain model class User.
 * 
 * @see dmsystem.entity.User
 * @author Justin Yang
 */
public class UserDao {

    private HibernateUtil hibernateUtil;

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public User getUser(String username) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User user where user.username = :username");
            query.setString("username", username);
            query.setMaxResults(1);
            return (User)query.uniqueResult();
        } finally {
            session.close();
        }
    }

    public void addUser(User user) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.save(user);
            ts.commit();
        } finally {
            session.close();
        }
    }

	public void add(Map<String, String> values) throws Exception {
		User transientInstance=new User();
		transientInstance=this._update(transientInstance,values);
		hibernateUtil.persist(transientInstance);
	}

	public void remove(User persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(User detachedInstance) throws Exception {
		if (detachedInstance != null) {
			hibernateUtil.update(detachedInstance);
		}
	}

	public User findById(int id) throws Exception {
		return (User) hibernateUtil.findById(User.class, id);
	}

	public User findByUsername(String username) throws Exception {
		Session session = hibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Query query = session
				.createQuery("from User user where user.username=?");
		Object dbResult = query.setString(0, username).uniqueResult();

		if (dbResult != null) {
			return (User) dbResult;
		} else {
			return null;
		}
	}

    @SuppressWarnings("unchecked")
    public List<User> getAllUser() throws Exception {
        return hibernateUtil.getAll(User.class, "name", true);
    }
    
    private User _update(User user, Map<String, String> values) throws Exception {
    	Set<String> keys = values.keySet();
    	for(String key :keys){
    		if (key.equals(Constants.kUsernameField)) {
                String username = values.get(key);
                user.setUsername(username);
                user.setPassword(username);
            } else if(key.equals(Constants.kNameField)) {
                String name = values.get(key);
                user.setName(name);
            } else if(key.equals(Constants.kAuthorityField)) {
                Integer authority = Integer.parseInt(values.get(key));
                user.setAuthority(authority);
            } 
    	}          
        return user;
    }
}
