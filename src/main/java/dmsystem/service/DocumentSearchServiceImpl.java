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
import dmsystem.entity.Tag;
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

	public List<Document> simpleSearch(String keywords) throws Exception {
		return this.documentSearchDao.simpleSearch(keywords);
	}

	public List<Document> advancedSearch(String docType, String[] params)
			throws Exception {
		Map<String, String> paramsMap = this.filterParams(docType, params);
		List<Document> documents = this.documentSearchDao
				.advancedSearch(paramsMap);
		if (StringUtil.isNullOrEmpty(paramsMap.get("tag"))
				|| StringUtil.equals(paramsMap.get("tag"), "")) {
			return documents;
		} else {
			List<Document> resultList = new ArrayList<Document>();
			for (Document each : documents) {
				if (this.matchTags(this.processTags(each.getTags()),
						paramsMap.get("tag"))) {
					resultList.add(each);
				}
			}

			return resultList;
		}
	}

	private Map<String, String> filterParams(String docType, String[] params) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("documentType", docType);
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

	private boolean matchTags(String str1, String str2) {
		if (StringUtil.isNullOrEmpty(str1)) {
			return false;
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

	private String processTags(Set<Tag> tags) {
		ArrayList<String> strList = new ArrayList<String>();
		Iterator<Tag> iter = tags.iterator();
		while (iter.hasNext()) {
			strList.add(iter.next().getName());
		}

		return strList.toString().substring(1, strList.toString().length() - 1)
				.replace(",", "");
	}

	public static void main(String args[]) {

		DocumentSearchServiceImpl obj = new DocumentSearchServiceImpl();
		String str1 = "role bases access control";
		String str2 = "network access control strategy";
		Set<String> tags = new HashSet<String>();
		tags.add(str1);
		tags.add(str2);
		System.out.println(tags.toArray());
		Tag tag1 = new Tag();
		tag1.setName("hello");
		Tag tag2 = new Tag();
		tag2.setName("world");
		Tag tag3 = new Tag();
		tag3.setName("kobe bryant");
		Set<Tag> set = new HashSet<Tag>();
		set.add(tag1);
		set.add(tag2);
		set.add(tag3);
		System.out.print(obj.processTags(set));
		Map<String, String> map = new HashMap<String, String>();
		map.put("zhaqi", "zhaqi");
		System.out.println(map.get("a"));
	}
}
