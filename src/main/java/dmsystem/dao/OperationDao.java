package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import com.google.gson.Gson;
import dmsystem.entity.Operation;
import dmsystem.util.HBaseUtil;
import dmsystem.util.HibernateUtil;
import org.apache.hadoop.hbase.thrift.generated.Hbase;

import java.util.Calendar;

/**
 * Utility object for domain model class Operation.
 * 
 * @see dmsystem.entity.Operation
 * @author Justin Yang
 */
public class OperationDao {
    private String table = "Users";
    private String operationFamily = "Ops";

    private HBaseUtil hBaseUtil;
    private Gson gson = new Gson();

    public void sethBaseUtil(HBaseUtil hBaseUtil) {
        this.hBaseUtil = hBaseUtil;
    }


	public void add(Operation transientInstance) throws Exception {
        String id = this._generateRowKey(transientInstance);
        transientInstance.setId(id);
        String json = this.gson.toJson(transientInstance);
		this.hBaseUtil.put(this.table, transientInstance.getUserId(), this.operationFamily, id, json);
	}

	public void remove(Operation persistentInstance) throws Exception {
		this.hBaseUtil.delete(this.table, persistentInstance.getUserId(), this.operationFamily, persistentInstance.getId());
	}

	public void update(Operation detachedInstance) throws Exception {
		if (detachedInstance != null) {
            String json = this.gson.toJson(detachedInstance);
            this.hBaseUtil.put(this.table, detachedInstance.getUserId(), this.operationFamily, detachedInstance.getId(), json);
		}
	}

	public Operation findById(String userId, String operationId) throws Exception {
        Operation operation = null;
        String json = this.hBaseUtil.get(this.table, userId, this.operationFamily, operationId);
        if (json != null) {
            operation = this.gson.fromJson(json, Operation.class);
        }
		return operation;
	}

    private String _generateRowKey(Operation operation) {
        long reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        return  reverseTimestamp + "";
    }

}
