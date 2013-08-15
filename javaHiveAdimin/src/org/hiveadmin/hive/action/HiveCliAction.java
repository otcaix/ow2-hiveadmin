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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hive.service.HiveCliService;
import org.hiveadmin.hive.service.impl.HiveCliServiceImpl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;

/**
 * @ClassName HiveCliAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 13, 2013 6:05:43 PM
 */

@Component
@Scope("session")
public class HiveCliAction extends ActionSupport{
	private Logger log = Logger.getLogger(HiveCliAction.class);
	private HiveCliService hiveCliServiceImpl;
	private String database;
	private String sql;
	private String fingerPrint;
	private String errorMsg;
	private long lastReadSize;
	private String readStatus;

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
	public String createFingerPrint(){
		String user = (String)ServletActionContext.getContext().getSession().get("user");
		String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return user+"_"+ts;
	}
	public String hiveCliQuery(){
		System.out.println("hivecliQuery=====");
		this.fingerPrint = createFingerPrint();
		
		try {
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
	}
	public String getResult(){
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			hiveCliServiceImpl.setContextPath(ServletActionContext.getServletContext().getRealPath("/"));
			String result = hiveCliServiceImpl.getResult(this.fingerPrint);
			out.println(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			errorMsg = "failed to get the result file of hive cli quey.exception msg:"+e.getMessage();
			log.error(errorMsg);
		}
		return null;
	}
	
}
