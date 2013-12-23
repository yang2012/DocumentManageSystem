package dmsystem.entity;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * DocumentHasDocumentExtraPropertyId generated by Justin Yang
 */
@Embeddable
public class DocumentWithExtraPropertyId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831077083642056008L;
	private Document document;
	private DocumentExtraProperty documentExtraProperty;

	public DocumentWithExtraPropertyId() {
	}

	public DocumentWithExtraPropertyId(Document document, DocumentExtraProperty documentExtraProperty) {
		this.document = document;
		this.documentExtraProperty = documentExtraProperty;
	}

	@ManyToOne
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@ManyToOne
	public DocumentExtraProperty getDocumentExtraProperty() {
		return this.documentExtraProperty;
	}

	public void setDocumentExtraProperty(DocumentExtraProperty documentExtraProperty) {
		this.documentExtraProperty = documentExtraProperty;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DocumentWithExtraPropertyId))
			return false;
		DocumentWithExtraPropertyId castOther = (DocumentWithExtraPropertyId) other;

		return (this.getDocument() == castOther.getDocument())
				&& (this.getDocumentExtraProperty() == castOther
						.getDocumentExtraProperty());
	}

	public int hashCode() {
		int result = 17;

		result = (this.document != null ? this.document.hashCode() : 0);
        result = 31 * result + (this.documentExtraProperty != null ? this.documentExtraProperty.hashCode() : 0);
        return result;
	}

}
