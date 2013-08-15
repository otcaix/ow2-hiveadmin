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

/**
 * @ClassName HiveCliService
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 12, 2013 9:49:22 AM
 */
public interface HiveCliService {

	public void cliQuery(String database,String sql, String fingerPrint) throws IOException;
	public String getQueryStatus(String fingerPrint, long lastReadSize, int maxline);
	public String getResult(String fingerPrint) throws Exception;
	public void setContextPath(String contextPath);
	
	
}
