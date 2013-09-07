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
 * HistoryRecordForProcess
 * do history record for special operations. such as the local hive cli query. It will log the operation util it is accomplished
 * @author wangjie wangjie370124@163.com
 * @date Aug 17, 2013 3:21:55 PM
 */
@Component(value="historyRecordForProcess")
@Scope("prototype")
public class HistoryRecordForProcess extends Thread{
	/**
	 * userHistoryLog
	 */
	private UserHistoryLog userHistoryLog;
	/**
	 * log
	 */
	private Logger log = Logger.getLogger(HistoryRecordForProcess.class);
	/**
	 * historyRecord
	 */
	private HistoryRecord historyRecord;
	/**
	 * process
	 */
	private Process process;
	
	/**
	 * Constructor
	 */
	public HistoryRecordForProcess(){}
	
	/**
	 * Constructor
	 */
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

	/* *
	 * open a thread to wait util the hive cli query is done. and then add the record;
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		log.debug("record thred...........run");
		try {
			log.debug("record thred...........<<wait for:");
			process.waitFor();
			log.debug("record thred...........>>wait for");
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
			log.debug("record thred...........exitvalue:0");
			if(historyRecord==null){
				log.debug("=============historyRecord is null");
			}
			historyRecord.setOp_res(true);
		}else{
			log.debug("record thred...........exitvalue: !0");
			historyRecord.setOp_res(false);
		}
		log.debug("record thred...........add history");
		log.debug("check userHistoryLog==null?"+(userHistoryLog==null));
		this.userHistoryLog.addHistotyRecord(historyRecord); 
	}
}
