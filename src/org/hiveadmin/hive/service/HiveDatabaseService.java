/**  
 * @Project: javaHiveAdimin
 * @Title: HiveDatabaseService.java
 * @Package org.hiveadmin.hive.service.impl
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;

/**
 * HiveDatabaseService is created to describe 
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 */
public interface HiveDatabaseService {

	/** 
	 * setHistoryRecord is the method is to reset the historyRecord property 
	 * @param historyRecord  an object of HistoryRecord
	 */
	public abstract void setHistoryRecord(HistoryRecord historyRecord);

	public abstract void setUserHistoryLog(UserHistoryLog userHistoryLog);

	/**
	 *createDatebase 
	 * @param name
	 * @param comment
	 * @param hdfspath
	 * @param dbproperties
	 * @throws Exception 
	 * @see org.hiveadmin.hive.service.HiveDatabaseService#createDatebase(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */

	public abstract void createDatebase(String name, String comment,
			String hdfspath, Map<String, String> dbproperties) throws Exception;

	/**
	 *deleteDatebase 
	 * @param name
	 * @param type
	 * @throws Exception 
	 * @see org.hiveadmin.hive.service.HiveDatabaseService#deleteDatebase(java.lang.String, java.lang.String)
	 */

	public abstract void deleteDatebase(String name, String type)
			throws Exception;

	/**
	 *listDatabase 
	 * @return
	 * @throws Exception 
	 * @see org.hiveadmin.hive.service.HiveDatabaseService#listDatabase()
	 */

	public abstract List<String> listDatabase() throws Exception;

}