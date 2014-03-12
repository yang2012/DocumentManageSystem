package dmsystem.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justinyang on 14-3-10.
 */
public class HBaseUtil {

    HbaseTemplate hbaseTemplate;

    Configuration config = null;
    HBaseAdmin admin = null;

    public void setHbaseTemplate(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    public HBaseUtil() {
        Configuration config = HBaseConfiguration.create();
        this.config = config;
        try {
            this.admin = new HBaseAdmin(config);
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }

    public void put(String table, String rowKey, String family, String qualifier, String jsonData) throws IOException, NoSuchAlgorithmException {
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(jsonData));
        hTable.put(put);
    }

    public String get(String table, String rowKey, String family, String qualifier) throws IOException {
        Result result = this.hbaseTemplate.get(table, rowKey, family, qualifier, new RowMapper<Result>() {
            @Override
            public Result mapRow(Result result, int rowNum) throws Exception {
                return result;
            }
        });

        byte[] bytes = result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        return new String(bytes);
    }

    public List<String> find(String table, String family) {
        return this.hbaseTemplate.find(table, family, new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }
        });
    }

    public List<String> getAll(String table, String family, String qualifier) throws IOException {
        List<String> jsons = new ArrayList<String>();

        Scan scan = new Scan();
        byte[] familyBytes = Bytes.toBytes(family);
        byte[] qualifierBytes = Bytes.toBytes(qualifier);
        scan.addColumn(familyBytes, qualifierBytes);

        HTable hTable = new HTable(this.config, Bytes.toBytes(table));

        ResultScanner scanner = hTable.getScanner(scan);
        Result result = null;
        while ((result = scanner.next()) != null) {
            byte[] bytes = result.getValue(familyBytes, qualifierBytes);
            jsons.add(new String(bytes));
        }
        return jsons;
    }

    public void delete(String table, String rowKey, String family, String qualifier) throws IOException {
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        hTable.delete(delete);
    }

    public void delete(String table, String rowKey) throws IOException {
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        hTable.delete(delete);
    }
}
