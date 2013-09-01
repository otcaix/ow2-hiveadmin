/**  
* @Project: javaHiveAdimin
* @Title: UserHistoryRecordAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 11:23:11 PM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.springframework.stereotype.Component;
import org.hiveadmin.hive.beans.HistoryRecord;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName UserHistoryRecordAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 11:23:11 PM
 */
@Component
public class UserHistoryRecordAction extends ActionSupport{
	private UserHistoryLog userHitoryLog = new UserHistoryLog();
	private List<HistoryRecord> historyRecordList;
	private String sortType;
	private String errorMsg;
	private Logger log = Logger.getLogger(UserHistoryRecordAction.class);
	
	public List<HistoryRecord> getHistoryRecordList() {
		return historyRecordList;
	}
	public void setHistoryRecordList(List<HistoryRecord> historyRecordList) {
		this.historyRecordList = historyRecordList;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public UserHistoryLog getUserHitoryLog() {
		return userHitoryLog;
	}
	@Resource
	public void setUserHitoryLog(UserHistoryLog userHitoryLog) {
		this.userHitoryLog = userHitoryLog;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getUserHistoryRecordList(){
		try{
			historyRecordList = userHitoryLog.getHistoryRecord((String) ServletActionContext.getContext().getSession().get("user"),sortType);
			if (historyRecordList==null){
				throw new Exception("get historyRecordlist error.");
			}
		}catch(Exception e){
			log.error("get history record list error. [exception msg:"+e.getMessage()+"]");
			this.errorMsg = e.getMessage();
			return ERROR;
		}
		log.info("get user history record list success");
		System.out.println(historyRecordList.size());
		return SUCCESS;
	}
}
