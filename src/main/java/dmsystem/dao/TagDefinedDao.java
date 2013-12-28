package dmsystem.dao;

import java.util.List;

import dmsystem.entity.TagDefined;
import dmsystem.util.HibernateUtil;

public class TagDefinedDao {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}
	@SuppressWarnings("unchecked")
	public List<TagDefined> getAll(){
		try {
			return hibernateUtil.getAll(TagDefined.class, "id", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
