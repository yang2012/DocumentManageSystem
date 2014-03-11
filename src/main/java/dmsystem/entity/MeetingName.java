package dmsystem.entity;

public class MeetingName implements java.io.Serializable {
	private static final long serialVersionUID = 2139000863282894997L;
	private int id;
	private String fullName;
	private String shortName;
	
	public MeetingName(){}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
