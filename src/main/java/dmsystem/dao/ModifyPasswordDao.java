package dmsystem.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import dmsystem.entity.User;
import dmsystem.util.HibernateUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class ModifyPasswordDao {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public String retrievePassword(String username) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select u.password from User u where u.username=?");
		query.setString(0, username);
		return (String) query.list().get(0);
	}

	public void updateUser(User user) {
		try {
			this.hibernateUtil.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
