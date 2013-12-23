package dmsystem.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * EvaluationWithExtraPropertyId generated by Justin Yang
 */
@Embeddable
public class EvaluationWithExtraPropertyId implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9029371507082666635L;
	private Evaluation evaluation;
	private EvaluationExtraProperty evaluationExtraProperty;

	public EvaluationWithExtraPropertyId() {
	}

	public EvaluationWithExtraPropertyId(Evaluation evaluation,
			EvaluationExtraProperty evaluationExtraProperty) {
		this.setEvaluation(evaluation);
		this.setEvaluationExtraProperty(evaluationExtraProperty);
	}

	@ManyToOne
	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	@ManyToOne
	public EvaluationExtraProperty getEvaluationExtraProperty() {
		return evaluationExtraProperty;
	}

	public void setEvaluationExtraProperty(
			EvaluationExtraProperty evaluationExtraProperty) {
		this.evaluationExtraProperty = evaluationExtraProperty;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DocumentWithExtraPropertyId))
			return false;
		EvaluationWithExtraPropertyId castOther = (EvaluationWithExtraPropertyId) other;

		return (this.getEvaluation() == castOther.getEvaluation())
				&& (this.getEvaluationExtraProperty() == castOther
						.getEvaluationExtraProperty());
	}

	public int hashCode() {
		int result = 17;

		result = (this.evaluation != null ? this.evaluation.hashCode() : 0);
		result = 31
				* result
				+ (this.evaluationExtraProperty != null ? this.evaluationExtraProperty
						.hashCode() : 0);
		return result;
	}
}
