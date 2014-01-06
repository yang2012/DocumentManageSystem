package dmsystem.service;

import dmsystem.dao.OperationDao;
import dmsystem.entity.Operation;

/**
 * 
 * @author bryant zhang
 * 
 */
public class OperationServiceImpl implements OperationService {
	private OperationDao operationDao;

	public OperationDao getOperationDao() {
		return operationDao;
	}

	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}

	public void addOperation(Operation operation) throws Exception {
		this.operationDao.add(operation);
	}

}
