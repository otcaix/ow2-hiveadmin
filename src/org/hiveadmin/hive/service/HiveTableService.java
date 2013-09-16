/**  
 * @Project: javaHiveAdimin
 * @Title: HiveTableService.java
 * @Package org.hiveadmin.hive.service
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.service;

import java.util.HashMap;
import java.util.List;

import org.hiveadmin.hive.beans.Columns;

/**
 * HiveTableService is created to describe
 * 
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 */
public interface HiveTableService {

	/**
	 * createTables is the method to achieve the creation of hive table.
	 * 
	 * @param external
	 *            Lets you create a table and provide a LOCATION so that Hive
	 *            does not use a default location for this table
	 * @param if_not_exists
	 *            To skip the error which is thrown if a table or view with the
	 *            same name already exists
	 * @param database_name
	 *            The name of database
	 * @param table_name
	 *            The name of table
	 * @param columns
	 *            The columns of table
	 * @param table_comment
	 *            The comment of table
	 * @param P_columns
	 *            The partition of table
	 * @param cluser_cols_name
	 *            The name of columns that is used to cluster
	 * @param clu_sorted_cols
	 *            The name of columns that is used to sort
	 * @param num_buckets
	 *            The number of buckets
	 * @param skewed_col_names
	 *            The name of columns that is used to skew
	 * @param skewed_col_values
	 *            The value of columns that is used to skew
	 * @param row_format_value
	 *            The format of row
	 * @param file_format
	 *            The format of file
	 * @param row_sorted_by_cols
	 *            The columns that is used to sort
	 * @param hdfs_path
	 *            The path of data file
	 * @param select_statement
	 *            The statement of selection
	 * @param exist_table_or_view_name
	 *            The name of an exist table or view
	 * @throws Exception
	 */
	public abstract void createTables(boolean external, boolean if_not_exists,
			String database_name, String table_name, List<Columns> columns,
			String table_comment, List<Columns> P_columns,
			List<String> cluser_cols_name, List<Columns> clu_sorted_cols,
			String num_buckets, List<String> skewed_col_names,
			List<String> skewed_col_values, String row_format_value,
			String file_format, List<Columns> row_sorted_by_cols,
			String hdfs_path, String select_statement,
			String exist_table_or_view_name) throws Exception;

	/**
	 * dropTable is the method to removes metadata and data for this table.
	 * 
	 * @param table_name
	 *            The name of table
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 */
	public abstract void dropTable(String table_name, String database_name)
			throws Exception;

	/**
	 * truncateTable is the method to removes all rows from a table or
	 * partition(s)
	 * 
	 * @param table_name
	 *            The name of table
	 * @param partition_spec
	 *            The name and value of partition
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 */
	public abstract void truncateTable(String table_name,
			List<Columns> partition_spec, String database_name)
			throws Exception;

	/**
	 * renameTable is the method to change the name of a table to a different
	 * name.
	 * 
	 * @param old_name
	 *            The original name of table
	 * @param new_name
	 *            The new name of table
	 * @param database_name
	 *            The name of database which the table belongs to
	 * @throws Exception
	 */
	public abstract void renameTable(String old_name, String new_name,
			String database_name) throws Exception;

	/**
	 * changeColumn is the method to change a column's name, data type, comment,
	 * or position.
	 * 
	 * @param table_name
	 *            The name of table
	 * @param has_column
	 *            The flag of "COLUMN" property
	 * @param col_old_name
	 *            The original name of column
	 * @param col_new_name
	 *            The new name of column
	 * @param col_type
	 *            The type of column
	 * @param col_comment
	 *            The comment of column
	 * @param first_or_after
	 *            The flag of relative position
	 * @param after_col_name
	 *            The name of relative column
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 */
	public abstract void changeColumn(String table_name, boolean has_column,
			String col_old_name, String col_new_name, String col_type,
			String col_comment, int first_or_after, String after_col_name,
			String database_name) throws Exception;

	/**
	 * addorReplacecolumn is the method to add new columns to the end of the
	 * existing columns but before the partition columns or to removes all
	 * existing columns and adds the new set of columns.
	 * 
	 * @param replace_or_add
	 *            The flag of add a column or replace columns
	 * @param table_name
	 *            The name of table
	 * @param columns
	 *            The columns that will be added or replace
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 */
	public abstract void addorReplacecolumn(boolean replace_or_add,
			String table_name, List<Columns> columns, String database_name)
			throws Exception;

	/**
	 * describeTable is the method to shows the list of columns including
	 * partition columns for the given table.
	 * 
	 * @param table_name
	 *            The name of table
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 * @return List<Columns> The information about tale's columns
	 */
	public abstract List<Columns> describeTable(String table_name,
			String database_name) throws Exception;

	/**
	 * showTable is the method to list all the base tables and views in the
	 * current database
	 * 
	 * @param database_name
	 *            The name of database
	 * @throws Exception
	 * @return List<String> The list of the table
	 */
	public abstract List<String> showTable(String database_name)
			throws Exception;

	/**
	 * showColumns is the method to show all the columns in a table including
	 * partition columns
	 * 
	 * @param tb_in_or_from
	 *            The flag of "IN" or "FROM" for table
	 * @param table_name
	 *            The name of table
	 * @param database_name
	 *            The name of database
	 * @param db_in_or_from
	 *            The flag of "IN" or "FROM" for database
	 * @throws Exception
	 * @return List<String> The list of columns in table
	 */
	public abstract List<String> showColumns(boolean tb_in_or_from,
			String table_name, String database_name, boolean db_in_or_from)
			throws Exception;

	/**
	 * selectFromTable is the method to select the data from table.
	 * 
	 * @param null_all_dist
	 *            The flag of "NULL" or "ALL" or "DISTINCT"
	 * @param select_exprs
	 *            The names of columns that will be selected from table
	 * @param table_name
	 *            The name of table
	 * @param where_condition
	 *            A boolean expression
	 * @param group_col_list
	 *            The names of columns will be as s group
	 * @param cluster_col_list
	 *            The names of columns that will be used in clustering
	 * @param dis_col_list
	 *            the name of columns that will be used in distributing
	 * @param sort_col_list
	 *            The names of columns will be used in sorting
	 * @param limit_num
	 *            The number of the result will be return
	 * @param database_name
	 *            the name of database
	 * @throws Exception
	 * @return List<List<String>> the list of data which user want
	 */
	public abstract List<List<String>> selectFromTable(int null_all_dist,
			List<String> select_exprs, String table_name,
			List<String> where_condition, List<String> group_col_list,
			List<String> cluster_col_list, List<String> dis_col_list,
			List<Columns> sort_col_list, int limit_num, String database_name)
			throws Exception;

	/**
	 * loadFileIntoTable is the method to load files into tables.<br>
	 * Hive does not do any transformation while loading data into tables. Load
	 * operations are currently pure copy/move operations that move datafiles
	 * into locations corresponding to Hive tables
	 * <p>
	 * 
	 * @param inpath
	 *            The path of file that will be loaded
	 * @param overwrite
	 *            The flag of property "OVERWRITE"
	 * @param table_name
	 *            The name of table
	 * @param local
	 *            The flag of "LOCATION"
	 * @param partition_spec
	 *            The name and value of partitions
	 * @param database_name
	 *            the name of database which table belongs to
	 * @throws Exception
	 */
	public abstract void loadFileIntoTable(String inpath, boolean overwrite,
			String table_name, boolean local, List<Columns> partition_spec,
			String database_name) throws Exception;

	/**
	 * cloneTo is the method to get the list of database
	 * 
	 * @throws Exception
	 * @return HashMap<String,String> the name and value of database ,this will
	 *         be used by the select tag in Strust2
	 */
	public abstract HashMap<String, String> cloneTo() throws Exception;

	/**
	 * cloneTable is the method to copy an exist table to an exist database and
	 * rename the table.
	 * 
	 * @param database_name
	 *            The name of database which the original table belongs to
	 * @param table_name
	 *            The name of original table
	 * @param to_database_name
	 *            The name of database which the table will be copy to
	 * @param table_new_name
	 *            The new name of table
	 * @throws Exception
	 */
	public abstract void cloneTable(String database_name, String table_name,
			String to_database_name, String table_new_name) throws Exception;
	
/*	public abstract HashMap<String, String>  showPartition(String table_name,String database_name) throws Exception ;
*/
}