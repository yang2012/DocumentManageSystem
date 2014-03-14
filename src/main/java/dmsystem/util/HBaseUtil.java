package dmsystem.util;

import com.google.gson.Gson;
import dmsystem.entity.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
        HColumnDescriptor dd = new HColumnDescriptor("Drafts");
        dt.addFamily(di);
        dt.addFamily(de);
        dt.addFamily(dd);
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
        document.setId(titleMd5 + reverseTimestamp);

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

        Evaluation evaluation = new Evaluation();
        evaluation.setDocId(document.getId());
        evaluation.setContent("Very Good");
        evaluation.setUserName(normalUser.getName());
        evaluation.setUserId(normalUser.getId());
        evaluation.setCreateTime(new Date());
        evaluation.setType(Constants.kSimpleEvaluation);
        evaluation.setPoint(4);
        evaluation.setPublished(true);
        String contentMd5 = StringUtil.md5(evaluation.getContent());
        reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        evaluation.setId(contentMd5 + reverseTimestamp);

        Evaluation evaluation2 = new Evaluation();
        evaluation2.setDocId(document.getId());
        evaluation2.setContent("So Good");
        evaluation2.setUserId(normalUser.getId());
        evaluation2.setUserName(normalUser.getName());
        evaluation2.setCreateTime(new Date());
        evaluation2.setType(Constants.kSimpleEvaluation);
        evaluation2.setPoint(3);
        evaluation2.setPublished(true);
        contentMd5 = StringUtil.md5(evaluation2.getContent());
        reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        evaluation2.setId(contentMd5 + reverseTimestamp);

        Evaluation draft = new Evaluation();
        draft.setDocId(document.getId());
        draft.setContent("Draft");
        draft.setUserId(normalUser.getId());
        draft.setCreateTime(new Date());
        draft.setType(Constants.kSimpleEvaluation);
        draft.setPoint(5);
        draft.setPublished(true);
        draft.setId(normalUser.getId());

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
        put.add(Bytes.toBytes("Evas"), Bytes.toBytes(evaluation.getId()), Bytes.toBytes(gson.toJson(evaluation)));
        put.add(Bytes.toBytes("Evas"), Bytes.toBytes(evaluation2.getId()), Bytes.toBytes(gson.toJson(evaluation2)));
        put.add(Bytes.toBytes("Drafts"), Bytes.toBytes(normalUser.getId()), Bytes.toBytes(gson.toJson(draft)));
        table.put(put);

        table = new HTable(config, "Users");
        put = new Put(Bytes.toBytes(normalUser.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(normalUser)));
        table.put(put);

        put = new Put(Bytes.toBytes(adminUser.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(adminUser)));
        table.put(put);
    }

    public void put(String table, String rowKey, String family, String qualifier, String jsonData) throws IOException {
        if (table == null || rowKey == null || family == null || qualifier == null || jsonData == null) {
            System.err.println("Null parameters when calling HBaseUtil:put method");
            return;
        }
        HTable hTable = new HTable(this.config, Bytes.toBytes(table));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(jsonData));
        hTable.put(put);
    }

    public String get(String table, String rowKey, final String family, final String qualifier) throws IOException {
        if (table == null || rowKey == null || family == null || qualifier == null) {
            System.err.println("Null parameters when calling HBaseUtil:get method");
            return null;
        }

        return this.hbaseTemplate.get(table, rowKey, family, qualifier, new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int rowNum) throws Exception {
                byte[] bytes = result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
                if (bytes != null) {
                    return new String(bytes);
                } else {
                    return null;
                }
            }
        });
    }

    public List<String> get(String table, final String rowKey, final String family) {
        return this.hbaseTemplate.find(table, family, new ResultsExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultScanner results) throws Exception {
                List<String> jsons = new ArrayList<String>();
                for (Result result : results) {
                    if (Bytes.equals(result.getRow(), Bytes.toBytes(rowKey))) {
                        for (KeyValue keyValue : result.list()) {
                            String value = Bytes.toString(keyValue.getValue());
                            jsons.add(value);
                        }
                    }
                }
                return jsons;
            }
        });
    }

    public List<String> find(String table, final String family, final String qualifier) {
        if (table == null || family == null) {
            System.err.println("Null parameters when calling HBaseUtil:find method");
            return null;
        }
        return this.hbaseTemplate.find(table, family, qualifier, new ResultsExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultScanner results) throws Exception {
                List<String> strings = new ArrayList<String>();
                Iterator<Result> it = results.iterator();
                while (it.hasNext()) {
                    Result result = it.next();
                    byte[] bytes = result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
                    strings.add(new String(bytes));
                }
                return strings;
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

        ResultScanner scanner = null;
        try {
            scanner = hTable.getScanner(scan);
            for (Result result : scanner) {
                byte[] bytes = result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
                jsons.add(new String(bytes));
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
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
        delete.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
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
