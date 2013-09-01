package org.hiveadmin.hive.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.Columns;
import org.hiveadmin.hive.service.impl.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("session")
public class HIveViewAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1379532893454739772L;

	Logger log = Logger.getLogger(HIveViewAction.class);

	private HiveViewServiceImpl server = new HiveViewServiceImpl();

	private boolean if_not_exists=false;
	private String view_name;
	private List<Columns>  columns;
	private String comment;
	private List<Columns>  tblproperties_spec;
	private String select_sentance;
	private List<Columns>  table_properties;

	public String createView() {
		boolean result = false;
		if (view_name == null || view_name.equals("")) {
			log.error("View name is null!");
			result = true;
		}

		if (select_sentance == null || select_sentance.equals("")) {
			log.error("Select_sentance is null!");
			result = true;
		}
		if (result) {
			return ERROR;
		}
		try {
			server.createView(if_not_exists, view_name,columns,comment,tblproperties_spec,select_sentance);
			log.info("View has been  created");
			return SUCCESS;
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String dropView() {
		if (view_name == null || view_name.equals("")) {
			log.error("View name is null!");
			return ERROR;
		}
		try {
			server.dropView(if_not_exists, view_name);
			log.info("View has been  dropped");
			return SUCCESS;
		} catch (Exception e) {
		
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public String alterViewProperties() {
		boolean result = false;
		if (view_name == null || view_name.equals("")) {
			log.error("View name is null!");
			result = true;
		}
		if (table_properties.isEmpty()) {
			log.error("Tblproperties_spec is null!");
			result = true;
		}
		if (result) {
			return ERROR;
		}
		try {
			server.alterViewProperties(view_name,table_properties);
			log.info("View has been  altered");
			return SUCCESS;
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}

	}

	public String alterViewAsSelect() {
		boolean result = false;
		if (view_name == null || view_name.equals("")) {
			log.error("View name is null!");
			result = true;
		}
		if (select_sentance == null || select_sentance.equals("")) {
			log.error("Select_statement is null!");
			result = true;
		}
		if (result) {
			return ERROR;
		}
		try {
			server.alterViewAsSelect(view_name, select_sentance);
			log.info("View has been  altered");
			return SUCCESS;
		} catch (Exception e) {
		
			e.printStackTrace();
			log.error(e.toString());
			return ERROR;
		}
	}

	public HiveViewServiceImpl getServer() {
		return server;
	}

	public void setServer(HiveViewServiceImpl server) {
		this.server = server;
	}

	public boolean isIf_not_exists() {
		return if_not_exists;
	}

	public void setIf_not_exists(boolean if_not_exists) {
		this.if_not_exists = if_not_exists;
	}

	public String getView_name() {
		return view_name;
	}

	public void setView_name(String view_name) {
		this.view_name = view_name;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<Columns> getColumns() {
		return columns;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Columns> getTblproperties_spec() {
		return tblproperties_spec;
	}

	public void setTblproperties_spec(List<Columns> tblproperties_spec) {
		this.tblproperties_spec = tblproperties_spec;
	}

	public String getSelect_sentance() {
		return select_sentance;
	}

	public void setSelect_sentance(String select_sentance) {
		this.select_sentance = select_sentance;
	}

	public List<Columns> getTable_properties() {
		return table_properties;
	}

	public void setTable_properties(List<Columns> table_properties) {
		this.table_properties = table_properties;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
