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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
	
	
	public List<String> getDatabaseList() {
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
			hiveDatabaseService.createDatebase(databaseName, description, hdfspath, dbproperties);
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			log .info("create database failed.");
			return ERROR;
		}
		return SUCCESS;
	}
	public String getHiveDatabaseList(){
		try {
			this.databaseList = hiveDatabaseService.listDatabase();
			
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			return ERROR;
		}
		
		return SUCCESS;
	}
	public String deleteDatebase(){
		try {
			hiveDatabaseService.deleteDatebase(databaseName, type);
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}

}
