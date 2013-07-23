/**  
* @Project: javaHiveAdimin
* @Title: DatabaseAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 4:31:06 PM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.service.HiveDatabaseService;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName DatabaseAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 4:31:06 PM
 */
@Component
public class DatabaseAction extends ActionSupport{
	
	private String databaseName;
	private String description;
	private String errorMsg;
	private String type;
	private String hdfspath;
	private Map<String, String> dbproperties;
	
	private HiveDatabaseService hiveDatabaseService;
	private Logger log = Logger.getLogger(DatabaseAction.class);
	private List<String> databaseList;
	
	private String result;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<String> getDatabaseList () {
		return databaseList;
	}
	public void setDatabaseList(List<String> databaseList) {
		this.databaseList = databaseList;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHdfspath() {
		return hdfspath;
	}
	public void setHdfspath(String hdfspath) {
		this.hdfspath = hdfspath;
	}
	public Map<String, String> getDbproperties() {
		return dbproperties;
	}
	public void setDbproperties(Map<String, String> dbproperties) {
		this.dbproperties = dbproperties;
	}

	public HiveDatabaseService getHiveDatabaseService() {
		return hiveDatabaseService;
	}
	@Resource(name="hiveDatabaseServiceImpl")
	public void setHiveDatabaseService(HiveDatabaseService hiveDatabaseService) {
		this.hiveDatabaseService = hiveDatabaseService;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String create(){
		try {
			HistoryRecord historyRecord = new HistoryRecord();
			historyRecord.setOp_user_name((String) ServletActionContext.getContext().getSession().get("user"));
			historyRecord.setOp_desc("create database. [database:"+databaseName+"]");
			hiveDatabaseService.setHistoryRecord(historyRecord);
			hiveDatabaseService.createDatebase(databaseName, description, hdfspath, dbproperties);
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			log .info("create database failed.");
			return ERROR;
		}
		return SUCCESS;
	}
	public String getHiveDatabaseList(){
		/*try {
			HistoryRecord historyRecord = new HistoryRecord();
			historyRecord.setOp_user_name((String) ServletActionContext.getContext().getSession().get("user"));
			historyRecord.setOp_desc("get databases list.");
			hiveDatabaseService.setHistoryRecord(historyRecord);
			this.databaseList = hiveDatabaseService.listDatabase();
			
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			return ERROR;
		}*/
		this.databaseList=new ArrayList<String>();
		this.databaseList.add("Default");
		this.databaseList.add("yun");
		this.databaseList.add("ss");
		return SUCCESS;
	}
	public String deleteDatabase(){
		try {
			HistoryRecord record = new HistoryRecord();
			record.setOp_user_name((String) ServletActionContext.getContext().getSession().get("user"));
			record.setOp_desc("detele database. [database:"+databaseName+"]");
			hiveDatabaseService.setHistoryRecord(record);
			hiveDatabaseService.deleteDatebase(databaseName, type);
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}

}
