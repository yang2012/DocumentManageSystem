package dmsystem.entity;

/**
 * 
 * @author bryant zhang
 * 
 */
public class Statistic {
	private String userId;
	private String username;
	private Integer docimport;
	private Integer uploadattachment;
	private Integer simplecomment;
	private Integer detailedcomment;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getDocimport() {
		return docimport;
	}

	public void setDocimport(Integer docimport) {
		this.docimport = docimport;
	}

	public Integer getUploadattachment() {
		return uploadattachment;
	}

	public void setUploadattachment(Integer uploadattachment) {
		this.uploadattachment = uploadattachment;
	}

	public Integer getSimplecomment() {
		return simplecomment;
	}

	public void setSimplecomment(Integer simplecomment) {
		this.simplecomment = simplecomment;
	}

	public Integer getDetailedcomment() {
		return detailedcomment;
	}

	public void setDetailedcomment(Integer detailedcomment) {
		this.detailedcomment = detailedcomment;
	}
}
