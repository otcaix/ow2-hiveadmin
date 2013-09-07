/**  
* @Project: javaHiveAdimin
* @Title: UserHistoryLog.java
* @Package org.hiveadmin.hive.dao
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 8:38:47 PM
* @version V1.0  
*/
package org.hiveadmin.hive.dao;

import java.util.List;

import org.hiveadmin.hive.beans.HistoryRecord;

/**
 * UserHistoryLog
 * <p>dao to operate the user history records;<br>
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 8:38:47 PM
 */
public interface UserHistory {
	/** 
	* addHistotyRecord 
	* <p>to add a history record to the database<br>
	* @param a bean that descriping a history record
	* 
	*/
	public void addHistotyRecord(HistoryRecord record);
	/** 
	* getHistoryRecord 
	* <p>to get the list of the history records of the current user<br>
	* @param userName name of the operating user
	* @return a list of history record
	*  
	*/
	public List<HistoryRecord> getHistoryRecord(String userName,String sort);
}
