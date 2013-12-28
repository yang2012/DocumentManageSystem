package dmsystem.entity;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;;

/**
 * Evaluation generated by Justin Yang
 */
@Entity
@Table(name = "Evaluation", catalog = "mydb")
public class Evaluation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2528726048882997643L;
	private int id;
	private String content;
	private Integer type;
	private Integer point;
	private Boolean published;
    private Date createTime;
	private Document document;
	private User user;

	private Set<EvaluationWithExtraProperty> extraProperties = new HashSet<EvaluationWithExtraProperty>(0);

	public Evaluation() {
	}

	public Evaluation(int id, Document document, User user) {
		this.id = id;
		this.setDocument(document);
		this.setUser(user);
	}

	public Evaluation(int id, String content, Integer type, Integer point,
			Boolean published, Document document, User user) {
		this.id = id;
		this.content = content;
		this.type = type;
		this.point = point;
		this.published = published;
		this.setDocument(document);
		this.setUser(user);
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

	@Column(name = "content", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "published")
	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "docId", nullable = false)
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.evaluation")
	public Set<EvaluationWithExtraProperty> getExtraProperties() {
		return extraProperties;
	}

	public void setExtraProperties(Set<EvaluationWithExtraProperty> extraProperties) {
		this.extraProperties = extraProperties;
	}

    public void updateBaseInfo(Evaluation evaluation) {
        if (evaluation == null) {
            return;
        }

        String content = evaluation.getContent();
        if (content != null && !content.equals(this.content)) {
            this.content = content;
        }

        Integer point = evaluation.getPoint();
        if (point != this.point) {
            this.point = point;
        }

        Boolean published = evaluation.getPublished();
        if (published != this.published) {
            this.published = published;
        }

        Integer type = evaluation.getType();
        if (type != this.type) {
            this.type = type;
        }
    }

    @Transient
    public String getFormatTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(this.createTime);
    }
}
