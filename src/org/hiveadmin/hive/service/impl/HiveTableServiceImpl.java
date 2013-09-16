/**  
 * @Project: javaHiveAdimin
 * @Title: HiveTableServiceImpl.java
 * @Package org.hiveadmin.hive.service.impl
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.service.impl;

import org.hiveadmin.hive.hiveutils.HiveConnectionBean;
import org.hiveadmin.hive.service.HiveTableService;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.springframework.stereotype.Component;

/**
 * HiveTableServiceImpl is created to describe the operations on the hive table.
 * HiveTableServiceImpl implements the basic function bout the operation on the
 * table
 * <p>
 * 
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 */

@Component
public class HiveTableServiceImpl implements HiveTableService {
	/**
	 * log:this property is to output the records of operation
	 */
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * userHistoryLog:this property is to push the records about user into the
	 * database
	 */
	private UserHistoryLog userHistoryLog = new UserHistoryLog();

	/**
	 * getUserHistoryLog is the method to return the UserHistoryLog object
	 * 
	 * @return UserHistoryLog
	 */

	public UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}

	/**
	 * setUserHistoryLog is the method to reset the UserHistoryLog property
	 * 
	 * @param userHistoryLog
	 */
	@Resource
	public void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		this.userHistoryLog = userHistoryLog;
	}

	 /**
	  *createTables 
	  * @param external
	  * @param if_not_exists
	  * @param database_name
	  * @param table_name
	  * @param columns
	  * @param table_comment
	  * @param P_columns
	  * @param cluser_cols_name
	  * @param clu_sorted_cols
	  * @param num_buckets
	  * @param skewed_col_names
	  * @param skewed_col_values
	  * @param row_format_value
	  * @param file_format
	  * @param row_sorted_by_cols
	  * @param hdfs_path
	  * @param tblproperties_spec
	  * @param select_statement
	  * @param exist_table_or_view_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#createTables(boolean, boolean, java.lang.String, java.lang.String, java.util.List, java.lang.String, java.util.List, java.util.List, java.util.List, java.lang.String, java.util.List, java.util.List, java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.String)
	  */
	@Override
	public void createTables(boolean external, boolean if_not_exists,
			String database_name, String table_name, List<Columns> columns,
			String table_comment, List<Columns> P_columns,
			List<String> cluser_cols_name, List<Columns> clu_sorted_cols,
			String num_buckets, List<String> skewed_col_names,
			List<String> skewed_col_values, String row_format_value,
			String file_format, List<Columns> row_sorted_by_cols,
			String hdfs_path,
		    String select_statement,
			String exist_table_or_view_name) throws Exception {
		String sql = "create ";
		if (external) {
			sql = sql + "external ";
		}
		sql = sql + "table ";
		if (if_not_exists) {
			sql = sql + " if not exists ";
		}
		if (!(database_name == null || database_name.equals(""))) {
			sql = sql + database_name + ".";
		}
		sql = sql + table_name + " ";
		if (!columns.isEmpty()) {
			sql = sql + "(";
			Iterator<Columns> p_columns = columns.iterator();
			while (p_columns.hasNext()) {
				Columns p_column = (Columns) p_columns.next();
				sql = sql + p_column.getCols_name() + " "
						+ p_column.getCols_type() + " comment '"
						+ p_column.getCols_comment() + "'";
				if (p_columns.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + " ) ";
		}

		if (!(table_comment == null || table_comment.equals(""))) {
			sql = sql + "comment '" + table_comment + "' ";
		}
		if (!P_columns.isEmpty()) {
			sql = sql + " partitioned by (";
			Iterator<Columns> p_columns = P_columns.iterator();
			while (p_columns.hasNext()) {
				Columns p_column = (Columns) p_columns.next();
				sql = sql + p_column.getCols_name() + " "
						+ p_column.getCols_type() + " comment '"
						+ p_column.getCols_comment() + "' ";
				if (p_columns.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + " ) ";

		}
		if (!cluser_cols_name.isEmpty()) {
			sql = sql + "clustered by (";
			List<String> clustered_by_name = cluser_cols_name;
			Iterator<String> p_entries = clustered_by_name.iterator();
			while (p_entries.hasNext()) {
				String pc_entry = p_entries.next();
				sql = sql + pc_entry;
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + ") ";
			if (!clu_sorted_cols.isEmpty()) {
				sql = sql + " sorted by (";
				Iterator<Columns> p_clu_entries = clu_sorted_cols.iterator();
				while (p_clu_entries.hasNext()) {
					Columns p_clu_entry = p_clu_entries.next();
					sql = sql + p_clu_entry.getCols_name() + " "
							+ p_clu_entry.getCols_type();
					if (p_clu_entries.hasNext()) {
						sql = sql + ", ";
					}

				}
				sql = sql + ")";

			}
			sql = sql + " into " + num_buckets + " buckets ";

		}
		System.out.println("database name:" + database_name);
		
		if (!skewed_col_names.isEmpty()) {
			sql = sql + " skewed by (";
			Iterator<String> p_entries = skewed_col_names.iterator();
			while (p_entries.hasNext()) {
				String p_entry = p_entries.next();
				sql = sql + p_entry;
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + ") on (";
			p_entries = skewed_col_values.iterator();
			while (p_entries.hasNext()) {
				String p_entry = p_entries.next();
				sql = sql + p_entry;
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + " ) ";

		}
		sql=sql+"ROW FORMAT DELIMITED  FIELDS TERMINATED BY '\\t' COLLECTION ITEMS TERMINATED BY '\\s'";

		if (!(exist_table_or_view_name == null || exist_table_or_view_name
				.equals(""))) {
			sql = sql + " like " + exist_table_or_view_name;

		}
		if (!(hdfs_path == null || hdfs_path.equals(""))) {
			sql = sql + " location " + hdfs_path;

		}

		System.out.println(sql);
		Statement stmt = HiveConnectionBean.getStmt();
		if (database_name == null || database_name.equals("")) {
			stmt.execute("use default");
		}
		System.out.println(sql);
		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Create table error:Fail to excute :" + sql);

		}
		stmt.close();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("create table:" + table_name + " in database:"
				+ database_name);
		historyRecord.setOp_sql(sql);
		historyRecord.setOp_res(true);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to create table!");

	}

	 /**
	  *dropTable 
	  * @param table_name
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#dropTable(java.lang.String, java.lang.String)
	  */
	@Override
	public void dropTable(String table_name, String database_name)
			throws Exception {
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
		String sql;
		sql = "drop table if exists " + table_name;
		System.out.println(sql);
		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Drop table error: :Fail to excute :" + sql);
		}
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Drop table:" + table_name + " in database:"
				+ database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to drop table");
		stmt.close();

	}

	 /**
	  *truncateTable 
	  * @param table_name
	  * @param partition_spec
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#truncateTable(java.lang.String, java.util.List, java.lang.String)
	  */
	@Override
	public void truncateTable(String table_name, List<Columns> partition_spec,
			String database_name) throws Exception {
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
		String sql = null;

		if (!partition_spec.isEmpty()) {
			sql = "truncate table " + table_name + " partition ( ";
			Iterator<Columns> entries = partition_spec.iterator();
			while (entries.hasNext()) {
				Columns entry = (Columns) entries.next();
				sql = sql + entry.getCols_name() + " = '"
						+ entry.getCols_type();
				if (entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "')";
		} else {
			sql = "truncate table " + table_name;
		}

		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Removw all rows from partition(s) error :Fail to excute :"
					+ sql);
		}
		log.info("Success to remove all rows from partition(s)");

		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("truncate Table:" + table_name
				+ " in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);

		stmt.close();

	}

	 /**
	  *renameTable 
	  * @param old_name
	  * @param new_name
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#renameTable(java.lang.String, java.lang.String, java.lang.String)
	  */
	@Override
	public void renameTable(String old_name, String new_name,
			String database_name) throws Exception {
		String sql = "alter table " + old_name + " rename to " + new_name;
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Rename table name error:  Fail to excute :" + sql);

		}
		log.info("Success to rename table \"" + old_name + "\"to \"" + new_name
				+ "\"");
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Rename table:" + old_name + "to table:"
				+ new_name + " in database:" + database_name);
		historyRecord.setOp_sql(sql);
		historyRecord.setOp_res(true);
		userHistoryLog.addHistotyRecord(historyRecord);
		stmt.close();

	}

	 /**
	  *changeColumn 
	  * @param table_name
	  * @param has_column
	  * @param col_old_name
	  * @param col_new_name
	  * @param col_type
	  * @param col_comment
	  * @param first_or_after
	  * @param after_col_name
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#changeColumn(java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	  */
	@Override
	public void changeColumn(String table_name, boolean has_column,
			String col_old_name, String col_new_name, String col_type,
			String col_comment, int first_or_after, String after_col_name,
			String database_name) throws Exception {

		String sql = "alter table " + table_name + " change ";
		if (has_column) {
			sql = sql + "column ";
		}
		sql = sql + " " + col_old_name + " " + col_new_name + " " + col_type
				+ " ";
		if (!(col_comment == null || col_comment.equals(""))) {
			sql = sql + " comment '" + col_comment + "'";
		}

		switch (first_or_after) {
		case 0:
			break;
		case 1:
			sql = sql + " first";
			break;
		case 2:
			sql = sql + " after " + after_col_name;
			break;
		}
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);

		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Change Column error:Fail to excute :" + sql);
		}
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Change table's Column :" + col_old_name
				+ "into Column:" + col_new_name + " in database:"
				+ database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Success to Change Column ");
		stmt.close();

	}

	 /**
	  *addorReplacecolumn 
	  * @param replace_or_add
	  * @param table_name
	  * @param columns
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#addorReplacecolumn(boolean, java.lang.String, java.util.List, java.lang.String)
	  */
	@Override
	public void addorReplacecolumn(boolean replace_or_add, String table_name,
			List<Columns> columns, String database_name) throws Exception {

		String sql = "";
		if (replace_or_add) {
			sql = "alter table " + table_name + " add columns (";
		} else {
			sql = "alter table " + table_name + " replace columns (";
		}
		Iterator<Columns> p_columns = columns.iterator();
		while (p_columns.hasNext()) {
			Columns p_column = (Columns) p_columns.next();
			sql = sql + p_column.getCols_name() + " " + p_column.getCols_type()
					+ " comment '" + p_column.getCols_comment() + "' ";
			if (p_columns.hasNext()) {
				sql = sql + " , ";
			}
		}
		sql = sql + " )";
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);

		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("add/replace columnserror:Fail to excute :" + sql);
		}
		log.info("Sucess to add/replace columns");
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("add columns to table:" + table_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Success to Change Column ");
		stmt.close();

	}

	 /**
	  *describeTable 
	  * @param table_name
	  * @param database_name
	  * @return
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#describeTable(java.lang.String, java.lang.String)
	  */
	@Override
	public List<Columns> describeTable(String table_name, String database_name)
			throws Exception {
		String sql = "describe " + table_name;
		Statement stmt = HiveConnectionBean.getStmt();
		if (database_name == null || database_name.equals("")) {
			stmt.execute("use default");
		} else {
			stmt.execute("use " + database_name);
		}
		ResultSet res = stmt.executeQuery(sql);
		List<Columns> tableDetails = new ArrayList<Columns>();

		while (res.next()) {
			if (res.getString(1) == null || res.getString(1).equals(""))
				break;
			Columns col = new Columns();
			col.setCols_name(res.getString(1));
			col.setCols_type(res.getString(2));
			col.setCols_comment(res.getString(3));
			tableDetails.add(col);
		}
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Show table: " + table_name
				+ "\'s details  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		res.close();
		stmt.close();
		userHistoryLog.addHistotyRecord(historyRecord);
		return tableDetails;
	}

	 /**
	  *showTable 
	  * @param database_name
	  * @return
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#showTable(java.lang.String)
	  */
	@Override
	public List<String> showTable(String database_name) throws Exception {
		Statement stmt = HiveConnectionBean.getStmt();
		if (database_name == null || database_name.equals("")) {
			stmt.execute("use default");
		} else {

			stmt.execute("use " + database_name);
			System.out.println(database_name);
		}
		String sql = "show tables ";
		ResultSet res = null;
		res = stmt.executeQuery(sql);
		List<String> tablelist = new ArrayList<String>();
		while (res.next()) {
			tablelist.add(res.getString(1));
		}
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Show  tabless  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);
		res.close();
		stmt.close();
		System.out.println(sql);
		return tablelist;
	}

	 /**
	  *showColumns 
	  * @param tb_in_or_from
	  * @param table_name
	  * @param database_name
	  * @param db_in_or_from
	  * @return
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#showColumns(boolean, java.lang.String, java.lang.String, boolean)
	  */
	@Override
	public List<String> showColumns(boolean tb_in_or_from, String table_name,
			String database_name, boolean db_in_or_from) throws Exception {
		List<String> columns_name = new ArrayList<String>();
		String sql = "show columns";
		if (tb_in_or_from) {
			sql = sql + " in ";
		} else {
			sql = sql + " from ";
		}
		sql = sql + table_name;
		if (!(database_name == null || database_name.equals(""))) {
			if (db_in_or_from) {
				sql = sql + " in ";
			} else {
				sql = sql + " from ";
			}
			sql = sql + database_name;
		}

		ResultSet res = null;
		Statement stmt = HiveConnectionBean.getStmt();

		res = stmt.executeQuery(sql);
		while (res.next()) {
			columns_name.add(res.getString(1));

		}
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Show Columns in table:" + table_name
				+ "  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);
		stmt.close();
		res.close();
		System.out.println(sql);
		return columns_name;
	}

	 /**
	  *selectFromTable 
	  * @param null_all_dist
	  * @param select_exprs
	  * @param table_name
	  * @param where_condition
	  * @param group_col_list
	  * @param cluster_col_list
	  * @param dis_col_list
	  * @param sort_col_list
	  * @param limit_num
	  * @param database_name
	  * @return
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#selectFromTable(int, java.util.List, java.lang.String, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, int, java.lang.String)
	  */
	@Override
	public List<List<String>> selectFromTable(int null_all_dist,
			List<String> select_exprs, String table_name,
			List<String> where_condition, List<String> group_col_list,
			List<String> cluster_col_list, List<String> dis_col_list,
			List<Columns> sort_col_list, int limit_num, String database_name)
			throws Exception {
		List<List<String>> table_data = new ArrayList<List<String>>();
		List<String> work = new ArrayList<String>();
		work = showColumns(true, table_name, database_name, true);
		int col_size = work.size();

		String sql = "select ";
		switch (null_all_dist) {
		case 0:
			break;
		case 1:
			sql = sql + " all ";
			break;
		case 2:
			sql = sql + " distinct ";
			break;

		}
		if (!select_exprs.isEmpty()) {

			Iterator<String> p_entries = select_exprs.iterator();
			while (p_entries.hasNext()) {
				sql = sql + p_entries.next();
				if (p_entries.hasNext()) {
					sql = sql + ", ";
				}
			}
		} else {
			sql = sql + " * ";
		}
		sql = sql + " from " + table_name + " ";
		if (!where_condition.isEmpty()) {
			sql = sql + "where ";
			Iterator<String> p_entries = where_condition.iterator();
			while (p_entries.hasNext()) {
				sql = sql + p_entries.next();
				if (p_entries.hasNext()) {
					sql = sql + " and ";
				}
			}
			sql = sql + " ";
		}
		if (!group_col_list.isEmpty()) {
			sql = sql + " group by ";
			Iterator<String> p_entries = group_col_list.iterator();
			while (p_entries.hasNext()) {
				sql = sql + p_entries.next();
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + " ";

		}
		if (!cluster_col_list.isEmpty()) {
			sql = sql + " cluster by ";
			Iterator<String> p_entries = cluster_col_list.iterator();
			while (p_entries.hasNext()) {
				sql = sql + p_entries.next();
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + " ";

		} else {
			if (!dis_col_list.isEmpty()) {
				sql = sql + " group by ";
				Iterator<String> p_entries = dis_col_list.iterator();
				while (p_entries.hasNext()) {
					sql = sql + p_entries.next();
					if (p_entries.hasNext()) {
						sql = sql + " , ";
					}
				}
				sql = sql + " ";

			}
			if (!sort_col_list.isEmpty()) {
				sql = sql + " sort by ";
				Iterator<Columns> p_entries = sort_col_list.iterator();
				while (p_entries.hasNext()) {
					Columns p_entry = p_entries.next();
					switch (p_entry.getCols_type()) {
					case "0":
						sql = sql + p_entry.getCols_name();
						break;
					case "1":
						sql = sql + p_entry.getCols_name() + " ASC ";
						break;
					case "2":
						sql = sql + p_entry.getCols_name() + " DESC";

					}
					if (p_entries.hasNext()) {
						sql = sql + " , ";
					}
				}
				sql = sql + " ";

			}

		}
		if (limit_num != 0) {
			sql = sql + " limit " + limit_num;

		}

		ResultSet res = null;
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);

		res = stmt.executeQuery(sql);
		while (res.next()) {
			List<String> work1 = new ArrayList<String>();
			for (int i = 1; i <= col_size; i++) {
				work1.add(res.getString(i));
				System.out.print(res.getString(i));
			}
			table_data.add(work1);
			System.out.println("\n");
		}
		stmt.close();
		res.close();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Show all data in table:" + table_name
				+ "  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);

		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);
		return table_data;

	}

	 /**
	  *loadFileIntoTable 
	  * @param inpath
	  * @param overwrite
	  * @param table_name
	  * @param local
	  * @param partition_spec
	  * @param database_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#loadFileIntoTable(java.lang.String, boolean, java.lang.String, boolean, java.util.List, java.lang.String)
	  */
	@Override
	public void loadFileIntoTable(String inpath, boolean overwrite,
			String table_name, boolean local, List<Columns> partition_spec,
			String database_name) throws Exception {
		String sql = "load data ";
		if (local) {
			sql = sql + "local ";
		}
		sql = sql + "inpath '" + inpath + "'  ";
		if (overwrite) {
			sql = sql + " overwrite ";
		}
		sql = sql + " into table " + table_name + " ";
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";
			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " ='"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "', ";
				}
			}
			sql = sql + "')";
		}

		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
		System.out.println(sql);
		stmt.execute(sql);
		stmt.close();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("load file into table:" + table_name
				+ "  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);

	}
	/*@Override
	public HashMap<String, String>  showPartition(String table_name,String database_name) throws Exception {
		String sql = "SHOW PARTITIONS  "+ table_name;
		
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
		HashMap<String, String> map = new LinkedHashMap<String, String>();

		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			map.put(res.getString(1), res.getString(1));

		}
		res.close();
		
		stmt.close();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc(" show partition table:" + table_name
				+ "  in database:" + database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);
		return map;

	}
*/
	 /**
	  *cloneTo 
	  * @return
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#cloneTo()
	  */
	@Override
	public HashMap<String, String> cloneTo() throws Exception {
		Statement stmt = HiveConnectionBean.getStmt();
		HashMap<String, String> map = new LinkedHashMap<String, String>();

		ResultSet res = stmt.executeQuery("show databases");
		while (res.next()) {
			map.put(res.getString(1), res.getString(1));

		}
		stmt.close();
		res.close();
		return map;
	}

	 /**
	  *cloneTable 
	  * @param database_name
	  * @param table_name
	  * @param to_database_name
	  * @param table_new_name
	  * @throws Exception 
	  * @see org.hiveadmin.hive.service.HiveTableService#cloneTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	  */
	@Override
	public void cloneTable(String database_name, String table_name,
			String to_database_name, String table_new_name) throws Exception {

		String sql = null;
		sql = "create table if not exists " + to_database_name + "."
				+ table_new_name + " like  " + database_name + "." + table_name;

		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute(sql);
		stmt.close();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("Clone table:" + table_name + "  in database:"
				+ database_name + " to database:" + to_database_name
				+ " named as:" + table_new_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Sucess to excute:" + sql);

	}

}
