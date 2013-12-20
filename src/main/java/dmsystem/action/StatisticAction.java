package dmsystem.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dmsystem.entity.DocumentType;
import dmsystem.entity.Statistic;
import dmsystem.entity.User;
import dmsystem.service.DocumentTypeService;
import dmsystem.service.StatisticService;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StatisticAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private String time;
	private String fromDate;
	private String toDate;

	private DocumentTypeService documentTypeService;
	private StatisticService statisticService;

	private List<DocumentType> documentTypes;

	private List<Statistic> statisticListWeek;
	private List<Statistic> statisticListMonth;
	private List<Statistic> statisticListYear;
	private List<Statistic> statisticList;

	public String retrievestatistic() throws Exception {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		documentTypes = this.documentTypeService.getAll();

		if (user == null) {
			return LOGIN;
		}

		this.statisticListWeek = this.statisticService
				.retrieveStatistic("week");
		this.statisticListMonth = this.statisticService
				.retrieveStatistic("month");
		this.statisticListYear = this.statisticService
				.retrieveStatistic("year");

		this.statisticList = this.statisticListWeek;

		return SUCCESS;
	}

	public String generatestatistic() throws Exception {
		user = (User) ActionContext.getContext().getSession()
				.get(User.SESSION_KEY);
		documentTypes = this.documentTypeService.getAll();

		if (user == null) {
			return LOGIN;
		}

		this.statisticListWeek = this.statisticService
				.retrieveStatistic("week");
		this.statisticListMonth = this.statisticService
				.retrieveStatistic("month");
		this.statisticListYear = this.statisticService
				.retrieveStatistic("year");

		this.statisticList = this.statisticService.retrieveStatisticForPeriod(
				fromDate, toDate);

		return SUCCESS;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public StatisticService getStatisticService() {
		return statisticService;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public List<Statistic> getStatisticListWeek() {
		return statisticListWeek;
	}

	public void setStatisticListWeek(List<Statistic> statisticListWeek) {
		this.statisticListWeek = statisticListWeek;
	}

	public List<Statistic> getStatisticListMonth() {
		return statisticListMonth;
	}

	public void setStatisticListMonth(List<Statistic> statisticListMonth) {
		this.statisticListMonth = statisticListMonth;
	}

	public List<Statistic> getStatisticListYear() {
		return statisticListYear;
	}

	public void setStatisticListYear(List<Statistic> statisticListYear) {
		this.statisticListYear = statisticListYear;
	}

	public List<Statistic> getStatisticList() {
		return statisticList;
	}

	public void setStatisticList(List<Statistic> statisticList) {
		this.statisticList = statisticList;
	}

}
