package dmsystem.entity;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * DocumentType generated by Justin Yang
 */
public class DocumentType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7939384004380348620L;
	private String id;
	private String name;
    private Set<DocumentExtraProperty> extraProperties = new HashSet<DocumentExtraProperty>(
            0);
	private transient Set<Document> documents = new HashSet<Document>(0);

	public DocumentType() {
	}

	public DocumentType(String id) {
		this.id = id;
	}

	public DocumentType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	public Set<DocumentExtraProperty> getExtraProperties() {
		return extraProperties;
	}

	public void setExtraProperties(Set<DocumentExtraProperty> extraProperties) {
		this.extraProperties = extraProperties;
	}

}
