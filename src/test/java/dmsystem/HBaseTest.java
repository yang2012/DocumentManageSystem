package dmsystem;

import com.google.gson.Gson;
import dmsystem.entity.*;
import dmsystem.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by justinyang on 14-3-11.
 */
public class HBaseTest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Gson gson = new Gson();
        Configuration config = HBaseConfiguration.create();

        // Create table
        HBaseAdmin admin = new HBaseAdmin(config);
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
        dt.addFamily(ci);
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

        // Run some operations -- a put, a get, and a scan -- against the table.
        HTable table = new HTable(config, "Configs");
        Put put = new Put(Bytes.toBytes(documentType.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("DocType"), Bytes.toBytes(gson.toJson(documentType)));
        table.put(put);

        put = new Put(Bytes.toBytes(tag.getId()));
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Tag"), Bytes.toBytes(gson.toJson(tag)));
        table.put(put);

        table = new HTable(config, "Docs");
        put.add(Bytes.toBytes("Info"), Bytes.toBytes("Basis"), Bytes.toBytes(gson.toJson(document)));
        table.put(put);
    }
}
