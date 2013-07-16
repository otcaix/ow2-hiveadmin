package org.hiveadmin.hive.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.hadoop.hive.ql.parse.HiveParser.restrictOrCascade_return;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.booleanValue_return;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.hiveutils.HiveConnection;
import org.hiveadmin.hive.service.HiveDatabaseService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName HiveDatabaseServiceImpl
 * @Description hive数据库操作实现
 */
@Component(value="hiveDatabaseServiceImpl")
public class HiveDatabaseServiceImpl implements HiveDatabaseService {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * @param name: 要创建的数据库名称
	 * @param comment: 创建数据库的comment字段
	 * @param hdfspath:创建数据库的location字段,指在hdfs上的位置
	 * @param dbproperties:数据库的properties字段,为一个map
	 * @throws Exception 
	 * @throw RuntimeException:创建失败
	 * (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveDatabaseService#createDatebase(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void createDatebase(String name, String comment, String hdfspath,
			Map<String, String> dbproperties) throws Exception {
		if (name==null || name.trim().length()<1){
			log.error("create database error. database name param is null");
			throw new RuntimeException("create database error. database name param is null");
		}
		String sql = "CREATE DATABASE IF NOT EXISTS "+name;
		if (comment !=null && comment.trim().length()>0){
			sql+=" COMMENT '"+comment+" ' ";
		}
		if (hdfspath !=null && hdfspath.trim().length()>0){
			sql+=" LOCATION '"+hdfspath+" ' ";
		}
		if (dbproperties!=null && dbproperties.size()>0){
			log.info("properties is not null. size:"+dbproperties.size());
			Iterator<String> it = dbproperties.keySet().iterator();
			sql+=" WITH DBPROPERTIES ( ";
			String inner="";
			while(it.hasNext()){
				String key = it.next();
				String value = dbproperties.get(key);
				inner+="'"+key+"'='"+value+"'";
				if(it.hasNext())
					inner+=",";
			}
			sql+=inner+")";
		}
		Connection conn=null;
		try{
			 conn= HiveConnection.getConnection();
			if (conn==null){
				log.error("get hive connection is null. can't do create database.");
				throw new Exception("get hive connection is null. can't do create database.");
			}
			Statement stmt = conn.createStatement();
			boolean res = stmt.execute(sql);
			if(res != true){
				log.error("create database res:false");
				throw new Exception("failed to create db. sql:"+sql);
			}
			log.info("success to create database. sql cmd:"+sql);
			conn.close();
		}catch(Exception e){
			log.error("failed to create database. sql cmd:"+sql);
			throw new Exception("failed to create database. [sql cmd:"+sql);
		}
	}
	/**
	 * @param name:要删除的数据库名称
	 * @param type:删除的类型,类型为[RESTRICT|CASCADE]
	 * @throws Exception 
	 * 
	 */
	@Override
	public void deleteDatebase(String name, String type) throws Exception {
		if(name==null || name.trim().length()<1){
			log.error("delete database error. database param is null");
			throw new Exception("delete database error. database param is null");
		}
		String sql ="DROP DATABASE IF EXISTS  "+name+" ";
		if(type !=null && type.trim().length()>0 ){
			sql+= type;
		}
		try{
			Connection conn =null;
			conn = HiveConnection.getConnection();
			Statement stmt = conn.createStatement();
			boolean res = stmt.execute(sql);
			if(res !=true){
				log.info("delete database res:false");
				throw new Exception();
			}
			else{
				log.info("success to delete database. sql:"+sql);
			}
		}catch(Exception e){
			
			log.info("failed to delete database. [sql:"+sql);
			throw e;
		}
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveDatabaseService#listDatabase()
	 */
	@Override
	public ResultSet listDatabase() throws Exception {
		String sql = "show databases";
		Connection conn = null;
		conn= HiveConnection.getConnection();
		if(conn==null){
			log.error("get hive connection error!");
			return null;
		}
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		return res;
	}

}
