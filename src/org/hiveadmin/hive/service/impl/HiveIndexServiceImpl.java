package org.hiveadmin.hive.service.impl;

import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.hiveutils.HiveConnectionBean;

public class HiveIndexServiceImpl {
	private Logger log = Logger.getLogger(this.getClass());

	public void createIndex(String index_name, String base_table_name,
			List<String> column_spec, String index_type,
			boolean with_deferred_rebuild, List<Columns> properties_spec,
			String index_table_name, String row_format, String stored_as,
			String stored_by_with, String hdfs_path,
			List<Columns> tblproperties, String index_comment)
			throws Exception {
		String sql = "create index " + index_name + " on table "
				+ base_table_name + " ";
		if (!column_spec.isEmpty()) {
			sql = sql + "( ";
			Iterator<String> p_entries = column_spec.iterator();
			while (p_entries.hasNext()) {
				sql = sql + p_entries.next();
				if (p_entries.hasNext()) {
					sql = sql + " , ";
				}

			}
			sql = sql + ") ";

		}
		sql = sql + " as " + index_type+ " ";
		if (with_deferred_rebuild) {
			sql = sql + " with deferres rebuild ";
		}
		if (!properties_spec.isEmpty()) {
			sql = sql + " idxproperties (";
			Iterator<Columns> p_entries = properties_spec.iterator();

			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + "='" + p_entry.getCols_type()+ "' ";
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "') ";

		}
		if (!(index_table_name == null || index_table_name.equals(""))) {
			sql = sql + " in table " + index_table_name;

		}
		if (!(stored_as == null ||stored_as.equals(""))) {
			sql = sql + " stored as " + stored_as + " ";

		} else {
			if (stored_by_with != null) {

				// 未完成
			}
		}
		if (!(hdfs_path == null || hdfs_path.equals(""))) {
			sql = sql + " location " + hdfs_path+ " ";

		}
		if (!tblproperties.isEmpty()) {
			sql = sql + " tblproperties (";
			Iterator<Columns> p_entries = tblproperties.iterator();

			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + "='" + p_entry.getCols_type() + "' ";
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "') ";
		}
		if (!(index_comment == null ||index_comment.equals(""))) {
			sql = sql +index_comment;
		}
		Statement stmt=HiveConnectionBean.getStmt();
		boolean res = stmt.execute(sql);
		stmt.close();

		log.info("Sucess to create table!");

	}

	public void dropIndex(boolean if_exists, String index_name, String table_name)
			throws Exception {
		String sql = "drop index ";
		if (if_exists) {
			sql = sql + " if exists ";
		}
		sql = sql + index_name + " on " + table_name;
	
		Statement stmt=HiveConnectionBean.getStmt();
		boolean res = stmt.execute(sql);
		stmt.close();
		/*
		 * if (!res) { log.error("Fail to excute :" + sql); throw new
		 * Exception("Fail to excute :" + sql);
		 * 
		 * }
		 */
		log.info("Sucess to create table!");
		// throw new Exception("Sucess to excute :" + sql);
	}

}
