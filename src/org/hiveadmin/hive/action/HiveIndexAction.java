package org.hiveadmin.hive.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.service.impl.HiveIndexServiceImpl;
import org.hiveadmin.hive.service.impl.HiveViewServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("session")
public class HiveIndexAction extends ActionSupport {
	private HiveIndexServiceImpl server = new HiveIndexServiceImpl();
	private Logger log = Logger.getLogger(this.getClass());
	private String index_name = null;
	private String base_table_name = null;
	private List<String> column_spec;
	private String index_type = null;
	private boolean with_deferred_rebuild = false;
	private List<Columns>  properties_spec;
	private String index_table_name = null;
	private String row_format = null;
	private String stored_as = null;
	private String stored_by_with = null;
	private String hdfs_path = null;
	private List<Columns>  tblproperties;
	private String index_comment = null;
	
	private boolean if_exists=false;
	private String drop_index_name;
	private String drop_table_name;

	public String createIndex() {
		boolean result = false;
		if (index_name == null || index_name.equals("")) {
			log.error("Index name is null");
			result = false;

		}
		if (base_table_name == null || base_table_name.equals("")) {
			log.error("Table name is null");
			result = false;
		}

		if (index_type == null || index_type.equals("")) {
			log.error("Index name is null");
			result = false;

		}
		if (result) {
			return ERROR;
		}
		try {
			server.createIndex(index_name, base_table_name, column_spec,
					index_type, with_deferred_rebuild, properties_spec,
					index_table_name, row_format, stored_as, stored_by_with,
					hdfs_path, tblproperties, index_comment);
			log.info("Index has been created");
			return SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}
	
	public String dropIndex(){
		boolean result = false;
		if (drop_index_name == null || drop_index_name.equals("")) {
			log.error("Index name is null");
			result = false;

		}
		if (drop_table_name == null || drop_table_name.equals("")) {
			log.error("Table name is null");
			result = false;
		}

		if (result) {
			return ERROR;
		}
		try {
			server.dropIndex(if_exists, drop_index_name, drop_index_name);
			log.info("Index has been created");
			return SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
		
	}

	public HiveIndexServiceImpl getServer() {
		return server;
	}

	public void setServer(HiveIndexServiceImpl server) {
		this.server = server;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getIndex_name() {
		return index_name;
	}

	public void setIndex_name(String index_name) {
		this.index_name = index_name;
	}

	public String getBase_table_name() {
		return base_table_name;
	}

	public void setBase_table_name(String base_table_name) {
		this.base_table_name = base_table_name;
	}

	public List<String> getColumn_spec() {
		return column_spec;
	}

	public void setColumn_spec(List<String> column_spec) {
		this.column_spec = column_spec;
	}

	public String getIndex_type() {
		return index_type;
	}

	public void setIndex_type(String index_type) {
		this.index_type = index_type;
	}

	public boolean isWith_deferred_rebuild() {
		return with_deferred_rebuild;
	}

	public void setWith_deferred_rebuild(boolean with_deferred_rebuild) {
		this.with_deferred_rebuild = with_deferred_rebuild;
	}

	public List<Columns> getProperties_spec() {
		return properties_spec;
	}

	public void setProperties_spec(List<Columns> properties_spec) {
		this.properties_spec = properties_spec;
	}

	public String getIndex_table_name() {
		return index_table_name;
	}

	public void setIndex_table_name(String index_table_name) {
		this.index_table_name = index_table_name;
	}

	public String getRow_format() {
		return row_format;
	}

	public void setRow_format(String row_format) {
		this.row_format = row_format;
	}

	public String getStored_as() {
		return stored_as;
	}

	public void setStored_as(String stored_as) {
		this.stored_as = stored_as;
	}

	public String getStored_by_with() {
		return stored_by_with;
	}

	public void setStored_by_with(String stored_by_with) {
		this.stored_by_with = stored_by_with;
	}

	public String getHdfs_path() {
		return hdfs_path;
	}

	public void setHdfs_path(String hdfs_path) {
		this.hdfs_path = hdfs_path;
	}

	public List<Columns> getTblproperties() {
		return tblproperties;
	}

	public void setTblproperties(List<Columns> tblproperties) {
		this.tblproperties = tblproperties;
	}

	public String getIndex_comment() {
		return index_comment;
	}

	public void setIndex_comment(String index_comment) {
		this.index_comment = index_comment;
	}

	public boolean isIf_exists() {
		return if_exists;
	}

	public void setIf_exists(boolean if_exists) {
		this.if_exists = if_exists;
	}

	public String getDrop_index_name() {
		return drop_index_name;
	}

	public void setDrop_index_name(String drop_index_name) {
		this.drop_index_name = drop_index_name;
	}

	public String getDrop_table_name() {
		return drop_table_name;
	}

	public void setDrop_table_name(String drop_table_name) {
		this.drop_table_name = drop_table_name;
	}

	
	
	
	

}
