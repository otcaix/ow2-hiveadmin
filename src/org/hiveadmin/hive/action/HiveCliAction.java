/**  
* @Project: javaHiveAdimin
* @Title: HiveCliAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 13, 2013 6:05:43 PM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.booleanValue_return;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.beans.RealTimeReadFileBean;
import org.hiveadmin.hive.beans.User;
import org.hiveadmin.hive.service.HiveCliService;
import org.hiveadmin.hive.service.impl.HiveCliServiceImpl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;

/**
 * HiveCliAction
 * do hive operations by the local hive client
 * @author wangjie wangjie370124@163.com
 * @date Aug 13, 2013 6:05:43 PM
 */

@Component
@Scope("session")
public class HiveCliAction extends ActionSupport{
	/**
	 * log
	 */
	private Logger log = Logger.getLogger(HiveCliAction.class);
	/**
	 * hiveCliServiceImpl
	 */
	private HiveCliService hiveCliServiceImpl;
	/**
	 * database
	 */
	private String database;
	/**
	 * sql
	 */
	private String sql;
	/**
	 * fingerPrint
	 */
	private String fingerPrint;
	/**
	 * errorMsg
	 */
	private String errorMsg;
	/**
	 * lastReadSize
	 */
	private long lastReadSize;
	/**
	 * newstart
	 */
	private String newstart;
	/**
	 * readStatus
	 */
	private String readStatus;
	/**
	 * realTimeReadFileBean
	 */
	private RealTimeReadFileBean realTimeReadFileBean;
	/**
	 * resultFilecontent
	 */
	private String resultFilecontent;
	/**
	 * isroot
	 */
	private boolean isroot;
	
	public String getResultFilecontent() {
		return resultFilecontent;
	}
	public void setResultFilecontent(String resultFilecontent) {
		this.resultFilecontent = resultFilecontent;
	}
	public String getNewstart() {
		return newstart;
	}
	public void setNewstart(String newstart) {
		this.newstart = newstart;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public RealTimeReadFileBean getRealTimeReadFileBean() {
		return realTimeReadFileBean;
	}
	@Resource
	public void setRealTimeReadFileBean(RealTimeReadFileBean realTimeReadFileBean) {
		this.realTimeReadFileBean = realTimeReadFileBean;
	}
	public long getLastReadSize() {
		return lastReadSize;
	}
	public void setLastReadSize(long lastReadSize) {
		this.lastReadSize = lastReadSize;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public HiveCliService getHiveCliServiceImpl() {
		return hiveCliServiceImpl;
	}
	@Resource
	public void setHiveCliServiceImpl(HiveCliService hiveCliServiceImpl) {
		this.hiveCliServiceImpl = hiveCliServiceImpl;
	}
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	/** 
	* createFingerPrint 
	* <p>create a fingerprint by the current user name an the typestamp<br>
	* 
	*/
	public String createFingerPrint(){
		String user = (String)ServletActionContext.getContext().getSession().get("user");
		String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return user+"_"+ts;
	}
	
	
	/** 
	* hiveCliQuery 
	* <p>to do the hive operations by the local hive client<br>
	* 
	*/
	public String hiveCliQuery(){
		System.out.println("hivecliQuery=====");
		this.fingerPrint = createFingerPrint();
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_user_name((String)ServletActionContext.getContext().getSession().get("user"));
		historyRecord.setOp_desc("execute sql by hive cli.");
		historyRecord.setOp_sql("use database "+database+";"+sql);
		
		try {
			hiveCliServiceImpl.setHistoryRecord(historyRecord);
			hiveCliServiceImpl.setContextPath(ServletActionContext.getServletContext().getRealPath("/"));
			hiveCliServiceImpl.cliQuery(database, sql, this.fingerPrint);
			
		} catch (IOException e) {
			this.errorMsg = "failed to do hive cli query.[exception:"+e.getMessage()+"]";
			log.error(this.errorMsg);
			return ERROR;
		}
		
		
		log.info("success to do hive cli query. sql:"+sql);
		return null;
	}
/*
	public String getQueryStatus() throws IOException{
		System.out.println("getQueryStatus======");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		hiveCliServiceImpl.setContextPath(ServletActionContext.getServletContext().getRealPath("/"));
		this.readStatus = hiveCliServiceImpl.getQueryStatus(fingerPrint,lastReadSize,30);
		out.print(this.readStatus);
		out.flush();
		out.close();	
		return null;
	}*/
	
	
	
	/** 
	* getQueryStatus 
	* <p>return the String of the query status. the status is read from the responding log file. and can read 30 lines once at most<br> 
	*/
	public String getQueryStatus() throws IOException{
		System.out.println("getQueryStatus======");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
				
		log.debug("[[[[[[[[[[");
		if("true".equals(newstart)){
			log.debug("true. start form 0;");
			this.realTimeReadFileBean.setLastread(0);	
		}else{
			log.debug("false. start from"+this.realTimeReadFileBean.getLastread());
		}
		hiveCliServiceImpl.setContextPath(ServletActionContext.getServletContext().getRealPath("/"));
		hiveCliServiceImpl.getQueryStatusBean(fingerPrint, this.realTimeReadFileBean, lastReadSize, 30);
		
		log.debug("stoped:"+this.realTimeReadFileBean.getLastread());
		Iterator<String> it = realTimeReadFileBean.getLines().iterator();
		while(it.hasNext()){
			String ress= it.next();
			System.out.println(ress);
			out.print(ress);
			out.print("<br>");
		}
		
		log.debug(realTimeReadFileBean.getLines().toString());
		out.flush();
		out.close();	
		return null;
	}
	
	/** 
	* getResult 
	* <p>get the result file content reading from the log file<br>
	*/
	public String getResult(){
		log.warn("********getResult:");
		log.warn("fingerprint:"+this.fingerPrint);
		try {			
			hiveCliServiceImpl.setContextPath(ServletActionContext.getServletContext().getRealPath("/"));
			resultFilecontent = hiveCliServiceImpl.getResult(this.fingerPrint);
			log.warn("result string:"+resultFilecontent);
		} catch (Exception e) {
			errorMsg = "failed to get the result file of hive cli quey. exception msg:"+e.getMessage();
			log.error(errorMsg);
			return ERROR;
		}
		//log.warn("<<<<<<<<<<<<get status:");
		//this.resultFilecontent="ok";
		return SUCCESS;
	}
	/** 
	* getDownLoadResultFile 
	* <p>return the result file resource<br>
	*/
	public InputStream getDownLoadResultFile(){
		log.info("currnt downloading fingerPrint is:"+fingerPrint);
		try {
			return hiveCliServiceImpl.getResultFileStream(this.fingerPrint);
		} catch (Exception e) {
			errorMsg = "download file failed.";
			return null;
		}
	}
	
}
