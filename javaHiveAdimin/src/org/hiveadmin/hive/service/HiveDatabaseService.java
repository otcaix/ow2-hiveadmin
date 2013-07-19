package org.hiveadmin.hive.service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;

/**
 * @ClassName HiveDatabaseService
 * @Description HiveDatabase操作类
 */
public interface HiveDatabaseService {
	public String getLog();
	public void setHistoryRecord(HistoryRecord historyRecord);
	public List<String> listDatabase() throws Exception;
	public void createDatebase(String name,String comment,String hdfspath,Map<String, String> dbproperties) throws Exception;
	public void deleteDatebase(String name,String type) throws Exception;
	/** 
	* @Title: testxx 
	* @Description: TODO
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/

}
