package org.hiveadmin.hive.service.impl;

import org.hiveadmin.hive.hiveutils.HiveConnectionBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
//import org.hiveadmin.hive.service.HiveTableService;

import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.beans.Partition;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.springframework.stereotype.Component;

@Component
public class HiveTableServiceImpl {
	private Logger log = Logger.getLogger(this.getClass());
	private UserHistoryLog userHistoryLog = new UserHistoryLog();

	public UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}

	@Resource
	public void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		this.userHistoryLog = userHistoryLog;
	}

	public void createTables(boolean external, boolean if_not_exists,
			String database_name, String table_name, List<Columns> columns,
			String table_comment, List<Columns> P_columns,
			List<String> cluser_cols_name, List<Columns> clu_sorted_cols,
			String num_buckets, List<String> skewed_col_names,
			List<String> skewed_col_values, String row_format_value,
			String file_format, List<Columns> row_sorted_by_cols,
			String with_serdepropertiles_value, String hdfs_path,
			List<Partition> tblproperties_spec, String select_statement,
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
	 * @param table_name
	 *            :要删除的表名
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 *             DROP TABLE [IF EXISTS] table_name
	 */


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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hiveadmin.hive.service.HiveTableService#truncateTable(com.hiveadmin
	 * .hive.forms.truncateTableForm)
	 */
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
		historyRecord.setOp_desc("truncate Table:" + table_name + " in database:"
				+ database_name);
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
	
		stmt.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hiveadmin.hive.service.HiveTableService#addPartitions(com.hiveadmin
	 * .hive.forms.AddPartitionForm)
	 */
	public void addPartitions(String table_name, boolean if_not_exists,
			List<Partition> partition_spec_location) throws Exception {

		String sql = "alter table " + table_name + " add ";
		if (if_not_exists) {
			sql = sql + " if not exists ";
		}

		Iterator<Partition> P_entries = partition_spec_location.iterator();

		while (P_entries.hasNext()) {
			sql = sql + " partition (";
			Partition p_partition = P_entries.next();

			Iterator<Columns> p_spec = p_partition.getPartition_spec()
					.iterator();

			String location = p_partition.getLocation();
			while (p_spec.hasNext()) {
				Columns P_spec_entry = (Columns) p_spec.next();
				sql = sql + P_spec_entry.getCols_name() + "= '"
						+ P_spec_entry.getCols_type();
				if (p_spec.hasNext()) {
					sql = sql + "' , ";
				}
			}
			sql = sql + "') ";
			if (!(location == null || location.equals(""))) {
				sql = sql + " location '" + location + "' ";

			}

		}

		
		System.out.println(sql);

	}

	/**
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public void recoverPartitions(String table_name) throws Exception {
		if (table_name.equals("") || table_name == null) {
			log.error("Recover partition error: Table name is null!");

		}
		String sql = "alter table " + table_name + " recover partitions";

		
		  log.error("Recover partition error: Fail to excute :" + sql); 
		 log.info("Success to Recover partition");
		
		System.out.println(sql);

	}

	/**
	 * @param drop_partition
	 * @return
	 * @throws Exception
	 */
	public void dropPartition(String table_name, boolean if_exists,
			List<Partition> partition_spec, boolean ignore_protection)
			throws Exception {

		if (table_name == null || table_name.equals("")) {
			log.error("Drop partition error: Table name is null");
			// throw new Exception("Drop partition error: Table name is null");
		}
		String sql = "alter table " + table_name + " drop ";
		if (if_exists) {
			sql = sql + " if exists ";
		}

		Iterator<Partition> p_entries = partition_spec.iterator();
		while (p_entries.hasNext()) {
			Iterator<Columns> sp_entries = p_entries.next().getPartition_spec()
					.iterator();

			sql = sql + " partition (";
			while (sp_entries.hasNext()) {
				Columns p_entry = (Columns) sp_entries.next();
				sql = sql + p_entry.getCols_name() + " = '"
						+ p_entry.getCols_type();
				if (sp_entries.hasNext()) {
					sql = sql + "', ";
				}

			}
			sql = sql + "') ";

		}
		if (ignore_protection) {
			sql = sql + "ignore protection";
		}
		
	
		 log.error("Drop partition error: Fail to excute :" + sql); // throw
		  new Exception("Drop partition error: Fail to excute :" + sql); 
		  log.info("Success to drop partition");
		 
		System.out.println(sql);

	}

	/**
	 * @param old_name
	 * @param new_name
	 * @return
	 * @throws Exception
	 */
	public void renameTable(String old_name, String new_name,
			String database_name) throws Exception {
		String sql = "alter table " + old_name + " rename to " + new_name;

		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);

		System.out.println(sql);
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
		// System.out.println(sql);

	}

	/**
	 * @param change_col_form
	 * @return
	 * @throws Exception
	 */
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
		System.out.println(sql);
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
	 * @param add_col_form
	 * @return
	 * @throws Exception
	 */
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
		System.out.println(sql);
		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("add/replace columnserror:Fail to excute :" + sql);
		}
		log.info("Sucess to add/replace columns");
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String) ServletActionContext
				.getContext().getSession().get("user"));
		historyRecord.setOp_desc("add columns to table:"+table_name );
		historyRecord.setOp_res(true);
		historyRecord.setOp_sql(sql);
		userHistoryLog.addHistotyRecord(historyRecord);
		log.info("Success to Change Column ");
		stmt.close();

	}

	/**
	 * @param table_properties_form
	 * @return
	 * @throws Exception
	 */
	public void alterTbaleProperties(String table_name,
			List<Columns> table_properties, String database_name)
			throws Exception {

		String sql = "alter table " + table_name + " set tblproperties ( ";
		Iterator<Columns> p_entries = table_properties.iterator();
		while (p_entries.hasNext()) {
			Columns p_entry = (Columns) p_entries.next();
			sql = sql + p_entry.getCols_name() + " = '"
					+ p_entry.getCols_type();
			if (p_entries.hasNext()) {
				sql = sql + "', ";
			}

		}
		sql = sql + "')";
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);

		System.out.println(sql);
		boolean res = stmt.execute(sql);
		if (!res) {
			log.error("Rename table name error:  Fail to excute :" + sql);

		}

		System.out.println(sql);

	}

	/**
	 * @param add_serde_properties_form
	 * @return
	 * @throws Exception
	 */
	public void addSerdeProperties(String table_name, String serde_class_name,
			List<Columns> serde_properties) throws Exception {

		String sql;
		sql = "alter table " + table_name + "set ";
		if (!(serde_class_name == null || serde_class_name.equals(""))) {
			sql = sql + " serde " + serde_class_name;
			if (!serde_properties.isEmpty()) {
				sql = sql + " with serdeproperties ('";
				Iterator<Columns> p_entries = serde_properties.iterator();
				while (p_entries.hasNext()) {
					Columns p_entry = (Columns) p_entries.next();
					sql = sql + p_entry.getCols_name() + "' = '"
							+ p_entry.getCols_type();
					if (p_entries.hasNext()) {
						sql = sql + "' , '";
					}
				}
				sql = sql + "')";
			}
		} else {
			sql = sql + " serdeproperties ('";
			Iterator<Columns> p_entries = serde_properties.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = (Columns) p_entries.next();
				sql = sql + p_entry.getCols_name() + "' = '"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , '";
				}
			}
			sql = sql + "')";
		}
		 log.error("Add serde properties:Fail to excute :" + sql);
		 
		  log.info("Sucess to add serde properties");
		
		System.out.println(sql);

	}

	/**
	 * @param table_or_partition_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableorPartitonFormat(String table_name,
			List<Columns> partition_spec, String file_format) throws Exception {
		String sql = "alter table " + table_name + " ";
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";
			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " = '"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}
			}
			sql = sql + "') ";

		}
		sql = sql + "set fileformat " + file_format;
		 log.error("Alter Table/Partition file format :Fail to excute :" +
		 sql); log.info("Sucess to alter Table/Partition file format");
		 
		System.out.println(sql);

	}

	/**
	 * @param table_storage_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableStorageProperties(String table_name,
			List<String> clustered_col_names, List<String> sorted_col_names,
			String num_buckets) throws Exception {
		String sql = "alter table " + table_name + " clustered by ( ";

		Iterator<String> pc_entries = clustered_col_names.iterator();
		while (pc_entries.hasNext()) {
			String pc_entry = pc_entries.next();
			sql = sql + pc_entry;
			if (pc_entries.hasNext()) {
				sql = sql + " , ";
			}
		}
		sql = sql + ") ";
		if (!sorted_col_names.isEmpty()) {
			sql = sql + " sorted by (";
			Iterator<String> ps_entries = sorted_col_names.iterator();
			while (ps_entries.hasNext()) {
				String ps_entry = ps_entries.next();
				sql = sql + ps_entry;
				if (ps_entries.hasNext()) {
					sql = sql + " , ";
				}
			}
			sql = sql + ") ";
		}
		sql = sql + " into " + num_buckets + " buckets";

		
		  log.error("Alter Table storage properties:Fail to excute :" + sql);
		 log.info("Sucess to alter  Table storage properties");
		 
		System.out.println(sql);

	}

	/**
	 * @param table_or_partition_loc_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableorPartitionLocation(String table_name,
			List<Columns> partition_spec, String location) throws Exception {
		String sql = "alter table " + table_name + " ";
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";

			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " = '"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}
			}
			sql = sql + "') ";
		}
		sql = sql + "set location  " + location;
		log.error("Alter table or propertiesc location:Fail to excute :" +
		 sql);  log.info("Sucess to alter  table or propertiesc location");
		 
		System.out.println(sql);

	}

	/**
	 * @param table_touce_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableTouch(String table_name, List<Columns> partition_spec)
			throws Exception {
		String sql = "alter table " + table_name + " touch ";
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";

			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " = '"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}
			}
			sql = sql + "') ";
		}
		
		  log.error("Alter table touch:Fail to excute :" + sql);
		 log.info("Sucess to alter  table touch");
		 
		System.out.println(sql);

	}

	/**
	 * @param table_unorArchive_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableUnorArchive(boolean archive_or_unarchive,
			String table_name, List<Columns> partition_spec) throws Exception {

		String sql = "alter table " + table_name;
		if (archive_or_unarchive) {
			sql = sql + " archive partition (";
		} else {
			sql = sql + " unarchive partition (";
		}
		Iterator<Columns> p_entries = partition_spec.iterator();
		while (p_entries.hasNext()) {
			Columns p_entry = p_entries.next();
			sql = sql + p_entry.getCols_name() + " = '"
					+ p_entry.getCols_type();
			if (p_entries.hasNext()) {
				sql = sql + "' , ";
			}
		}
		sql = sql + "') ";

		 log.error("Alter table (Un)Archive:Fail to excute :" + sql);
		  log.info("Sucess to alter  table (Un)Archive");
		
		System.out.println(sql);

	}

	/**
	 * @param table_or_parttition_protection_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableorPartitionProtections(String table_name,
			List<Columns> partition_spec, boolean en_or_disable,
			boolean nodrop_or_offline) throws Exception {
		String sql = "alter table " + table_name;
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";

			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " = '"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}
			}
			sql = sql + "') ";
		}
		if (en_or_disable) {
			sql = sql + " enable ";
		} else {
			sql = sql + " disable ";
		}
		if (nodrop_or_offline) {
			sql = sql + " NO_DROP";
		} else {
			sql = sql + " OFFLINE";
		}
		 log.error
		  ("Alter table or Partition protection error:Fail to excute :" + sql);
	log.info("Sucess to alter  table or Partition protection");
		 
		System.out.println(sql);

	}

	/**
	 * @param table_rename_partition_form
	 * @return
	 * @throws Exception
	 */
	public void alterTableRenamePartition(String table_name,
			List<Columns> old_partition_spec, List<Columns> new_partition_spec)
			throws Exception {
		String sql = "alter table " + table_name + " partition (";
		Iterator<Columns> p_old_entries = old_partition_spec.iterator();
		while (p_old_entries.hasNext()) {
			Columns p_old_entry = p_old_entries.next();
			sql = sql + p_old_entry.getCols_name() + " = '"
					+ p_old_entry.getCols_type();
			if (p_old_entries.hasNext()) {
				sql = sql + "' , ";
			}
		}
		sql = sql + "') rename to partition (";

		Iterator<Columns> p_new_entries = new_partition_spec.iterator();
		while (p_new_entries.hasNext()) {
			Columns p_new_entry = p_new_entries.next();
			sql = sql + p_new_entry.getCols_name() + " = '"
					+ p_new_entry.getCols_type();
			if (p_new_entries.hasNext()) {
				sql = sql + "' , ";
			}
		}
		sql = sql + "') ";

	
		 log.error("Alter table rename partion error:Fail to excute :" + sql);
		 log.info("Sucess to alter table rename partion");
		 
		System.out.println(sql);

	}

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
	 * @return
	 * @throws Exception
	 */
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
	 * @param identifer
	 * @return
	 * @throws Exception
	 */
	public List<String> showTable(String identifier, String database_name)
			throws Exception {
		Statement stmt = HiveConnectionBean.getStmt();
		if (!(database_name == null || database_name.equals(""))) {
			stmt.execute("use " + database_name);
		} else {
			stmt.execute("use default");
		}
		String sql = "show tables (\"" + identifier + "\")";
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
	 * @param table_name
	 * @return
	 * @throws Exception
	 */
	public ResultSet showTableProperties(String table_name) throws Exception {
		String sql = "show tblproperties " + table_name;
		ResultSet res = null;
		 log.info("Sucess to excute:" + sql);
		 
		System.out.println(sql);
		return res;
	}

	/**
	 * @param table_name
	 * @param identifer
	 * @return
	 * @throws Exception
	 */
	public ResultSet ShowTableProperties(String table_name, String identifier)
			throws Exception {
		String sql = "show tblproperties " + table_name + " (\"" + identifier
				+ "\")";
		ResultSet res = null;
		 log.info("Sucess to excute:" + sql);
		 
		System.out.println(sql);
		return res;
	}

	/**
	 * @param show_partition_form
	 * @return
	 * @throws Exception
	 */
	public ResultSet showPartitions(String table_name,
			List<Columns> partition_spec) throws Exception {
		String sql = "show partitions " + table_name + " ";
		if (!partition_spec.isEmpty()) {
			sql = sql + "partition (";
			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + "='"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "')";

		}
		ResultSet res = null;
		 log.info("Sucess to excute:" + sql);
		 
		System.out.println(sql);
		return res;

	}

	/**
	 * @param show_taborpart_extend_form
	 * @return
	 * @throws Exception
	 */
	public ResultSet showTablePartitionExtended(String database_name,
			boolean in_or_from, String identifier, List<Columns> partition_spec)
			throws Exception {
		String sql = "show table extended ";
		if (!(database_name == null || database_name.equals(""))) {
			if (in_or_from) {
				sql = sql + " in ";
			} else {
				sql = sql + " out ";
			}
			sql = sql + database_name;

		}
		sql = sql + " like \"" + identifier + "\" ";
		if (!partition_spec.isEmpty()) {
			sql = sql + " partition (";
			Iterator<Columns> p_entries = partition_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + "='"
						+ p_entry.getCols_type();
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "')";

		}
		ResultSet res = null;
		log.info("Sucess to excute:" + sql);
		
		System.out.println(sql);
		return res;

	}

	/**
	 * @param show_create_table_form
	 * @return
	 * @throws Exception
	 */
	public ResultSet showCreateTable(String table_or_view_name,
			String database_name) throws Exception {
		String sql = "show create table ";

		if (!(database_name == null || database_name.equals(""))) {
			sql = sql + database_name + ".";
		}

		sql = sql + table_or_view_name;

		ResultSet res = null;
		 log.info("Sucess to excute:" + sql);
		 
		System.out.println(sql);
		return res;
	}

	/**
	 * @param columns_form
	 * @return
	 * @throws Exception
	 */
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
		System.out.println(sql);
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

	public ResultSet selectColumnByREGEX(String table_name, String REGEX)
			throws Exception {
		String sql = "select " + REGEX + " from " + table_name;
		ResultSet res = null;
		 log.info("Sucess to excute:" + sql);
		 
		System.out.println(sql);
		return res;

	}

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
		System.out.println(sql);
		Statement stmt = HiveConnectionBean.getStmt();
		stmt.execute("use " + database_name);
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

		System.out.println(sql);

	}

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

	public void cloneTable(String database_name, String table_name,
			String to_database_name, String table_new_name) throws Exception {
		
		String sql = null;
		sql = "create table if not exists " + to_database_name + "."
				+ table_new_name + " like  " + database_name + "."
				+ table_name;
		System.out.println(sql);
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
