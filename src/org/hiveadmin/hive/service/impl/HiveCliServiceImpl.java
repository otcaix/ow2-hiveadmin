/**  
* @Project: javaHiveAdimin
* @Title: HiveCliServiceImpl.java
* @Package org.hiveadmin.hive.service.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 12, 2013 9:58:23 AM
* @version V1.0  
*/
package org.hiveadmin.hive.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.hadoop.hive.ql.parse.HiveParser.fileFormat_return;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.nullCondition_return;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.beans.HistoryRecordForProcess;
import org.hiveadmin.hive.beans.RealTimeReadFileBean;
import org.hiveadmin.hive.service.HiveCliService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Result;

/**
 * HiveCliServiceImpl
 * hivcli operations
 * @author wangjie wangjie370124@163.com
 * @date Aug 12, 2013 9:58:23 AM
 */
@Component
@Scope("prototype")
public class HiveCliServiceImpl implements HiveCliService {
	
	private Logger log = Logger.getLogger(HiveCliServiceImpl.class);
	
	private String contextPath;

	private InputStream statusStream;

	private HistoryRecord historyRecord;

	private HistoryRecordForProcess historyRecordForProcess;
	
	
	
	public HistoryRecordForProcess getHistoryRecordForProcess() {
		return historyRecordForProcess;
	}
	@Resource(name="historyRecordForProcess")
	public void setHistoryRecordForProcess(
			HistoryRecordForProcess historyRecordForProcess) {
		log.warn("set historyRecordForProcess for HiveCliServiceImpl");
		log.warn("historyRecordForProcess==null?"+(historyRecordForProcess==null));
		this.historyRecordForProcess = historyRecordForProcess;
	}
	public HistoryRecord getHistoryRecord() {
		return historyRecord;
	}
	public String getContextPath() {
		if(this.contextPath==null|| this.contextPath.trim().length()==0){
			this.contextPath = ".";
		}
		return contextPath;
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#setContextPath(java.lang.String)
	 */
	@Override
	public void setContextPath(String contextPath) {
		log.debug("set context path:"+contextPath);
		this.contextPath = contextPath;
	}

	public String getDataPath(){
		return this.getContextPath()+File.separator+"data"+File.separator;
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#cliQuery(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void cliQuery(String database, String sql, String fingerPrint) throws IOException{
		log.info("params[database:"+database+"][sql:"+sql+"][fingerPrint:"+fingerPrint+"]");
		String dataPath = getDataPath();
		File tempFile = new File(dataPath);
		System.out.println("===");
		System.out.println(tempFile.getPath());
		if(!tempFile.exists() && !tempFile.isDirectory()){
			System.out.println("file not exist");
			tempFile.mkdirs();
		}
		if(database == null || "".equals(database)){
			database = "default";
		}
		String resfileString = "";
		String statusfileString = "";
		String cmdString = "hive --database='"+database+"' -e '"+sql+"' 1>"+dataPath+fingerPrint+".result 2>"+dataPath+fingerPrint+".status";
		System.out.println("cmdstring===="+cmdString);
		List<String> cmds  = new ArrayList<String>();
		cmds.add("sh");
		cmds.add("-c");
		cmds.add(cmdString);
		ProcessBuilder pb = new ProcessBuilder(cmds);
		log.warn("hive cli process start");
		Process p = pb.start();
		historyRecord.setResultfile(fingerPrint);
		
		log.warn("recordThread start");
		this.historyRecordForProcess.setHistoryRecord(historyRecord);
		this.historyRecordForProcess.setProcess(p);
		
		this.historyRecordForProcess.start();
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#getResult(java.lang.String)
	 */
	@Override
	public String getResult(String fingerPrint) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(getDataPath()+fingerPrint+".result")));
			String result = "";
			String temp = "";
			try {
				while((temp=br.readLine())!=null){
					result+=temp+"<br>";
				}
				return result;
			} catch (IOException e) {
				log.error("failed to read the result file of hive cli query. file:"+getDataPath()+fingerPrint+".result"+" exception:"+e.getMessage());
				throw e;
			}
		} catch (FileNotFoundException e) {
			log.error("result file not exist. filepath:"+getDataPath()+fingerPrint+".result");
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#getQueryStatus(java.lang.String, long)
	 */
	@Override
	public String getQueryStatus(String fingerPrint, long lastReadSize,int maxline) {
		RealTimeReadFile rf = new RealTimeReadFile();
		String dataPathString = this.getDataPath();
		File statusFile = new File(dataPathString+fingerPrint+".status");
		return rf.read(statusFile, lastReadSize,maxline);
		
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#getQueryStatusBean(java.lang.String, org.hiveadmin.hive.beans.RealTimeReadFileBean, long, int)
	 */
	@Override
	public void getQueryStatusBean(String fingerPrint,RealTimeReadFileBean bean, long lastReadSize,int maxline) {
		RealTimeReadFile rf = new RealTimeReadFile();
		String dataPathString = this.getDataPath();
		File statusFile = new File(dataPathString+fingerPrint+".status");
		rf.readLines(statusFile, bean,maxline);		
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#setHistoryRecord(org.hiveadmin.hive.beans.HistoryRecord)
	 */
	@Override
	public void setHistoryRecord(HistoryRecord historyRecord) {
		this.historyRecord = historyRecord;
		
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveCliService#getResultFileStream()
	 */
	@Override
	public InputStream getResultFileStream(String fingerPrint) throws Exception {
		if(fingerPrint==null||fingerPrint.length()==0){
			throw new Exception("param fingerPrint is null");
		}
		return new FileInputStream(new File(getDataPath()+fingerPrint+".result"));
	}

	
}
