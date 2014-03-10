package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import dmsystem.util.Wrapper.HBaseUtil;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.util.Bytes;

import dmsystem.entity.User;
import dmsystem.util.Constants;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private HBaseUtil hBaseUtil;

	public User getUser(String username) {

        try {
            this.hBaseUtil = HBaseUtil.defaultInstance();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] rowKey = messageDigest.digest(username.getBytes());
            this.hBaseUtil.put("Users", "123123123", "Basis", "info", Bytes.toBytes("JustinYang"));

            byte[] result = this.hBaseUtil.get("Users", "123123123", "Basis", "info");
            System.out.println(result);
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

//		Session session = hibernateUtil.getSessionFactory().openSession();
//		try {
//			Query query = session
//					.createQuery("from User user where user.username = :username");
//			query.setString("username", username);
//			query.setMaxResults(1);
//			return (User) query.uniqueResult();
//		} finally {
//			session.close();
//		}
	}

	public void add(User user) throws Exception {
//		hibernateUtil.persist(user);
	}

	public void add(Map<String, String> values) throws Exception {
		User transientInstance = new User();
		transientInstance = this._update(transientInstance, values);
//		hibernateUtil.persist(transientInstance);
	}

	public void remove(User persistentInstance) throws Exception {
//		hibernateUtil.remove(persistentInstance);
	}

	public void update(User detachedInstance) throws Exception {
//		hibernateUtil.update(detachedInstance);
	}

	public User findById(int id) throws Exception {
//		return (User) hibernateUtil.findById(User.class, id);
        return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() throws Exception {
//		return hibernateUtil.getAll(User.class, "id", true);
        return null;
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
