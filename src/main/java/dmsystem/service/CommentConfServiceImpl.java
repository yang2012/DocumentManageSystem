package dmsystem.service;

import java.util.List;

import dmsystem.dao.EvaluationExtraPropertyDao;
import dmsystem.entity.EvaluationExtraProperty;

public class CommentConfServiceImpl implements CommentConfService{
	EvaluationExtraPropertyDao evaluationExtrapropertyDao;
	public void setEvaluationExtraPropertyDao(
			EvaluationExtraPropertyDao evaluationExtrapropertyDao) {
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
        try {
            this.evaluationExtrapropertyDao.add(evaluationExtraProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	public void delEvaluationExtraProperty(
			EvaluationExtraProperty evaluationExtraProperty) {
        try {
            this.evaluationExtrapropertyDao.remove(evaluationExtraProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	public void modEvaluationExtraProperty(EvaluationExtraProperty evaluationExtraProperty){
        try {
            this.evaluationExtrapropertyDao.update(evaluationExtraProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
