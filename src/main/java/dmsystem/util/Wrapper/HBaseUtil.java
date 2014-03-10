package dmsystem.util.Wrapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by justinyang on 14-3-10.
 */
public class HBaseUtil {
    Configuration config = null;
    HBaseAdmin admin = null;

    private static HBaseUtil _instance;

    public static HBaseUtil defaultInstance() throws MasterNotRunningException, ZooKeeperConnectionException {
        if (_instance == null) {
            _instance = new HBaseUtil();

            Configuration config = HBaseConfiguration.create();
            _instance.config = config;
            _instance.admin = new HBaseAdmin(config);
        }
        return _instance;
    }

    public void put(String table, String rowKey, String family, String qualifier, byte[] data) throws IOException {
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), data);
        hTable.put(put);
    }

    public byte[] get(String table, String rowKey, String family, String qualifier) throws IOException {
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Get g = new Get(Bytes.toBytes(rowKey));
        Result result = hTable.get(g);
        return result.value();
    }
}
