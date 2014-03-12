package dmsystem.entity;

public class TagDefined {
	private int id;
	private String name;
	public TagDefined(){
		
	}
	public TagDefined(int id) {
		this.id = id;
	}

	public TagDefined(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
