/**  
 * @Project: javaHiveAdimin
 * @Title: HiveDatabaseServiceImpl.java
 * @Package org.hiveadmin.hive.service.impl
 * @author Wangjie,wangjie12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.hiveadmin.hive.hiveutils.HiveConnectionBean;
import org.hiveadmin.hive.service.HiveDatabaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * HiveDatabaseServiceImpl is created to describe HiveDatabaseServiceImpl is
 * created to describe the operations on the hive database.
 * HiveDatabaseServiceImpl implements the basic function bout the operation on
 * the database
 * 
 * @author Wangjie,wangjie12@octaix.iscas.ac.cn
 */

@Component(value = "hiveDatabaseServiceImpl")
@Scope("prototype")
public class HiveDatabaseServiceImpl implements HiveDatabaseService {

	/**
	 * hiveConnection:this property is used to connection hive
	 */
	private HiveConnectionBean hiveConnection;

	/**
	 * userHistoryLog:this property is to push the records about user into the
	 * database
	 */
	private UserHistoryLog userHistoryLog;
	/**
	 * historyRecord:this property is to describe the records about operation on
	 * the database
	 */
	private HistoryRecord historyRecord;

	/**
	 * This method can create a new HiveDatabaseServiceImpl object.
	 */
	public HiveDatabaseServiceImpl() {

	}

	/**
	 * getHistoryRecord is the method to return the history record
	 * 
	 * @return HistoryRecord
	 */
	public HistoryRecord getHistoryRecord() {
		return historyRecord;
	}

	/**
	 * setHistoryRecord
	 * 
	 * @param historyRecord
	 * @see org.hiveadmin.hive.service.impl.HiveDatabaseService#setHistoryRecord(org.hiveadmin.hive.beans.HistoryRecord)
	 */
	@Override
	public void setHistoryRecord(HistoryRecord historyRecord) {
		this.historyRecord = historyRecord;
	}

	public UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}

	/**
	 * setUserHistoryLog
	 * 
	 * @param userHistoryLog
	 * @see org.hiveadmin.hive.service.impl.HiveDatabaseService#setUserHistoryLog(org.hiveadmin.hive.dao.impl.UserHistoryLog)
	 */
	@Override
	@Resource
	public void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		this.userHistoryLog = userHistoryLog;
	}

	public HiveConnectionBean getHiveConnection() {
		return hiveConnection;
	}

	@Resource
	public void setHiveConnection(HiveConnectionBean hiveConnection) {
		this.hiveConnection = hiveConnection;
	}

	private Logger log = Logger.getLogger(this.getClass());
	private String curlog;

	public String getLog() {
		return this.curlog;
	}

	/**
	 * createDatebase
	 * 
	 * @param name
	 * @param comment
	 * @param hdfspath
	 * @param dbproperties
	 * @throws Exception
	 * @see org.hiveadmin.hive.service.impl.HiveDatabaseService#createDatebase(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.Map)
	 */

	@Override
	public void createDatebase(String name, String comment, String hdfspath,
			Map<String, String> dbproperties) throws Exception {
		if (name == null || name.trim().length() < 1) {
			log.error("create database error. database name param is null");
			throw new Exception(
					"create database error. database name param is null");
		}
		String sql = "CREATE DATABASE IF NOT EXISTS " + name;
		if (comment != null && comment.trim().length() > 0) {
			sql += " COMMENT '" + comment + " ' ";
		}
		if (hdfspath != null && hdfspath.trim().length() > 0) {
			sql += " LOCATION '" + hdfspath + " ' ";
		}
		if (dbproperties != null && dbproperties.size() > 0) {
			log.info("properties is not null. size:" + dbproperties.size());
			Iterator<String> it = dbproperties.keySet().iterator();
			sql += " WITH DBPROPERTIES ( ";
			String inner = "";
			while (it.hasNext()) {
				String key = it.next();
				String value = dbproperties.get(key);
				inner += "'" + key + "'='" + value + "'";
				if (it.hasNext())
					inner += ",";
			}
			sql += inner + ")";
		}
		historyRecord.setOp_sql(sql);
		Connection conn = null;
		try {
			conn = hiveConnection.getConnection();
			if (conn == null) {
				log.error("get hive connection is null. can't do create database.");
				throw new Exception(
						"get hive connection is null. can't do create database.");
			}
			Statement stmt = conn.createStatement();
			boolean res = stmt.execute(sql);
			if (res != true) {
				log.error("create database res:false");
				throw new Exception("failed to create db. sql:" + sql);
			}
			log.info("success to create database. sql cmd:" + sql);
			historyRecord.setOp_res(true);
			userHistoryLog.addHistotyRecord(historyRecord);
			conn.close();
		} catch (Exception e) {
			log.error("failed to create database. sql cmd:" + sql
					+ "[exception:" + e.getMessage() + "]");
			historyRecord.setOp_res(false);
			throw new Exception("failed to create database. [sql cmd:" + sql);
		}
	}

	/**
	 * deleteDatebase
	 * 
	 * @param name
	 * @param type
	 * @throws Exception
	 * @see org.hiveadmin.hive.service.impl.HiveDatabaseService#deleteDatebase(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public void deleteDatebase(String name, String type) throws Exception {
		if (name == null || name.trim().length() < 1) {
			log.error("delete database error. database param is null");
			throw new Exception("delete database error. database param is null");
		}
		String sql = "DROP DATABASE IF EXISTS  " + name + " ";
		if (type != null && type.trim().length() > 0) {
			sql += type;
		}
		historyRecord.setOp_sql(sql);
		try {
			Connection conn = null;
			conn = HiveConnectionBean.getConnection();
			Statement stmt = conn.createStatement();
			boolean res = stmt.execute(sql);
			if (res != true) {
				log.info("delete database res:false");
				throw new Exception();
			} else {
				log.info("success to delete database. sql:" + sql);
				historyRecord.setOp_res(true);
				userHistoryLog.addHistotyRecord(historyRecord);
				conn.close();
			}
		} catch (Exception e) {

			log.info("failed to delete database. [sql:" + sql);
			historyRecord.setOp_res(false);
			userHistoryLog.addHistotyRecord(historyRecord);
			throw e;
		}
	}

	/**
	 * listDatabase
	 * 
	 * @return
	 * @throws Exception
	 * @see org.hiveadmin.hive.service.impl.HiveDatabaseService#listDatabase()
	 */

	@Override
	public List<String> listDatabase() throws Exception {
		String sql = "show databases";
		Connection conn = null;
		conn = hiveConnection.getConnection();
		if (conn == null) {
			log.error("get hive connection error!");
			return null;
		}
		historyRecord.setOp_sql(sql);
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			List<String> databaseList = new ArrayList<String>();
			while (res.next()) {
				databaseList.add(res.getString(1));
			}
			res.close();
			conn.close();
			historyRecord.setOp_res(true);
			userHistoryLog.addHistotyRecord(historyRecord);
			return databaseList;

		} catch (Exception e) {
			historyRecord.setOp_res(false);
			userHistoryLog.addHistotyRecord(historyRecord);
			throw e;
		}
	}
}
