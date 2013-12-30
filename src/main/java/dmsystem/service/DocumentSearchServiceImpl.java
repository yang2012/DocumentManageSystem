package dmsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dmsystem.dao.DocumentSearchDao;
import dmsystem.entity.Document;
import dmsystem.util.StringUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class DocumentSearchServiceImpl implements DocumentSearchService {
	private DocumentSearchDao documentSearchDao;

	public DocumentSearchDao getDocumentSearchDao() {
		return documentSearchDao;
	}

	public void setDocumentSearchDao(DocumentSearchDao documentSearchDao) {
		this.documentSearchDao = documentSearchDao;
	}

	public List<Document> getDocList(String keywords) {
		List<Document> documents = null;
		try {
			documents = this.documentSearchDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Document> resultList = new ArrayList<Document>();
		for (int i = 0; i < documents.size(); i++) {
			if (matchDocForSimpleSearch(documents.get(i), keywords)) {
				resultList.add(documents.get(i));
			}
		}
		return resultList;
	}

	public List<Document> getDocList(String docType, String[] params) {
		List<Document> documents = null;
		try {
			if (StringUtil.equals(docType, "0"))
				documents = this.documentSearchDao.getAll();
			else
				documents = this.documentSearchDao.getDocsByType(docType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> paramsMap = this.filterParams(params);

		List<Document> resultList = new ArrayList<Document>();
		for (int i = 0; i < documents.size(); i++) {
			if (matchDocForAdvancedSearch(documents.get(i), paramsMap)) {
				resultList.add(documents.get(i));
			}
		}

		return resultList;
	}

	private boolean matchDocForSimpleSearch(Document doc, String keywords) {
		if (matchWords(doc.getTitle(), keywords)) {
			return true;
		}
		if (matchWords(doc.getAuthor(), keywords)) {
			return true;
		}
		if (matchWords(doc.getKeywords(), keywords)) {
			return true;
		}
		return false;
	}

	private boolean matchDocForAdvancedSearch(Document doc,
			Map<String, String> paramsMap) {
		if (!matchWords(doc.getTitle(), paramsMap.get("title"))) {
			return false;
		}
		if (!matchWords(doc.getAuthor(), paramsMap.get("author"))) {
			return false;
		}
		if (!matchWords(doc.getTags().toArray().toString(),
				paramsMap.get("tag"))) {
			return false;
		}
		if (!matchWords(doc.getKeywords(), paramsMap.get("keywords"))) {
			return false;
		}
		if (!matchWords(doc.getPublisher(), paramsMap.get("publisher"))) {
			return false;
		}
		if (!matchWords(doc.getYear(), paramsMap.get("publishYear"))) {
			return false;
		}
		return true;
	}

	private Map<String, String> filterParams(String[] params) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("title", params[0]);
		paramMap.put("author", params[1]);
		paramMap.put("tag", params[2]);
		paramMap.put("keywords", params[3]);
		paramMap.put("publisher", params[4]);
		paramMap.put("publishYear", params[5]);

		Map<String, String> resultMap = new HashMap<String, String>();
		Iterator<Entry<String, String>> iter = paramMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			if (!StringUtil.equals(entry.getValue(), "")) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}

		return resultMap;
	}

//	private List<Document> filterDocsByType(List<Document> list, String docType) {
//		List<Document> resultList = new ArrayList<Document>();
//		for (int i = 0; i < list.size(); i++) {
//			if (StringUtil.equals(docType, list.get(i).getDocumentType()
//					.getName())) {
//				resultList.add(list.get(i));
//			}
//		}
//		return resultList;
//	}

	private boolean matchWords(String str1, String str2) {
		if (StringUtil.isNullOrEmpty(str1) || StringUtil.isNullOrEmpty(str2)) {
			return true;
		} else {
			String[] strArr1 = str1.split(" ");
			String[] strArr2 = str2.split(" ");
			int length1 = strArr1.length;
			int length2 = strArr2.length;
			for (int i = 0; i < length1; i++) {
				for (int j = 0; j < length2; j++) {
					if (StringUtil.equalsIgnoreCase(strArr1[i], strArr2[j])) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static void main(String args[]) {

		DocumentSearchServiceImpl obj = new DocumentSearchServiceImpl();
		String str1 = "role bases access control";
		String str2 = "network access control strategy";
		Set<String> tags = new HashSet<String>();
		tags.add(str1);
		tags.add(str2);
		System.out.println(tags.toArray());
		System.out.println(obj.matchWords(str1, str2));
		str1 = "role bases access control";
		str2 = "Network Access strategy";
		System.out.println(obj.matchWords(str1, str2));
		str1 = "role control";
		str2 = "Network Access strategy";
		System.out.println(obj.matchWords(str1, str2));

	}
}
