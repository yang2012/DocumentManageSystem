package dmsystem.service;

import dmsystem.dao.OperationDao;
import dmsystem.entity.Operation;
import dmsystem.entity.User;
import dmsystem.util.DateUtil;

import dmsystem.util.Constants;

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

    public void addOperation(User user, Integer type) {
        Operation operation = new Operation();
        operation.setExpression(this._getExpression(type));
        operation.setTime(DateUtil.getCurrentDate());
        operation.setType(type);
        operation.setUserId(user.getId());

        try {
            this.operationDao.add(operation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOperation(Operation operation) throws Exception {
		this.operationDao.add(operation);
	}

    private String _getExpression(Integer type) {
        String expression = null;
        switch (type) {
            case Constants.kImportDocOperationType:
                expression = "Import a document.";
                break;
            case Constants.kUploadAttachmentOperationType:
                expression = "Upload an attachment.";
                break;
            case Constants.kSimpleCommentOperationType:
                expression = "Publish a simple comment.";
                break;
            case Constants.kDetailedCommentOperationType:
                expression = "Publish a detailed comment";
                break;
            default:
                expression = "";
                break;
        }
        return expression;
    }
}
