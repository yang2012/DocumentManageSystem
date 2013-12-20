package dmsystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmsystem.dao.StatisticDao;
import dmsystem.dao.UserDao;
import dmsystem.entity.Operation;
import dmsystem.entity.Statistic;
import dmsystem.entity.User;
import dmsystem.util.DateUtil;

/**
 * 
 * @author bryant zhang
 * 
 */
public class StatisticServiceImpl implements StatisticService {

	private StatisticDao statisticDao;
	private UserDao userDao;

	public List<Statistic> retrieveStatistic(String time) throws Exception {
		List<User> userList = this.userDao.getAllUser();
		List<Operation> operationList = this.statisticDao
				.getOperationsByDate(time);
		return this.generateStatistic(userList, operationList);
	}

	public List<Statistic> retrieveStatisticForPeriod(String fromDateStr,
			String toDateStr) throws Exception {
		Date fromDate = DateUtil.getDateFromString(fromDateStr);
		Date toDate = DateUtil.getDateFromString(toDateStr);
		List<User> userList = this.userDao.getAllUser();
		List<Operation> operationList = this.statisticDao.getOperationsByDate(
				fromDate, toDate);
		return this.generateStatistic(userList, operationList);
	}

	public List<Statistic> generateStatistic(List<User> userList,
			List<Operation> operationList) throws Exception {
		List<Statistic> statList = new ArrayList<Statistic>();

		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getAuthority().intValue() == 1) {
				Statistic stat = new Statistic();
				stat.setUserId(userList.get(i).getId());
				stat.setUsername(userList.get(i).getName());
				stat.setDocimport(new Integer(0));
				stat.setUploadattachment(new Integer(0));
				stat.setSimplecomment(new Integer(0));
				stat.setDetailedcomment(new Integer(0));
				statList.add(stat);
			}
		}
		for (int i = 0; i < operationList.size(); i++) {
			for (int j = 0; j < statList.size(); j++) {
				if (statList.get(j).getUserId().intValue() == operationList
						.get(i).getUser().getId()) {
					switch (operationList.get(i).getType().intValue()) {
					case 3:
						statList.get(j).setDocimport(
								new Integer(statList.get(j).getDocimport()
										.intValue() + 1));
						break;
					case 2:
						statList.get(j).setUploadattachment(
								new Integer(statList.get(j)
										.getUploadattachment().intValue() + 1));
						break;
					case 1:
						statList.get(j).setSimplecomment(
								new Integer(statList.get(j).getSimplecomment()
										.intValue() + 1));
						break;
					case 4:
						statList.get(j).setDetailedcomment(
								new Integer(statList.get(j)
										.getDetailedcomment().intValue() + 1));
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
}
