package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmsystem.util.HBaseUtil;
import dmsystem.util.StringUtil;

import dmsystem.entity.User;
import dmsystem.util.Constants;

import java.util.ArrayList;
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
	private Gson gson = new Gson();

	public void sethBaseUtil(HBaseUtil hBaseUtil) {
		this.hBaseUtil = hBaseUtil;
	}

	private String table = "Users";
	private String infoFamily = "Info";
	private String basicInfoQualifier = "Basis";
	private String opFamily = "Ops";

	public User getUser(String username) {
		if (username == null) {
			return null;
		}

		User user = null;
		try {
			String rowKey = StringUtil.md5(username);
			user = this.findById(rowKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public void add(User user) throws Exception {
		String rowKey = StringUtil.md5(user.getUsername());
		String jsonData = this.gson.toJson(user);
		this.hBaseUtil.put(this.table, rowKey, this.infoFamily,
				this.basicInfoQualifier, jsonData);
	}

	public void add(Map<String, String> values) throws Exception {
		User transientInstance = new User();
		transientInstance = this._update(transientInstance, values);
		this.add(transientInstance);
	}

	public void remove(User user) throws Exception {
		String json = this.hBaseUtil.get(this.table,
				StringUtil.md5(user.getUsername()), this.infoFamily,
				this.basicInfoQualifier);
		User u = new Gson().fromJson(json, User.class);
		u.setAuthority(0);
		json = new Gson().toJson(u);
		System.out.println(json);
		this.hBaseUtil.put(this.table, StringUtil.md5(user.getUsername()),
				this.infoFamily, this.basicInfoQualifier, json);
	}

	public void update(User detachedInstance) throws Exception {
		this.add(detachedInstance);
	}

	public User findById(String id) throws Exception {
		User user = null;
		String json = this.hBaseUtil.get(this.table, id, this.infoFamily,
				this.basicInfoQualifier);
		if (json != null) {
			Gson gson = new Gson();
			user = gson.fromJson(json, User.class);
		}
		return user;
	}

	public List<User> getAllUser() throws Exception {
		List<String> jsons = this.hBaseUtil.getAll(this.table, this.infoFamily,
				this.basicInfoQualifier);
		List<User> users = new ArrayList<User>();
		Gson gson = new Gson();
		for (String json : jsons) {
			User u = gson.fromJson(json, User.class);
			if (u.getAuthority() != 0) {
				users.add(u);
			}
		}
		return users;
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
