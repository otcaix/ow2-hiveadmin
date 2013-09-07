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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.springframework.stereotype.Component;

/**
 * HiveConnection
 * <p>a util to connect to the remote hive server by thrift and do some operations<br>  
 * @author wangjie wangjie370124@163.com
 * @date Jul 16, 2013 11:08:09 AM
 */

public class HiveConnectionBean {

	/**
	 * driverName used to connect the hive server
	 */
	private static String driverName;
	/**
	 * url of the hive server
	 */
	private static String url;
	/**
	 * user to connect to the hive server
	 */
	private static String user;
	/**
	 * password to connect to the hive server
	 */
	private static String password;
	// private Logger log = Logger.getLogger(this.getClass());
	private Connection connection = null;
	private static Statement stmt = null;
	private static UserHistoryLog userHistoryLog;
	private static HistoryRecord historyRecord = new HistoryRecord();

	private static Logger log = Logger.getLogger(HiveConnectionBean.class);

	public static UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}

	public static void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		HiveConnectionBean.userHistoryLog = userHistoryLog;
	}

	public static HistoryRecord getHistoryRecord() {
		return historyRecord;
	}

	public static void setHistoryRecord(HistoryRecord historyRecord) {
		HiveConnectionBean.historyRecord = historyRecord;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		HiveConnectionBean.log = log;
	}

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

	public static Statement getStmt() throws Exception {
		Connection conn = getConnection();
		if (conn == null) {
			log.error("Connection is null");
			throw new Exception("Connection is null");
		}
		stmt = conn.createStatement();
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private HiveConnectionBean() {
	}

	/** 
	* getConnection 
	* <p>get default connection<br>
	* 
	*/
	public static Connection getConnection() throws SQLException,
			ClassNotFoundException {

		Class.forName(driverName);
		log.info("url:[" + url + "]user:[" + user + "]pass:[" + password + "]");
		return DriverManager.getConnection(url, user, password);

	}
	/** 
	* getConnection 
	* <p>get connection of a givent database<br>
	*/
	public static Connection getConnection(String database) throws SQLException, ClassNotFoundException{
		
		Class.forName(driverName);
		log .info("url:["+url+"]user:["+user+"]pass:["+password+"]");
		return DriverManager.getConnection(url+"/"+database, user, password);
        
	}
	
	/** 
	* executQuery 
	* <p>to execute a sql cmd<br>
	* @param database the database to execute sql
	* @param sqlcmd  the sql to execute
	* @return the result
	* @throws 
	*/
	public static List executQuery(String database,String sqlcmd) throws Exception{
		if(database==null || database.trim().length()==0){
			log.warn("database is not given. use default database.");
			database="default";
		}
		
		if(sqlcmd==null || sqlcmd.trim().length()<1){
			log.error("template content is null. do nothing");
			throw new Exception("template content is null. do nothing");
		}
		Connection conn = getConnection(database);
		if(conn==null){
			log.error("failed to get connection");
			return null;
		}
		Statement stmt = conn.createStatement();
		log.warn("======jdbc::exe query");
		log.warn("sql:"+sqlcmd);
		ResultSet res = stmt.executeQuery(sqlcmd);
		if(res !=null){
			ResultSetMetaData resmd = res.getMetaData();
			ArrayList rows = new ArrayList();
			int colnum = res.getMetaData().getColumnCount();
			log.debug("====colnum:"+colnum);
			int i = 0;
			while(res.next()){
				log.warn("=====add item:");
				HashMap hashMap = new HashMap<>(); 
				for(i=0;i<colnum;i++){
					hashMap.put(resmd.getColumnName(i+1),res.getObject(i+1));
					log.warn("put:colum="+resmd.getColumnName(i+1)+";value="+res.getObject(i+1)+"\t");
				}
				rows.add(hashMap);
				log.warn("--------end add");
			}
			conn.close();
			log.warn("======jdbc::exe query end");
			return rows;
		}
		return null;
		
		
	}


}