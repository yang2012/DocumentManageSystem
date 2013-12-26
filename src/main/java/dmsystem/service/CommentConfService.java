package dmsystem.service;

import java.util.List;

import dmsystem.entity.EvaluationExtraProperty;

public interface CommentConfService {
	public List<EvaluationExtraProperty> getAll();	
	public void addEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty);
	public void delEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty);
	public void modEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty);
}
