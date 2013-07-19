/**  
* @Project: javaHiveAdimin
* @Title: HiveConnection.java
* @Package org.hiveadmin.hive.hiveutils
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 16, 2013 11:08:09 AM
* @version V1.0  
*/
package org.hiveadmin.hive.hiveutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @ClassName HiveConnection
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 16, 2013 11:08:09 AM
 */

public class HiveConnectionBean {

	private static String driverName;
	private static String url;
	private static String user;
	private static String password;
	//private Logger log = Logger.getLogger(this.getClass());
	private  Connection connection=null;
	private static Logger log = Logger.getLogger(HiveConnectionBean.class);
	
	public static String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		HiveConnectionBean.driverName = driverName;
	}

	public static String getUrl() {
		return url;
	}
	@Resource
	public void setUrl(String url) {
		HiveConnectionBean.url = url;
	}

	public static String getUser() {
		return user;
	}

	public void setUser(String user) {
		HiveConnectionBean.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		HiveConnectionBean.password = password;
	}
	
	private HiveConnectionBean(){}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		
		Class.forName(driverName);
		log .info("url:["+url+"]user:["+user+"]pass:["+password+"]");
		return DriverManager.getConnection(url, user, password);
        
	}

}