package dmsystem.entity;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

;

/**
 * Document generated by Justin Yang
 */
@Entity
@Table(name = "Document", catalog = "mydb")
public class Document implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7162066275065672397L;
	private int id;
	private String title;
	private String author;
	private String year;
	private Integer pages;
	private String abstracts;
	private String keywords;
	private String url;
	private String publisher;
	private Date createTime;
	private DocumentType documentType;
	private User user;

	private Set<DocumentWithExtraProperty> extraProperties = new HashSet<DocumentWithExtraProperty>(
			0);
	private Set<Attachment> attachments = new HashSet<Attachment>(0);
	private Set<Tag> tags = new HashSet<Tag>(0);

    private Set<Evaluation> evaluations = new HashSet<Evaluation>(0);
	private Set<DocumentRelation> refererRelations = new HashSet<DocumentRelation>(
			0);
	private Set<DocumentRelation> refereeRelations = new HashSet<DocumentRelation>(
			0);

	public Document() {
	}

	public Document(int id, DocumentType documentType, User user) {
		this.id = id;
		this.setDocumentType(documentType);
		this.user = user;
	}

	public Document(int id, String title, String author, String year,
			Integer pages, String abstracts, String keywords, String url,
			String publisher, Date createTime, DocumentType documentType,
			User user) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.pages = pages;
		this.abstracts = abstracts;
		this.keywords = keywords;
		this.url = url;
		this.publisher = publisher;
		this.createTime = createTime;
		this.setDocumentType(documentType);
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title", length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "author", length = 100)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "year", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "pages")
	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	@Column(name = "abstracts", length = 400)
	public String getAbstracts() {
		return this.abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	@Column(name = "keywords", length = 150)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "publisher", length = 100)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "docTypeId", nullable = false)
	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false,updatable=false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.document")
	public Set<DocumentWithExtraProperty> getExtraProperties() {
		return extraProperties;
	}

	public void setExtraProperties(
			Set<DocumentWithExtraProperty> extraProperties) {
		this.extraProperties = extraProperties;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "document")
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.referer", cascade = CascadeType.ALL)
	public Set<DocumentRelation> getRefererRelations() {
		return refererRelations;
	}

	public void setRefererRelations(Set<DocumentRelation> refererRelations) {
		this.refererRelations = refererRelations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.referee", cascade = CascadeType.ALL)
	public Set<DocumentRelation> getRefereeRelations() {
		return refereeRelations;
	}

	public void setRefereeRelations(Set<DocumentRelation> refereeRelations) {
		this.refereeRelations = refereeRelations;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Transient
    public List<Evaluation> getPublishedEvaluations() {
        List<Evaluation> publishedEvaluations = new ArrayList<Evaluation>();
        for (Evaluation evaluation : this.evaluations) {
            if (evaluation.getPublished()) {
                publishedEvaluations.add(evaluation);
            }
        }

        Collections.sort(publishedEvaluations, new Comparator<Evaluation>() {
            public int compare(Evaluation o1, Evaluation o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return publishedEvaluations;
    }

    @Transient
    public Integer getAveragePoint() {
        Integer averagePoint = 0;
        Set<Evaluation> evaluations = this.evaluations;
        if (evaluations != null && evaluations.size() != 0) {
            Integer totalPoint = 0;
            for (Evaluation evaluation : evaluations) {
                Integer point = evaluation.getPoint();
                totalPoint += point == null ? 0 : point;
            }

            averagePoint = totalPoint / evaluations.size();
        }
        return averagePoint;
    }

    public void updateInfo(Document document) {
        String title = document.getTitle();
        if (title != null && !title.equals(this.title)) {
            this.title = title;
        }

        String author = document.getAuthor();
        if (author != null && !author.equals(this.author)) {
            this.author = author;
        }

        String url = document.getUrl();
        if (url != null && !url.equals(this.url)) {
            this.url = url;
        }

        String publisher = document.getPublisher();
        if (publisher != null && !publisher.equals(this.publisher)) {
            this.publisher = publisher;
        }

        String abstracts = document.getAbstracts();
        if (abstracts != null && !abstracts.equals(this.abstracts)) {
            this.abstracts = abstracts;
        }

        String keywords = document.getKeywords();
        if (keywords != null && !keywords.equals(this.keywords)) {
            this.keywords = keywords;
        }

        String year = document.getYear();
        if (year != null && !year.equals(this.year)) {
            this.year = year;
        }

        Integer pages = document.getPages();
        if (!pages.equals(this.pages)) {
            this.pages = pages;
        }
    }
}
