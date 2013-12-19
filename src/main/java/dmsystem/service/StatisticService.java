package dmsystem.service;

import java.util.List;

import dmsystem.entity.Statistic;

/**
 * 
 * @author bryant zhang
 * 
 */
public interface StatisticService {
	public List<Statistic> retrieveStatistic(String time) throws Exception;

	public List<Statistic> retrieveStatisticForPeriod(String fromDateStr,
			String toDateStr) throws Exception;
}
