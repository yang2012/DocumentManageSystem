package dmsystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmsystem.dao.OperationDao;
import dmsystem.dao.StatisticDao;
import dmsystem.dao.UserDao;
import dmsystem.entity.Operation;
import dmsystem.entity.Statistic;
import dmsystem.util.DateUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StatisticServiceImpl implements StatisticService {

	private StatisticDao statisticDao;
	private UserDao userDao;
	private OperationDao operationDao;

	public List<Statistic> retrieveStatistic(String time) throws Exception {
		List<Integer> userIdList = this.operationDao.retrieveUserIds(time);
		List<Operation> operationList = this.statisticDao
				.getOperationsByDate(time);
		return this.generateStatistic(userIdList, operationList);
	}

	public List<Statistic> retrieveStatisticForPeriod(String fromDateStr,
			String toDateStr) throws Exception {
		Date fromDate = DateUtil.getDateFromString(fromDateStr);
		Date toDate = DateUtil.getDateFromString(toDateStr);
		List<Integer> userIdList = this.operationDao.retrieveUserIds(fromDate,
				toDate);
		List<Operation> operationList = this.statisticDao.getOperationsByDate(
				fromDate, toDate);
		return this.generateStatistic(userIdList, operationList);
	}

	public List<Statistic> generateStatistic(List<Integer> userIdList,
			List<Operation> operationList) throws Exception {
		List<Statistic> statList = new ArrayList<Statistic>();

		for (int i = 0; i < userIdList.size(); i++) {
			Statistic stat = new Statistic();
			stat.setUserId(userIdList.get(i));
			stat.setUsername(this.userDao.findById(userIdList.get(i)).getName());
			statList.add(stat);
		}

		for (int i = 0; i < operationList.size(); i++) {
			for (int j = 0; j < statList.size(); j++) {
				if (statList.get(j).getUserId().intValue() == operationList
						.get(i).getUser().getId()) {
					switch (operationList.get(i).getType().intValue()) {
					case 3:
						statList.get(j).setDocimport(
								statList.get(j).getDocimport() + 1);
						break;
					case 2:
						statList.get(j).setUploadattachment(
								(statList.get(j).getUploadattachment() + 1));
						break;
					case 1:
						statList.get(j).setSimplecomment(
								(statList.get(j).getSimplecomment() + 1));
						break;
					case 4:
						statList.get(j).setDetailedcomment(
								(statList.get(j).getDetailedcomment() + 1));
						break;
					}
				}
			}
		}
		return statList;
	}

	public StatisticDao getStatisticDao() {
		return statisticDao;
	}

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationDao getOperationDao() {
		return operationDao;
	}

	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}
}
