package org.hiveadmin.hive.hiveutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class HiveTest {

	private static String driverName="org.apache.hadoop.hive.jdbc.HiveDriver";
	private static String url="jdbc:hive://192.168.22.11:10000/";
	private static String user="";
	private static String password="";
	private static String sql="";
	private static ResultSet res;
	//private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception{
		    System.out.println();
	        Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.execute("create database wangjie6");
            System.out.println("=========show database:");
            
            res = stmt.executeQuery("show databases");
            while(res.next()){
            	System.out.println("===begin while");
            	res.getString("database_name");
            	System.out.println("===end while");
            }
            System.out.println("++++++++++++");
            System.out.println("=====删除表");
            // 创建的表名
            String tableName = "testHiveDriverTable";
            /** 第一步:存在就先删除 **/
            sql = "drop table " + tableName;
            stmt.executeQuery(sql);
            System.out.println("====创建表:");
            /** 第二步:不存在就创建 **/
            sql = "create table " + tableName + " (key int, value string)  row format delimited fields terminated by '\t'";
            stmt.executeQuery(sql);
            System.out.println("=====show tables");
            // 执行“show tables”操作
            sql = "show tables '" + tableName + "'";
            System.out.println("Running:" + sql);
            res = stmt.executeQuery(sql);
            System.out.println("执行“show tables”运行结果:");
            if (res.next()) {
                    System.out.println(res.getString(1));
            }
            System.out.println("=====desc tables");
            // 执行“describe table”操作
            sql = "describe " + tableName;
            System.out.println("Running:" + sql);
            res = stmt.executeQuery(sql);
            System.out.println("执行“describe table”运行结果:");
            while (res.next()) {  
                    System.out.println(res.getString(1) + "\t" + res.getString(2));
            }
            System.out.println("==========load data into table");
            // 执行“load data into table”操作
            String filepath = "/home/work/userinfo.txt";
            sql = "load data local inpath '" + filepath + "' into table " + tableName;
            System.out.println("Running:" + sql);
            res = stmt.executeQuery(sql);
            System.out.println("====select");
            // 执行“select * query”操作
            sql = "select * from " + tableName;
            System.out.println("Running:" + sql);
            res = stmt.executeQuery(sql);
            System.out.println("执行“select * query”运行结果:");
            while (res.next()) {
                    System.out.println(res.getInt(1) + "\t" + res.getString(2));
            }
            System.out.println("====select count");
            // 执行“regular hive query”操作
            sql = "select count(1) from " + tableName;
            System.out.println("Running:" + sql);
            res = stmt.executeQuery(sql);
            System.out.println("执行“regular hive query”运行结果:");
            while (res.next()) {
                    System.out.println(res.getString(1));

            }

            conn.close();
            conn = null;
	 
		 
	}
}