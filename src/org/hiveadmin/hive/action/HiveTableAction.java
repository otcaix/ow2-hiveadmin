/**  
 * @Project: javaHiveAdimin
 * @Title: HiveTableAction.java
 * @Package org.hiveadmin.hive.action
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.service.HiveTableService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

/**
 * HiveTableAction can provide the base operating on table service for user.
 * HIveTableAction is based on the class named as
 * HiveTableSreviceImpl.HiveTableAction makes it very convenient to complete
 * table operation.
 * 
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 */

@Component
@Scope("session")
public class HiveTableAction extends ActionSupport {

	private static final long serialVersionUID = 4976591227541380974L;

	/**
	 * to_database_name:this property is the name of database which the table
	 * will be copied to
	 */
	private String to_database_name;
	/**
	 * table_new_name:this property is the new name of the table which will be
	 * copied
	 */
	private String table_new_name;
	/**
	 * databaselist:this property is the list of database, it is used in the
	 * select tag in Strust2
	 */
	private HashMap<String, String> databaselist;
	/**
	 * server:this property is to provide the services for this class
	 */
	private HiveTableService server;
	/**
	 * log:this property is to output the record of user's operation
	 */
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * external:this property is the table type,it is used when create a table
	 */
	private boolean external = false;
	/**
	 * if_not_exists:this property is to skip the error which is thrown if a
	 * table or view with the same name already exists
	 */
	private boolean if_not_exists = true;
	/**
	 * database_name:this property is the name of database
	 */
	private String database_name = "default";
	/**
	 * table_name:this property is the name of database
	 */
	private String table_name;
	/**
	 * columns:this property is the columns of table
	 */
	private List<Columns> columns = new ArrayList<Columns>();
	/**
	 * table_comment:this property is the comment of table
	 */
	private String table_comment = "";
	/**
	 * P_columns:this property is The partition of table
	 */
	private List<Columns> P_columns = new ArrayList<Columns>();
	/**
	 * cluser_cols_name:this property is the name of columns that is used to
	 * cluster
	 */
	private List<String> cluser_cols_name = new ArrayList<String>();
	/**
	 * clu_sorted_cols:this property is the name of columns that is used to sort
	 */
	private List<Columns> clu_sorted_cols = new ArrayList<Columns>();
	/**
	 * num_buckets:this property is the number of buckets
	 */
	private String num_buckets = "0";
	/**
	 * skewed_col_names:this property is the name of columns that is used to
	 * skew
	 */
	private List<String> skewed_col_names = new ArrayList<String>();
	/**
	 * skewed_col_values:this property is the value of columns that is used to
	 * skew
	 */
	private List<String> skewed_col_values = new ArrayList<String>();
	/**
	 * row_format_value:this property is the format of row
	 */
	private String row_format_value;
	/**
	 * file_format:this property is the format of file
	 */
	private String file_format;
	/**
	 * row_sorted_by_cols:this property is the columns that is used to sort
	 */
	private List<Columns> row_sorted_by_cols = new ArrayList<Columns>();
	/**
	 * hdfs_path:this property is the path of data file
	 */
	private String hdfs_path;
	/**
	 * select_statement:this property is the statement of selection
	 */
	private String select_statement;
	/**
	 * exist_table_or_view_name:this property is the name of an exist table or
	 * view
	 */
	private String exist_table_or_view_name;
	/**
	 * partition_spec:this property is the name and value of partition
	 */
	private List<Columns> partition_spec = new ArrayList<Columns>();
	/**
	 * old_name:this property is the original name of table
	 */
	private String old_name;
	/**
	 * new_name:this property is the new name of table
	 */
	private String new_name;
	/**
	 * has_column:this property is the flag of "COLUMN" property
	 */
	private boolean has_column = false;
	/**
	 * col_old_name:this property is the original name of column
	 */
	private String col_old_name;
	/**
	 * col_new_name:this property is the new name of column
	 */
	private String col_new_name;
	/**
	 * col_new_type:this property is the type of column
	 */
	private String col_new_type;
	/**
	 * col_new_comment:this property is the comment of column
	 */
	private String col_new_comment;
	/**
	 * first_or_after:this property is the flag of relative position
	 */
	private int first_or_after = 0;
	/**
	 * after_col_name:this property is the name of relative column
	 */
	private String after_col_name;
	/**
	 * replace_or_add:this property is the flag of add a column or replace
	 * columns
	 */
	private boolean replace_or_add = false;

	/**
	 * tb_in_or_from:this property is the flag of "IN" or "FROM" for table
	 */
	private boolean tb_in_or_from = true;
	/**
	 * db_in_or_from:this property is the flag of "IN" or "FROM" for database
	 */
	private boolean db_in_or_from = false;

	/**
	 * null_all_dist:this property is the flag of "NULL" or "ALL" or "DISTINCT"
	 */
	private int null_all_dist;
	/**
	 * select_exprs:this property is the names of columns that will be selected
	 * from table
	 */
	private List<String> select_exprs = new ArrayList<String>();
	/**
	 * where_condition:this property is a boolean expression
	 */
	private List<String> where_condition = new ArrayList<String>();
	/**
	 * group_col_list:this property is the names of columns will be as s group
	 */
	private List<String> group_col_list = new ArrayList<String>();
	/**
	 * cluster_col_list:this property is the names of columns that will be used
	 * in clustering
	 */
	private List<String> cluster_col_list = new ArrayList<String>();
	/**
	 * dis_col_list:this property is the name of columns that will be used in distributing
	 */

	private List<String> dis_col_list = new ArrayList<String>();
	/**
	 * sort_col_list:this property is The names of columns will be used in
	 * sorting
	 */
	private List<Columns> sort_col_list = new ArrayList<Columns>();
	/**
	 * limit_num:this property is The number of the result will be return
	 */
	private int limit_num = 0;

	/**
	 * inpath:this property is The path of file that will be loaded
	 */
	private String inpath;
	/**
	 * overwrite:this property is The flag of property "OVERWRITE"
	 */
	private boolean overwrite;
	/**
	 * local:this property is The flag of "LOCATION"
	 */
	private boolean local;
	/**
	 * columns_name:this property is list of columns'name
	 */
	List<String> columns_name;
	/**
	 * tableList:this property is the list of table
	 */
	private List<String> tableList = new ArrayList<String>();
	/**
	 * tableDetails:this property is the list of columns of a table
	 */
	private List<Columns> tableDetails = new ArrayList<Columns>();
	/**
	 * table_data:this property is the list of data from a table
	 */
	private List<List<String>> table_data;

	/**
	 * createTable is the method to provide the service about creating a table
	 * 
	 * @return String
	 */
	public String createTable() {
		if (table_name == null || table_name.equals("")) {
			log.error("table name is null");
			return INPUT;

		}
		try {
			server.createTables(external, if_not_exists, database_name,
					table_name, columns, table_comment, P_columns,
					cluser_cols_name, clu_sorted_cols, num_buckets,
					skewed_col_names, skewed_col_values, row_format_value,
					file_format, row_sorted_by_cols, hdfs_path,
					select_statement, exist_table_or_view_name);
			log.info("Sucess to create table!");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());
			return ERROR;
		}

	}

	/**
	 * dropTable is the method to provide the service: removes metadata and data
	 * for this table.
	 * 
	 * @return String
	 */
	public String dropTable() {

		if (table_name == null || table_name.equals("")) {
			log.error("table name is null");
			return INPUT;

		}
		try {
			server.dropTable(table_name, database_name);
			log.info("Sucess to drop table");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * truncateTable is the method to provide the service: removes all rows from
	 * a table or partition(s)
	 * 
	 * @return String
	 */
	public String truncateTable() {
		if (table_name == null || table_name.equals("")) {
			log.error("table name is null");
			return INPUT;

		}
		try {
			server.truncateTable(table_name, partition_spec, database_name);
			log.info("Success to remove all rows from partition(s)");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	/**
	 * renameTable is the method to provide the service: change the name of a
	 * table to a different name.
	 * 
	 * @return String
	 */
	public String renameTable() {
		boolean result = false;
		if (old_name.equals("") || old_name == null) {
			log.error("Rename table name error: Old name is null");
			result = true;
		}
		if (new_name.equals("") || new_name == null) {
			log.error("Rename table name error: New name is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.renameTable(old_name, new_name, database_name);

			log.info("Success to rename table \"" + old_name + "\"to \""
					+ new_name + "\"");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * changeColumn is the method to provide the service: change a column's
	 * name, data type, comment, or position.
	 * 
	 * @return String
	 */
	public String changeColumn() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Change Column error: Table name is null");
			result = true;
		}
		if (col_old_name.equals("") || col_old_name == null) {
			log.error("Change Column error: column old name is null");
			result = true;
		}
		if (col_new_name.equals("") || col_new_name == null) {
			log.error("Change Column error: Column new name is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.changeColumn(table_name, has_column, col_old_name,
					col_new_name, col_new_type, col_new_comment,
					first_or_after, after_col_name, database_name);

			log.info("Success to Change Column ");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * addorReplacecolumn is the method to provide the service: add new columns
	 * to the end of the existing columns but before the partition columns or to
	 * removes all existing columns and adds the new set of columns.
	 * 
	 * @return String
	 */
	public String addorReplacecolumn() {

		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Add/replace columnserror: Table name is null");
			result = true;
		}

		if (columns.isEmpty()) {
			log.error("Aadd/replace columnserror:Column new name is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.addorReplacecolumn(true, table_name, columns, database_name);

			log.info("Sucess to add/replace columns");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * showTable is the method to provide the service:list all the base tables
	 * and views in the current database
	 * 
	 * @return String
	 */
	public String showTable() {

		try {
			tableList = server.showTable(database_name);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * showColumns is the method to provide the service:show all the columns in
	 * a table including partition columns
	 * 
	 * @return String
	 */
	public String showColumns() {
		if (table_name.equals("") || table_name == null) {
			log.error("Show columns error:Table name is null");
			return INPUT;
		}
		try {
			columns_name = server.showColumns(tb_in_or_from, table_name,
					database_name, db_in_or_from);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * selectFromTable is the method to provide the service:select the data from
	 * table.
	 * 
	 * @return String
	 */
	public String selectFromTable() {
		if (table_name.equals("") || table_name == null) {
			log.error("Select from table error:Table name is null");
			return INPUT;
		}
		showColumns();
		try {

			table_data = server.selectFromTable(null_all_dist, select_exprs,
					table_name, where_condition, group_col_list,
					cluster_col_list, dis_col_list, sort_col_list, limit_num,
					database_name);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * loadFileIntoTable is the method to provide the service:load files into
	 * tables.<br>
	 * 
	 * @return String
	 */
	public String loadFileIntoTable() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Load file into table error:Table name is null");
			result = true;
		}
		if (inpath == null || inpath.equals("")) {
			log.error("Load file into table error:REGEX is null");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			server.loadFileIntoTable(inpath, overwrite, table_name, local,
					partition_spec, database_name);
			log.info("Sucess to load file into table");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * tableDetails is the method to provide the service:shows the list of
	 * columns including partition columns for the given table.
	 * 
	 * @return String
	 */
	public String tableDetails() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Load file into table error:Table name is null");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			tableDetails = server.describeTable(table_name, database_name);
			log.info("Sucess to load file into table");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	/**
	 * cloneTo is the method to provide the service:
	 * 
	 * @return String
	 */
	public String cloneTo() {
		try {
			databaselist = server.cloneTo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * cloneTable is the method to provide the service: copy an exist table to
	 * an exist database and rename the table.
	 * 
	 * @return String
	 */
	public String cloneTable() {
		if (table_new_name.equals("") || table_new_name == null) {
			log.error("Load file into table error:Table name is null");
			addFieldError("tablename", "table name can't be null");
			return INPUT;
		}
		try {
			server.cloneTable(database_name, table_name, to_database_name,
					table_new_name);
			log.info("Sucess to drop table");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public HiveTableService getServer() {
		return server;
	}

	@Resource(name = "hiveTableServiceImpl")
	public void setServer(HiveTableService server) {
		this.server = server;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public boolean isIf_not_exists() {
		return if_not_exists;
	}

	public void setIf_not_exists(boolean if_not_exists) {
		this.if_not_exists = if_not_exists;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public List<Columns> getColumns() {
		return columns;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}

	public String getTable_comment() {
		return table_comment;
	}

	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}

	public List<Columns> getP_columns() {
		return P_columns;
	}

	public void setP_columns(List<Columns> p_columns) {
		P_columns = p_columns;
	}

	public List<String> getCluser_cols_name() {
		return cluser_cols_name;
	}

	public void setCluser_cols_name(List<String> cluser_cols_name) {
		this.cluser_cols_name = cluser_cols_name;
	}

	public List<Columns> getClu_sorted_cols() {
		return clu_sorted_cols;
	}

	public void setClu_sorted_cols(List<Columns> clu_sorted_cols) {
		this.clu_sorted_cols = clu_sorted_cols;
	}

	public String getNum_buckets() {
		return num_buckets;
	}

	public void setNum_buckets(String num_buckets) {
		this.num_buckets = num_buckets;
	}

	public List<String> getSkewed_col_names() {
		return skewed_col_names;
	}

	public void setSkewed_col_names(List<String> skewed_col_names) {
		this.skewed_col_names = skewed_col_names;
	}

	public List<String> getSkewed_col_values() {
		return skewed_col_values;
	}

	public void setSkewed_col_values(List<String> skewed_col_values) {
		this.skewed_col_values = skewed_col_values;
	}

	public String getRow_format_value() {
		return row_format_value;
	}

	public void setRow_format_value(String row_format_value) {
		this.row_format_value = row_format_value;
	}

	public String getFile_format() {
		return file_format;
	}

	public void setFile_format(String file_format) {
		this.file_format = file_format;
	}

	public List<Columns> getRow_sorted_by_cols() {
		return row_sorted_by_cols;
	}

	public void setRow_sorted_by_cols(List<Columns> row_sorted_by_cols) {
		this.row_sorted_by_cols = row_sorted_by_cols;
	}


	public String getHdfs_path() {
		return hdfs_path;
	}

	public void setHdfs_path(String hdfs_path) {
		this.hdfs_path = hdfs_path;
	}

	public String getSelect_statement() {
		return select_statement;
	}

	public void setSelect_statement(String select_statement) {
		this.select_statement = select_statement;
	}

	public String getExist_table_or_view_name() {
		return exist_table_or_view_name;
	}

	public void setExist_table_or_view_name(String exist_table_or_view_name) {
		this.exist_table_or_view_name = exist_table_or_view_name;
	}

	public List<Columns> getPartition_spec() {
		return partition_spec;
	}

	public void setPartition_spec(List<Columns> partition_spec) {
		this.partition_spec = partition_spec;
	}

	public String getOld_name() {
		return old_name;
	}

	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

	public String getNew_name() {
		return new_name;
	}

	public void setNew_name(String new_name) {
		this.new_name = new_name;
	}

	public boolean isHas_column() {
		return has_column;
	}

	public void setHas_column(boolean has_column) {
		this.has_column = has_column;
	}

	public String getCol_old_name() {
		return col_old_name;
	}

	public void setCol_old_name(String col_old_name) {
		this.col_old_name = col_old_name;
	}

	public String getCol_new_name() {
		return col_new_name;
	}

	public void setCol_new_name(String col_new_name) {
		this.col_new_name = col_new_name;
	}

	public int getFirst_or_after() {
		return first_or_after;
	}

	public void setFirst_or_after(int first_or_after) {
		this.first_or_after = first_or_after;
	}

	public String getAfter_col_name() {
		return after_col_name;
	}

	public String getCol_new_type() {
		return col_new_type;
	}

	public void setCol_new_type(String col_new_type) {
		this.col_new_type = col_new_type;
	}

	public String getCol_new_comment() {
		return col_new_comment;
	}

	public void setCol_new_comment(String col_new_comment) {
		this.col_new_comment = col_new_comment;
	}

	public void setAfter_col_name(String after_col_name) {
		this.after_col_name = after_col_name;
	}

	public boolean isReplace_or_add() {
		return replace_or_add;
	}

	public void setReplace_or_add(boolean replace_or_add) {
		this.replace_or_add = replace_or_add;
	}

	public String getDatabase_name() {
		return database_name;
	}

	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}

	public boolean isTb_in_or_from() {
		return tb_in_or_from;
	}

	public void setTb_in_or_from(boolean tb_in_or_from) {
		this.tb_in_or_from = tb_in_or_from;
	}

	public boolean isDb_in_or_from() {
		return db_in_or_from;
	}

	public void setDb_in_or_from(boolean db_in_or_from) {
		this.db_in_or_from = db_in_or_from;
	}

	public int getNull_all_dist() {
		return null_all_dist;
	}

	public void setNull_all_dist(int null_all_dist) {
		this.null_all_dist = null_all_dist;
	}

	public List<String> getSelect_exprs() {
		return select_exprs;
	}

	public void setSelect_exprs(List<String> select_exprs) {
		this.select_exprs = select_exprs;
	}

	public List<String> getWhere_condition() {
		return where_condition;
	}

	public void setWhere_condition(List<String> where_condition) {
		this.where_condition = where_condition;
	}

	public List<String> getGroup_col_list() {
		return group_col_list;
	}

	public void setGroup_col_list(List<String> group_col_list) {
		this.group_col_list = group_col_list;
	}

	public List<String> getCluster_col_list() {
		return cluster_col_list;
	}

	public void setCluster_col_list(List<String> cluster_col_list) {
		this.cluster_col_list = cluster_col_list;
	}

	public List<String> getDis_col_list() {
		return dis_col_list;
	}

	public void setDis_col_list(List<String> dis_col_list) {
		this.dis_col_list = dis_col_list;
	}

	public List<Columns> getSort_col_list() {
		return sort_col_list;
	}

	public void setSort_col_list(List<Columns> sort_col_list) {
		this.sort_col_list = sort_col_list;
	}

	public int getLimit_num() {
		return limit_num;
	}

	public void setLimit_num(int limit_num) {
		this.limit_num = limit_num;
	}

	public String getInpath() {
		return inpath;
	}

	public void setInpath(String inpath) {
		this.inpath = inpath;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	public String getTo_database_name() {
		return to_database_name;
	}

	public void setTo_database_name(String to_database_name) {
		this.to_database_name = to_database_name;
	}

	public String getTable_new_name() {
		return table_new_name;
	}

	public void setTable_new_name(String table_new_name) {
		this.table_new_name = table_new_name;
	}

	public HashMap<String, String> getDatabaselist() {
		return databaselist;
	}

	public void setDatabaselist(HashMap<String, String> databaselist) {
		this.databaselist = databaselist;
	}

	public List<String> getColumns_name() {
		return columns_name;
	}

	public void setColumns_name(List<String> columns_name) {
		this.columns_name = columns_name;
	}

	public List<List<String>> getTable_data() {
		return table_data;
	}

	public void setTable_data(List<List<String>> table_data) {
		this.table_data = table_data;
	}

	public List<Columns> getTableDetails() {
		return tableDetails;
	}

	public void setTableDetails(List<Columns> tableDetails) {
		this.tableDetails = tableDetails;
	}

}
