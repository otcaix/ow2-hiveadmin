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
 * @ClassName UserHistoryLog
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 8:38:47 PM
 */
public interface UserHistory {
	public void addHistotyRecord(HistoryRecord record);
	public List<HistoryRecord> getHistoryRecord(String userName,String sort);
}
