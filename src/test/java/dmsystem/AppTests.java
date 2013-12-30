package dmsystem;

import dmsystem.entity.*;
import dmsystem.util.Constants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class AppTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void testDatabase() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);

        session.beginTransaction();

        User user = new User();
        user.setName("justin");
        user.setPassword("user");
        user.setUsername("user");
        user.setAuthority(Constants.Authority.Normal.ordinal());
        session.save(user);

        User admin = new User();
        admin.setName("Admin");
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setAuthority(Constants.Authority.Admin.ordinal());
        session.save(admin);

        DocumentType docType = new DocumentType();
        docType.setName("issue");
        session.save(docType);

        DocumentType dTypeBook = new DocumentType();
        dTypeBook.setName("图书");
        session.save(dTypeBook);

        DocumentType dTypeSection = new DocumentType();
        dTypeSection.setName("图书章节");
        session.save(dTypeSection);

        DocumentType dTypeJournal = new DocumentType();
        dTypeJournal.setName("期刊");
        session.save(dTypeJournal);

        DocumentType dTypeConference = new DocumentType();
        dTypeConference.setName("会议");
        session.save(dTypeConference);

        DocumentType dTypeThesis = new DocumentType();
        dTypeThesis.setName("学位论文");
        session.save(dTypeThesis);

        DocumentType dTypeReport = new DocumentType();
        dTypeReport.setName("技术报告");
        session.save(dTypeReport);

        DocumentType dTypeOnline = new DocumentType();
        dTypeOnline.setName("在线资源");
        session.save(dTypeOnline);

        for (int i = 0; i < 10; i++) {
            Document document = new Document();

            document.setTitle("Alternative to mental hospital treatment: I. conceptual model, treatment program, and clinical evaluation");
            document.setAuthor("LI Stein, MA Test");
            document.setYear("1900");
            document.setAbstracts("A conceptual model for the development of communitybased treatment programs for the chronically disabled psychiatric patient was developed, and the results of a controlled study and follow-up are reported. A community-treatment program that was based on the conceptual model was compared with conventional treatment (ie, progressive short-term hospitalization plus aftercare). The results have shown that use of the community program for 14 months greatly reduced the need to hospitalize patients and enhanced the community tenure and adjustment of the experimental patients. When the special programming was discontinued, many of the gains that were attained deteriorated, and use of the hospital rose sharply. The results suggest that community programming should be comprehensive and ongoing");
            document.setKeywords("This is a keyword");
            document.setPages(12);
            document.setUrl("http://archpsyc.ama-assn.org/cgi/reprint/37/4/392.pdf");
            document.setCreateTime(new Date());
            document.setPublisher("Archives of general psychiatry");

            document.setUser(user);
            user.getDocuments().add(document);

            document.setDocumentType(docType);
            docType.getDocuments().add(document);

            session.save(document);
        }
        Document document = new Document();

        document.setTitle("Hello world");
        document.setAuthor("justin");
        document.setYear("2011");
        document.setAbstracts("This is a abstract");
        document.setKeywords("This is a keyword");
        document.setPages(12);
        document.setUrl("http://www.baidu.com/baike/whatwrong");
        document.setCreateTime(new Date());
        document.setPublisher("Renming Publisher");

        document.setUser(user);
        user.getDocuments().add(document);

        document.setDocumentType(docType);
        docType.getDocuments().add(document);

        session.save(document);

        Attachment attachment = new Attachment();
        attachment.setName("sfdas.pdf");
        attachment.setUrl("http://asdfasdf.com/sadfasdf");

        document.getAttachments().add(attachment);
        attachment.setDocument(document);

        session.save(attachment);

        Evaluation evaluation = new Evaluation();
        evaluation.setContent("helloworld");
        evaluation.setCreateTime(new Date());
        evaluation.setPoint(5);
        evaluation.setPublished(true);
        evaluation.setType(1);

        evaluation.setDocument(document);
        evaluation.setUser(user);

        session.save(evaluation);

        Tag tag = new Tag();
        tag.setName("java");

        tag.setDocument(document);

        session.save(tag);

        Operation operation = new Operation();
        operation.setExpression("Add a new document");
        operation.setTime(new Date());
        operation.setType(3);

        operation.setUser(user);

        session.save(operation);

        Document document2 = new Document();
        document2.setTitle("Hello world 2");
        document2.setAuthor("justin");
        document2.setYear("2010");
        document2.setAbstracts("This is a abstract");
        document2.setKeywords("This is a keyword");
        document2.setPages(12);
        document2.setUrl("http://www.baidu.com/baike/whatwrong");
        document2.setCreateTime(new Date());
        document2.setPublisher("Renming Publisher");

        document2.setUser(user);
        document2.setDocumentType(docType);

        session.save(document2);

        RelationType relationType = new RelationType();
        relationType.setName("Reference");
        session.save(relationType);

        DocumentRelation documentRelation = new DocumentRelation();
        documentRelation.setReferer(document);
        documentRelation.setReferee(document2);
        documentRelation.setRelationType(relationType);

        document.getRefererRelations().add(documentRelation);

        session.save(documentRelation);

        DocumentExtraProperty documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("出处");
        documentExtraProperty.setDocumentType(dTypeJournal);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("出处");
        documentExtraProperty.setDocumentType(dTypeConference);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("城市");
        documentExtraProperty.setDocumentType(dTypeConference);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("卷");
        documentExtraProperty.setDocumentType(dTypeJournal);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("期");
        documentExtraProperty.setDocumentType(dTypeJournal);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("编辑");
        documentExtraProperty.setDocumentType(dTypeBook);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("编辑");
        documentExtraProperty.setDocumentType(dTypeSection);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("图书名");
        documentExtraProperty.setDocumentType(dTypeSection);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("学校");
        documentExtraProperty.setDocumentType(dTypeThesis);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("DOI");
        documentExtraProperty.setDocumentType(dTypeJournal);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("DOI");
        documentExtraProperty.setDocumentType(dTypeConference);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("ISBN");
        documentExtraProperty.setDocumentType(dTypeBook);
        session.save(documentExtraProperty);

        documentExtraProperty = new DocumentExtraProperty();
        documentExtraProperty.setPropertyName("ISBN");
        documentExtraProperty.setDocumentType(dTypeSection);
        session.save(documentExtraProperty);

        DocumentWithExtraProperty documentWithExtraProperty = new DocumentWithExtraProperty();
        documentWithExtraProperty.setDocument(document);
        documentWithExtraProperty
                .setDocumentExtraProperty(documentExtraProperty);
        documentWithExtraProperty.setPropertyValue("Nanjing");

        session.save(documentWithExtraProperty);

        EvaluationExtraProperty evaluationExtraProperty = new EvaluationExtraProperty();
        evaluationExtraProperty.setPropertyName("Feeling");

        session.save(evaluationExtraProperty);

        EvaluationWithExtraProperty evaluationWithExtraProperty = new EvaluationWithExtraProperty();
        evaluationWithExtraProperty.setEvaluation(evaluation);
        evaluationWithExtraProperty
                .setEvaluationExtraProperty(evaluationExtraProperty);
        evaluationWithExtraProperty.setPropertyValue("Oh yeah");

        session.save(evaluationWithExtraProperty);

        session.getTransaction().commit();
    }
}
