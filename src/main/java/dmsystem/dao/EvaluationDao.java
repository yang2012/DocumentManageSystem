package dmsystem.dao;

// Generated Dec 16, 2013 7:25:34 PM by Hibernate Tools 4.0.0

import com.google.gson.Gson;
import dmsystem.entity.Document;
import dmsystem.entity.Evaluation;
import dmsystem.entity.User;
import dmsystem.util.HBaseUtil;
import dmsystem.util.HibernateUtil;
import dmsystem.util.StringUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Utility object for domain model class Evaluation.
 * @see dmsystem.entity.Evaluation
 * @author Justin Yang
 */
public class EvaluationDao {

    private String table = "Docs";
    private String evaluationFamily = "Evas";
    private String draftFamily = "Drafts";

    private HBaseUtil hBaseUtil;
    private Gson gson = new Gson();

    public void sethBaseUtil(HBaseUtil hBaseUtil) {
        this.hBaseUtil = hBaseUtil;
    }

	public void addEvaluation(Evaluation transientInstance) throws Exception {
        String key = this._generateRowKey(transientInstance);
        transientInstance.setId(key);
        this.updateEvaluation(transientInstance);
	}

    public void addDraft(Evaluation transientDraft) throws IOException {
        this.updateDraft(transientDraft);
    }

	public void removeEvaluation(Evaluation persistentInstance) throws Exception {
        this.hBaseUtil.delete(this.table, persistentInstance.getDocId(), this.evaluationFamily, persistentInstance.getId());
	}

    public void removeDraft(Evaluation persistentDraft) throws Exception {
        this.hBaseUtil.delete(this.table, persistentDraft.getDocId(), this.draftFamily, persistentDraft.getUserId());
    }

	public void updateEvaluation(Evaluation detachedInstance) throws Exception {
		if (detachedInstance != null) {
            String json = gson.toJson(detachedInstance);
            this.hBaseUtil.put(this.table, detachedInstance.getDocId(), this.evaluationFamily, detachedInstance.getId(), json);
		}
	}

    public void updateDraft(Evaluation detachedDraft) throws IOException {
        if (detachedDraft != null) {
            String json = this.gson.toJson(detachedDraft);
            this.hBaseUtil.put(this.table, detachedDraft.getDocId(), this.draftFamily, detachedDraft.getUserId(), json);
        }
    }

	public Evaluation getEvaluation(String id) throws Exception {
        Evaluation evaluation = null;
        List<String> jsons = this.hBaseUtil.find(this.table, this.evaluationFamily, id);
        if (jsons.size() == 1) {
            evaluation = this.gson.fromJson(jsons.get(0), Evaluation.class);
        }
		return evaluation;
	}

    public Set<Evaluation> getEvaluations(Document document) throws IOException {
        Set<Evaluation> evaluations = new HashSet<Evaluation>();
        List<String> jsons = this.hBaseUtil.get(this.table, document.getId(), this.evaluationFamily);
        if (jsons != null) {
            for (String json : jsons) {
                Evaluation evaluation = this.gson.fromJson(json, Evaluation.class);
                evaluations.add(evaluation);
            }
        }
        return evaluations;
    }

    public Evaluation getDraft(String userId, String docId) {
        Evaluation draft = null;
        try {
            String json = this.hBaseUtil.get(this.table, docId, this.draftFamily, userId);
            if (json != null) {
                draft = this.gson.fromJson(json, Evaluation.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return draft;
    }

    private String _generateRowKey(Evaluation evaluation) {
        String contentMd5 = StringUtil.md5(evaluation.getContent());
        long reverseTimestamp = Long.MAX_VALUE - Calendar.getInstance().getTime().getTime();
        return contentMd5 + reverseTimestamp;
    }
}
