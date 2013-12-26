package dmsystem.service;

import java.util.List;

import dmsystem.dao.EvaluationExtrapropertyDao;
import dmsystem.entity.EvaluationExtraProperty;

public class CommentConfServiceImpl implements CommentConfService{
	EvaluationExtrapropertyDao evaluationExtrapropertyDao;
	public void setEvaluationExtrapropertyDao(
			EvaluationExtrapropertyDao evaluationExtrapropertyDao) {
		this.evaluationExtrapropertyDao = evaluationExtrapropertyDao;
	}
	public List<EvaluationExtraProperty> getAll(){
		try {
			return this.evaluationExtrapropertyDao.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void addEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty) {
		this.evaluationExtrapropertyDao.addEvaluationExtraProperty(evaluationExtraProperty);
		
	}
	public void delEvaluationExtraProperty(
			EvaluationExtraProperty evaluationExtraProperty) {
		this.evaluationExtrapropertyDao.delEvaluationExtraProperty(evaluationExtraProperty);
		
	}
	
	public void modEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty){
		this.evaluationExtrapropertyDao.modEvaluationExtraProperty(evaluationExtraProperty);
	}
}
