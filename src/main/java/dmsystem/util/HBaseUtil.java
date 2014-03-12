package dmsystem.util;

import com.google.gson.Gson;
import dmsystem.entity.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by justinyang on 14-3-10.
 */
public class HBaseUtil {

    HbaseTemplate hbaseTemplate;

    Configuration config = null;

    public void setHbaseTemplate(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    public HBaseUtil() {
        Configuration config = HBaseConfiguration.create();
        this.config = config;

        try {
            this._importData();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _importData() throws NoSuchAlgorithmException, IOException {
        Gson gson = new Gson();
        Configuration config = HBaseConfiguration.create();

        // Create table
        HBaseAdmin admin = new HBaseAdmin(config);

        HTableDescriptor [] hTableDescriptors = admin.listTables();
        for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
            if (Bytes.equals(Bytes.toBytes("Users"), hTableDescriptor.getName())
                    || Bytes.equals(Bytes.toBytes("Docs"), hTableDescriptor.getName())
                    || Bytes.equals(Bytes.toBytes("Configs"), hTableDescriptor.getName())) {
                admin.disableTable(hTableDescriptor.getName());
                admin.deleteTable(hTableDescriptor.getName());
            }
        }

        HTableDescriptor ut = new HTableDescriptor("Users");
        HColumnDescriptor ui = new HColumnDescriptor("Info");
        HColumnDescriptor uo = new HColumnDescriptor("Ops");
        ut.addFamily(ui);
        ut.addFamily(uo);
        admin.createTable(ut);

        HTableDescriptor dt = new HTableDescriptor("Docs");
        HColumnDescriptor di = new HColumnDescriptor("Info");
        HColumnDescriptor de = new HColumnDescriptor("Evas");
        dt.addFamily(di);
        dt.addFamily(de);
        admin.createTable(dt);

        HTableDescriptor ct = new HTableDescriptor("Configs");
        HColumnDescriptor ci = new HColumnDescriptor("Info");
        ct.addFamily(ci);
        admin.createTable(ct);

        DocumentType documentType = new DocumentType();
        documentType.setName("Essay");
        documentType.setId(StringUtil.md5(documentType.getName()));

        DocumentExtraProperty documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("ExtraProperty");
        documentExtraProperty.setId(StringUtil.md5(documentExtraProperty.getPropertyName()));

        documentExtraProperty.setDocumentType(documentType);
        documentType.getExtraProperties().add(documentExtraProperty);

        DocumentWithExtraProperty documentWithExtraProperty = new DocumentWithExtraProperty();
        documentWithExtraProperty.setPropertyValue("propertyValue");
        documentWithExtraProperty.setDocumentExtraProperty(documentExtraProperty);

        Tag tag = new Tag();
        tag.setName("Tag");
        tag.setId(StringUtil.md5(tag.getName()));

        Document document = new Document();
        document.setTitle("Hello world");
        document.setAuthor("Justin");
        document.setUrl("http://justinyangis.me");
        document.setPages(12);
        document.setKeywords("a lot of keywords");
        document.setAbstracts("This is abstract");
        document.setCreateTime(new Date());
        document.setYear("2014");
        document.setPublisher("南京大学");
        document.getTags().add(tag);
        document.setDocumentType(documentType);

        documentWithExtraProperty.setDocument(document);
        document.getExtraProperties().add(documentWithExtraProperty);

        String titleMd5 = StringUtil.md5(document.getTitle());
        long reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        String docid = titleMd5 + reverseTimestamp;
        document.setId(docid);

        User normalUser = new User();
        normalUser.setUsername("user");
        normalUser.setPassword("123");
        normalUser.setAuthority(Constants.kNormalAuthority);
        normalUser.setName("User");
        normalUser.setId(StringUtil.md5(normalUser.getUsername()));

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("123");
        adminUser.setAuthority(Constants.kAdminAuthority);
        adminUser.setName("Admin");
        adminUser.setId(adminUser.getName());

        // Run some operations -- a put, a get, and a scan -- against the table.
        HTable table = new HTable(config, "Configs");
        Put put = new Put(Bytes.toBytes(documentType.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("DocType"), Bytes.toBytes(gson.toJson(documentType)));
        table.put(put);

        put = new Put(Bytes.toBytes(tag.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Tag"), Bytes.toBytes(gson.toJson(tag)));
        table.put(put);

        table = new HTable(config, "Docs");
        put = new Put(Bytes.toBytes(document.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(document)));
        table.put(put);

        table = new HTable(config, "Users");
        put = new Put(Bytes.toBytes(normalUser.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(normalUser)));
        table.put(put);

        put = new Put(Bytes.toBytes(adminUser.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(adminUser)));
        table.put(put);
    }

    public void put(String table, String rowKey, String family, String qualifier, String jsonData) throws IOException, NoSuchAlgorithmException {
        if (table == null || rowKey == null || family == null || qualifier == null || jsonData == null) {
            System.err.println("Null parameters when calling HBaseUtil:put method");
            return;
        }
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(jsonData));
        hTable.put(put);
    }

    public String get(String table, String rowKey, String family, String qualifier) throws IOException {
        if (table == null || rowKey == null || family == null || qualifier == null) {
            System.err.println("Null parameters when calling HBaseUtil:get method");
            return null;
        }
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
        if (table == null || family == null) {
            System.err.println("Null parameters when calling HBaseUtil:find method");
            return null;
        }
        return this.hbaseTemplate.find(table, family, new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }
        });
    }

    public List<String> getAll(String table, String family, String qualifier) throws IOException {
        if (table == null || family == null || qualifier == null) {
            System.err.println("Null parameters when calling HBaseUtil:getall method");
            return null;
        }
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
        if (table == null || rowKey == null || family == null || qualifier == null) {
            System.err.println("Null parameters when calling HBaseUtil:delete method");
            return;
        }
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        hTable.delete(delete);
    }

    public void delete(String table, String rowKey) throws IOException {
        if (table == null || rowKey == null) {
            System.err.println("Null parameters when calling HBaseUtil:delete method");
            return;
        }
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        hTable.delete(delete);
    }
}
