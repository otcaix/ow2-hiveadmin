package org.hiveadmin.hive.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.beans.Partition;
import org.hiveadmin.hive.service.impl.HiveTableServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("session")
public class HiveTableAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4976591227541380974L;
	private String to_database_name;
	public String getTo_database_name() {
		return to_database_name;
	}

	public void setTo_database_name(String to_database_name) {
		this.to_database_name = to_database_name;
	}

	private String table_new_name;
	private  HashMap<String,String> databaselist;
	private HiveTableServiceImpl server;
	private Logger log = Logger.getLogger(this.getClass());
	private boolean external = false;
	private boolean if_not_exists = true;
	private String database_name = "default";
	private String table_name;
	private List<Columns> columns = new ArrayList<Columns>();
	private String table_comment = "";
	private List<Columns> P_columns = new ArrayList<Columns>();
	private List<String> cluser_cols_name = new ArrayList<String>();
	private List<Columns> clu_sorted_cols = new ArrayList<Columns>();
	private String num_buckets = "0";
	private List<String> skewed_col_names = new ArrayList<String>();
	private List<String> skewed_col_values = new ArrayList<String>();
	private String row_format_value;
	private String file_format;
	private List<Columns> row_sorted_by_cols = new ArrayList<Columns>();
	private String with_serdepropertiles_value;
	private String hdfs_path;
	private List<Partition> tblproperties_spec = new ArrayList<Partition>();
	private String select_statement;
	private String exist_table_or_view_name;

	private boolean if_exists = false;

	private List<Columns> partition_spec = new ArrayList<Columns>();

	private List<Partition> partition_spec_location;

	private boolean ignore_protection = false;

	private String old_name;
	private String new_name;

	private boolean has_column = false;
	private String col_old_name;
	private String col_new_name;
	private String col_new_type;
	private String col_new_comment;
	private int first_or_after = 0;
	private String after_col_name;

	private boolean replace_or_add = false;

	private List<Columns> table_properties = new ArrayList<Columns>();
	private String serde_class_name;
	private List<Columns> serde_properties = new ArrayList<Columns>();

	private List<String> clustered_col_names = new ArrayList<String>();
	private List<String> sorted_col_names = new ArrayList<String>();

	private String location;

	private boolean archive_or_unarchive = false;

	private boolean en_or_disable = false;
	private boolean nodrop_or_offline = false;

	private List<Columns> old_partition_spec = new ArrayList<Columns>();
	private List<Columns> new_partition_spec = new ArrayList<Columns>();

	private boolean in_or_from = false;
	private String identifier;
	private boolean tb_in_or_from = true;

	private boolean db_in_or_from = false;
	private String table_or_view_name;

	private int null_all_dist;
	private List<String> select_exprs = new ArrayList<String>();

	private List<String> where_condition = new ArrayList<String>();
	private List<String> group_col_list = new ArrayList<String>();
	private List<String> cluster_col_list = new ArrayList<String>();
	private List<String> dis_col_list = new ArrayList<String>();
	private List<Columns> sort_col_list = new ArrayList<Columns>();
	private int limit_num = 0;

	private String REGEX;

	private String inpath;
	private boolean overwrite;
	private boolean local;
	List<String> columns_name;
	private List<String> tableList = new ArrayList<String>();
	private List<Columns> tableDetails = new ArrayList<Columns>();
	private List<List<String>> table_data;



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

	/**
	 * @return
	 */
	public List<Columns> getTableDetails() {
		return tableDetails;
	}

	public void setTableDetails(List<Columns> tableDetails) {
		this.tableDetails = tableDetails;
	}

	private ResultSet res = null;

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
					file_format, row_sorted_by_cols,
					with_serdepropertiles_value, hdfs_path, tblproperties_spec,
					select_statement, exist_table_or_view_name);
			log.info("Sucess to create table!");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());
			return ERROR;
		}

	}

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

	public String addPartition() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Add partitions error: Table_name is null");
			result = true;
		}
		if (partition_spec_location.isEmpty()) {
			log.error("Add partitions error: Patitions is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.addPartitions(table_name, if_not_exists,
					partition_spec_location);
			log.info("Success to add partition");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String recverPartitions() {
		if (table_name.equals("") || table_name == null) {
			log.error("Recover partition error: Table name is null!");
			return INPUT;
		}
		try {
			server.recoverPartitions(table_name);
			log.info("Success to Recover partition");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String dropPartition() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Drop partition error: Table name is null");
			result = true;
		}
		if (partition_spec_location.isEmpty()) {
			log.error("Drop partition error: Patitions is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.dropPartition(table_name, if_exists,
					partition_spec_location, ignore_protection);
			log.info("Success to drop partition");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

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

	public String alterTbaleProperties() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter table properties:Table name is null");
			result = true;
		}

		if (table_properties.isEmpty()) {
			log.error("Alter table properties:Table properties is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.alterTbaleProperties(table_name, table_properties,
					database_name);
			log.info("Sucess to alter table properties");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String addSerdeProperties() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Add serde properties error:Table name is null");
			result = true;
		}

		if ((serde_class_name == null || serde_class_name.equals(""))
				&& serde_properties.isEmpty()) {
			log.error("Add serde properties error:Serde class name and Serde properties can't be null at the same time");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.addSerdeProperties(table_name, serde_class_name,
					serde_properties);
			log.info("Sucess to add serde properties");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String alterTableorPartitonFormat() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter Table/Partition file format error:Table name is null");
			result = true;
		}

		if ((file_format == null || file_format.equals(""))
				&& serde_properties.isEmpty()) {
			log.error("Alter Table/Partition file format error:File format is null");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.alterTableorPartitonFormat(table_name, partition_spec,
					file_format);
			log.info("Sucess to alter Table/Partition file format");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String alterTableStorageProperties() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter Table storage properties error:Table name is null");
			result = true;
		}

		if (clustered_col_names.isEmpty()) {
			log.error("Alter Table storage properties error:Clustered clolumn names is null");
			result = true;

		}
		if (Integer.parseInt(num_buckets) < 0) {
			log.error("Alter Table storage properties error:Number od buckets must >= 0");
			result = true;

		}
		if (result) {
			return INPUT;
		}
		try {
			server.alterTableStorageProperties(table_name, clustered_col_names,
					sorted_col_names, num_buckets);
			log.info("Sucess to alter  Table storage properties");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String alterTableorPartitionLocation() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter table or propertiesc location error:Table name is null");
			result = true;
		}
		if (location.equals("") || location == null) {
			log.error("Alter table or propertiesc location error:Location is null");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			server.alterTableorPartitionLocation(table_name, partition_spec,
					location);
			log.info("Sucess to alter  table or propertiesc location");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String alterTableTouch() {

		if (table_name.equals("") || table_name == null) {
			log.error("Alter table touch error:Table name is null");
			return INPUT;
		}

		try {
			server.alterTableTouch(table_name, partition_spec);
			log.info("Sucess to alter  table touch");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String alterTableUnorArchive() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter table (Un)Archive error:Table name is null");
			result = true;
		}
		if (partition_spec.isEmpty()) {
			log.error("Alter table (Un)Archive error:Partition_spec is null");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			server.alterTableUnorArchive(archive_or_unarchive, table_name,
					partition_spec);
			log.info("Sucess to alter  table (Un)Archive");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String alterTableorPartitionProtections() {
		if (table_name.equals("") || table_name == null) {
			log.error("Alter table or Partition protection error:Table name is null");
			return INPUT;
		}
		try {
			server.alterTableorPartitionProtections(table_name, partition_spec,
					en_or_disable, nodrop_or_offline);
			log.info("Sucess to alter  table or Partition protection");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String alterTableRenamePartition() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Alter table rename partion error:Table name is null");
			result = true;
		}
		if (old_partition_spec.isEmpty()) {
			log.error("Alter table rename partion error:Old partition_spec is null");
			result = true;
		}
		if (new_partition_spec.isEmpty()) {
			log.error("Alter table rename partion error:New partition_spec is null");
			result = true;
		}
		if (old_partition_spec.size() != new_partition_spec.size()) {
			log.error("Alter table rename partion error:The two Partition_specs have different size");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			server.alterTableRenamePartition(table_name, old_partition_spec,
					new_partition_spec);
			log.info("Sucess to alter table rename partion");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String showTable() {

		try {

			if (identifier == null || identifier.equals("")) {
				tableList = server.showTable(database_name);
			} else {
				tableList = server.showTable(identifier, database_name);
			}
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String showTableProperties() {
		if (table_name.equals("") || table_name == null) {
			log.error("show Table Properties error:Table name is null");
			return INPUT;
		}
		try {
			if (identifier == null || identifier.equals("")) {
				res = server.showTableProperties(table_name);
			} else {
				res = server.ShowTableProperties(table_name, identifier);
			}
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String showPartitions() {
		if (table_name.equals("") || table_name == null) {
			log.error("show partition error:Table name is null");
			return INPUT;
		}
		try {
			res = server.showPartitions(table_name, partition_spec);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String showTablePartitionExtended() {
		if (identifier.equals("") || identifier == null) {
			log.error("show table partition extended error:identifier is null");
			return INPUT;
		}
		try {
			res = server.showTablePartitionExtended(database_name, in_or_from,
					identifier, partition_spec);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

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

	public String showCreateTable() {
		if ((database_name != null)
				&& (table_or_view_name == null || table_or_view_name.equals(""))) {
			log.error("show Create Table error:database name is not null but table name is null");
			return INPUT;
		}
		try {
			res = server.showCreateTable(table_or_view_name, database_name);
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

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

	public String selectColumnByREGEX() {
		boolean result = false;
		if (table_name.equals("") || table_name == null) {
			log.error("Select column by REGEX error:Table name is null");
			result = true;
		}
		if (REGEX == null || REGEX.equals("")) {
			log.error("Select column by REGEX error:REGEX is null");
			result = true;
		}

		if (result) {
			return INPUT;
		}
		try {
			res = server.selectColumnByREGEX(table_name, REGEX);
			log.info("Sucess to select column by REGEX");
			return SUCCESS;
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

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

	public String cloneTo() {
		try {
			databaselist = server.cloneTo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
      public String cloneTable(){
    	  if (table_new_name.equals("") || table_new_name == null) {
  			log.error("Load file into table error:Table name is null");
  			addFieldError("tablename", "table name can't be null");
  			return INPUT;
    	  }
  			try {
  				server.cloneTable(database_name,table_name,to_database_name,table_new_name);
  				log.info("Sucess to drop table");
  				return SUCCESS;
  			} catch (Exception e) {

  				e.printStackTrace();
  				log.error(e.toString());
  				return ERROR;
  			}
    	
    	  
	
      }
	public HiveTableServiceImpl getServer() {
		return server;
	}

	@Resource(name = "hiveTableServiceImpl")
	public void setServer(HiveTableServiceImpl server) {
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

	public String getWith_serdepropertiles_value() {
		return with_serdepropertiles_value;
	}

	public void setWith_serdepropertiles_value(
			String with_serdepropertiles_value) {
		this.with_serdepropertiles_value = with_serdepropertiles_value;
	}

	public String getHdfs_path() {
		return hdfs_path;
	}

	public void setHdfs_path(String hdfs_path) {
		this.hdfs_path = hdfs_path;
	}

	public List<Partition> getTblproperties_spec() {
		return tblproperties_spec;
	}

	public void setTblproperties_spec(List<Partition> tblproperties_spec) {
		this.tblproperties_spec = tblproperties_spec;
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

	public boolean isIf_exists() {
		return if_exists;
	}

	public void setIf_exists(boolean if_exists) {
		this.if_exists = if_exists;
	}

	public List<Columns> getPartition_spec() {
		return partition_spec;
	}

	public void setPartition_spec(List<Columns> partition_spec) {
		this.partition_spec = partition_spec;
	}

	public List<Partition> getPartition_spec_location() {
		return partition_spec_location;
	}

	public void setPartition_spec_location(
			List<Partition> partition_spec_location) {
		this.partition_spec_location = partition_spec_location;
	}

	public boolean isIgnore_protection() {
		return ignore_protection;
	}

	public void setIgnore_protection(boolean ignore_protection) {
		this.ignore_protection = ignore_protection;
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

	public List<Columns> getTable_properties() {
		return table_properties;
	}

	public void setTable_properties(List<Columns> table_properties) {
		this.table_properties = table_properties;
	}

	public String getSerde_class_name() {
		return serde_class_name;
	}

	public void setSerde_class_name(String serde_class_name) {
		this.serde_class_name = serde_class_name;
	}

	public List<Columns> getSerde_properties() {
		return serde_properties;
	}

	public void setSerde_properties(List<Columns> serde_properties) {
		this.serde_properties = serde_properties;
	}

	public List<String> getClustered_col_names() {
		return clustered_col_names;
	}

	public void setClustered_col_names(List<String> clustered_col_names) {
		this.clustered_col_names = clustered_col_names;
	}

	public List<String> getSorted_col_names() {
		return sorted_col_names;
	}

	public void setSorted_col_names(List<String> sorted_col_names) {
		this.sorted_col_names = sorted_col_names;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isArchive_or_unarchive() {
		return archive_or_unarchive;
	}

	public void setArchive_or_unarchive(boolean archive_or_unarchive) {
		this.archive_or_unarchive = archive_or_unarchive;
	}

	public boolean isEn_or_disable() {
		return en_or_disable;
	}

	public void setEn_or_disable(boolean en_or_disable) {
		this.en_or_disable = en_or_disable;
	}

	public boolean isNodrop_or_offline() {
		return nodrop_or_offline;
	}

	public void setNodrop_or_offline(boolean nodrop_or_offline) {
		this.nodrop_or_offline = nodrop_or_offline;
	}

	public List<Columns> getOld_partition_spec() {
		return old_partition_spec;
	}

	public void setOld_partition_spec(List<Columns> old_partition_spec) {
		this.old_partition_spec = old_partition_spec;
	}

	public List<Columns> getNew_partition_spec() {
		return new_partition_spec;
	}

	public void setNew_partition_spec(List<Columns> new_partition_spec) {
		this.new_partition_spec = new_partition_spec;
	}

	public String getDatabase_name() {
		return database_name;
	}

	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}

	public boolean isIn_or_from() {
		return in_or_from;
	}

	public void setIn_or_from(boolean in_or_from) {
		this.in_or_from = in_or_from;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public String getTable_or_view_name() {
		return table_or_view_name;
	}

	public void setTable_or_view_name(String table_or_view_name) {
		this.table_or_view_name = table_or_view_name;
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

	public String getREGEX() {
		return REGEX;
	}

	public void setREGEX(String rEGEX) {
		REGEX = rEGEX;
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

	public ResultSet getRes() {
		return res;
	}

	public void setRes(ResultSet res) {
		this.res = res;
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

}
