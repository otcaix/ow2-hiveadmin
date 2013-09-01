/**  
* @Project: javaHiveAdimin
* @Title: HistoryRecordForProcess.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 17, 2013 3:21:55 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @ClassName HistoryRecordForProcess
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 17, 2013 3:21:55 PM
 */
@Component(value="historyRecordForProcess")
@Scope("prototype")
public class HistoryRecordForProcess extends Thread{
	private UserHistoryLog userHistoryLog;
	private Logger log = Logger.getLogger(HistoryRecordForProcess.class);
	private HistoryRecord historyRecord;
	private Process process;
	
	public HistoryRecordForProcess(){}
	
	public HistoryRecordForProcess(HistoryRecord record,Process p){
		this.historyRecord = record;
		this.process = p;
	}
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}
	@Resource
	public void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		log.warn("set setUserHistoryLog for HistoryRecordForProcess");
		this.userHistoryLog = userHistoryLog;
	}

	public HistoryRecord getHistoryRecord() {
		return historyRecord;
	}

	public void setHistoryRecord(HistoryRecord historyRecord) {
		this.historyRecord = historyRecord;
	}

	@Override
	public void run() {
		log.warn("record thred...........run");
		try {
			log.warn("record thred...........<<wait for:");
			process.waitFor();
			log.warn("record thred...........>>wait for");
		} catch (InterruptedException e) {
			log.warn("failed to excecute cmd. exception message:"+e.getMessage());
		}
		int exitvalue=-1;
		try{
		 exitvalue = process.exitValue();
		}catch(Exception e){
			log.error("failed to do record. process is not rightly exit.");
		}
		if(exitvalue==0){
			log.warn("record thred...........exitvalue:0");
			if(historyRecord==null){
				log.warn("=============historyRecord is null");
			}
			historyRecord.setOp_res(true);
		}else{
			log.warn("record thred...........exitvalue: !0");
			historyRecord.setOp_res(false);
		}
		log.warn("record thred...........add history");
		log.warn("check userHistoryLog==null?"+(userHistoryLog==null));
		this.userHistoryLog.addHistotyRecord(historyRecord); 
	}
}
