/**  
* @Project: javaHiveAdimin
* @Title: HiveCliService.java
* @Package org.hiveadmin.hive.service
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 12, 2013 9:49:22 AM
* @version V1.0  
*/
package org.hiveadmin.hive.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.beans.RealTimeReadFileBean;

/**
 * HiveCliService
 * to do hive query by the local hive cli way.
 * @author wangjie wangjie370124@163.com
 * @date Aug 12, 2013 9:49:22 AM
 */
public interface HiveCliService {

	/** 
	* cliQuery 
	* <p>to do cli query<br>
	* @param database the database to execute sql
	* @param sql the sql command
	* @param fingetPrint a String to mark this operation
	* 
	* @throws throw exception if failed 
	*/
	public void cliQuery(String database,String sql, String fingerPrint) throws IOException;
	/** 
	* getQueryStatus 
	* <p>to get query status in real time<br>
	* @param fingetPrint a String to mark this operation
	* @param lastReadSize 
	* @param maxline the max line that can read at one time
	* @return the result
	* @throws 
	*/
	public String getQueryStatus(String fingerPrint, long lastReadSize, int maxline);
	/** 
	* getResult 
	* <p>get the result content<br>
	* @param fingetPrint a String to mark this operation
	* @return the result content
	* @throws throw exception if failed 
	*/
	public String getResult(String fingerPrint) throws Exception;
	/** 
	* setContextPath 
	* <p><br>
	* @param 
	* @return
	* @throws 
	*/
	public void setContextPath(String contextPath);
	
	/** 
	* getQueryStatusBean 
	* <p><br>
	* @param 
	* @return
	* @throws 
	*/
	void getQueryStatusBean(String fingerPrint, RealTimeReadFileBean bean,
			long lastReadSize, int maxline);
	
	/** 
	* setHistoryRecord 
	* <p><br>
	* @param 
	* @return
	* @throws 
	*/
	public void setHistoryRecord(HistoryRecord historyRecord);
	

	/** 
	* getResultFileStream 
	* <p>return the resource of the result file<br>
	* @param 
	* @return
	* @throws 
	*/
	public InputStream getResultFileStream(String fingerPrint) throws Exception;
	
	
}
