/**  
* @Project: javaHiveAdimin
* @Title: HiveCliResPathBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 12, 2013 1:24:58 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.io.File;

/**
 * @ClassName HiveCliResPathBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 12, 2013 1:24:58 PM
 */
public class HiveCliResPathBean {
	public String resultPath="";
	public String statusPath="";
	
	public String getResultPath() {
		return resultPath;
	}
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	public String getStatusPath() {
		return statusPath;
	}
	public void setStatusPath(String statusPath) {
		this.statusPath = statusPath;
	}
}
