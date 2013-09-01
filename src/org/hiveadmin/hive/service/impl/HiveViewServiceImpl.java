package org.hiveadmin.hive.service.impl;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import java.util.Map.Entry;

import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.hiveutils.HiveConnectionBean;

import java.util.Iterator;

public class HiveViewServiceImpl {
	private Logger log = Logger.getLogger(this.getClass());

	public void createView(boolean if_not_exists, String view_name,
			List<Columns> columns, String comment,
			List<Columns> tblproperties_spec, String select_sentance)
			throws Exception {
		String sql = "create view ";
		if (if_not_exists) {
			sql = sql + " if not exists ";
		}
		sql = sql + view_name;
		if (!columns.isEmpty()) {

			Iterator<Columns> p_entries = columns
					.iterator();
			sql = sql + " ( ";
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + " comment '"
						+ p_entry.getCols_type() + "' ";
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "') ";

		}
		if (!(comment == null || comment.equals(""))) {
			sql = sql + " comment '" + comment + "' ";
		}
		if (!tblproperties_spec.isEmpty()) {
			sql = sql + " TBLPROPERTIES (";

			Iterator<Columns> p_entries = tblproperties_spec.iterator();
			while (p_entries.hasNext()) {
				Columns p_entry = p_entries.next();
				sql = sql + p_entry.getCols_name() + "='" + p_entry.getCols_type() + "' ";
				if (p_entries.hasNext()) {
					sql = sql + "' , ";
				}

			}
			sql = sql + "') ";
		}
		sql = sql + select_sentance;
		Statement stmt=HiveConnectionBean.getStmt();
		boolean res = stmt.execute(sql);
		stmt.close();
		log.info("Sucess to create table!");

	}

	public void dropView(boolean if_exists, String view_name) throws Exception {
		String sql = "drop view ";
		if (if_exists) {
			sql = sql + " if exists ";
		}
		sql = sql + view_name;
		
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

	public void alterViewProperties(String view_name,
			List<Columns> table_properties) throws Exception {
		String sql = "alter view " + view_name + " set tblproperties (";
		Iterator<Columns> p_entries = table_properties
				.iterator();
		while (p_entries.hasNext()) {
			Columns p_entry = p_entries.next();
			sql = sql + p_entry.getCols_name() + "='" + p_entry.getCols_type() + "' ";
			if (p_entries.hasNext()) {
				sql = sql + "' , ";
			}

		}
		sql = sql + "') ";

		Statement stmt=HiveConnectionBean.getStmt();
		boolean res = stmt.execute(sql);
		stmt.close();
	
		if (!res) {
			log.error("Fail to excute :" + sql);
			throw new Exception("Fail to excute :" + sql);

		}
		log.info("Sucess to create table!");
		throw new Exception("Sucess to excute :" + sql);

	}

	public void alterViewAsSelect(String view_name, String select_sentance)
			throws Exception {
		String sql = "alter view " + view_name + " as " + select_sentance;
	
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
