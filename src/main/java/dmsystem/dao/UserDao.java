package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.User;
import dmsystem.util.Constants;
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
			Query query = session
					.createQuery("from User user where user.username = :username");
			query.setString("username", username);
			query.setMaxResults(1);
			return (User) query.uniqueResult();
		} finally {
			session.close();
		}
	}

	public void add(User user) throws Exception {
		hibernateUtil.persist(user);
	}

	public void add(Map<String, String> values) throws Exception {
		User transientInstance = new User();
		transientInstance = this._update(transientInstance, values);
		hibernateUtil.persist(transientInstance);
	}

	public void remove(User persistentInstance) throws Exception {
		hibernateUtil.remove(persistentInstance);
	}

	public void update(User detachedInstance) throws Exception {
		hibernateUtil.update(detachedInstance);
	}

	public User findById(int id) throws Exception {
		return (User) hibernateUtil.findById(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() throws Exception {
		return hibernateUtil.getAll(User.class, "id", true);
	}

	private User _update(User user, Map<String, String> values)
			throws Exception {
		Set<String> keys = values.keySet();
		for (String key : keys) {
			if (key.equals(Constants.kUsernameField)) {
				String username = values.get(key);
				user.setUsername(username);
				user.setPassword(username);
			} else if (key.equals(Constants.kNameField)) {
				String name = values.get(key);
				user.setName(name);
			} else if (key.equals(Constants.kAuthorityField)) {
				Integer authority = Integer.parseInt(values.get(key));
				user.setAuthority(authority);
			}
		}
		return user;
	}
}
