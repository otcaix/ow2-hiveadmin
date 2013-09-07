/**  
* @Project: javaHiveAdimin
* @Title: RealTimeReadFile.java
* @Package org.hiveadmin.hive.service.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 13, 2013 5:42:13 PM
* @version V1.0  
*/
package org.hiveadmin.hive.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.RealTimeReadFileBean;

import com.mysql.jdbc.log.Log;

/**
 * RealTimeReadFile
 * a util to read growing files
 * @author wangjie wangjie370124@163.com
 * @date Aug 13, 2013 5:42:13 PM
 */
public class RealTimeReadFile {
	
	private Logger log = Logger.getLogger(RealTimeReadFile.class);
	
	/** 
	* read 
	* <p>to read a growing file<br>
	* @param file  the file path to read
	* @param maxline maxline to read at one time
	* @return result content
	* 
	*/
	public String read(File file, long lastReadSize,int maxline){
		if(maxline<0){
			return null;
		}
		
		RandomAccessFile rFile = null; 
		
		try {
			rFile = new RandomAccessFile(file, "r");
			log.debug("file size:"+rFile.length());
			log.debug("find lastRead:"+lastReadSize);
			rFile.seek(lastReadSize);
		} catch (IOException e1) {
			
			log.warn("file not exist or can not be accessed. [filepath:"+file.getPath()+"]");
			return null;
		}
		String tmp = "";
		String retString = "";
		int i = 0 ;
		try {
			while(i<maxline && (tmp = rFile.readLine())!=null){
				log.debug("cur read String:"+retString);
				log.debug("new readline:"+tmp);
				retString +="\n"+ tmp;
				i = i+1;
			}
			rFile.close();
			log.debug("return status lines:"+retString);
			log.debug("return string:"+retString);
			log.debug("return string size:"+retString.length());
			return retString;
		} catch (IOException e) {
			log.warn("got exception while reading status file. [filepath:"+file.getPath()+"][exception:"+e.getMessage()+"]");
			return null;
		}
			
	}
	/** 
	* readLines 
	* <p>to read a growing file<br>
	* @param file the path to read
	* @param bean record the infomation about the reading
	* @param maxline the maxlien to read at one time
	* 
	*/
	public void readLines(File file, RealTimeReadFileBean bean,int maxline){
		bean.getLines().clear();
		if(maxline<0){
			log.info("read nothing");
			return;
		}
		
		RandomAccessFile rFile = null; 
		
		try {
			rFile = new RandomAccessFile(file, "r");
			rFile.seek(bean.getLastread());
		} catch (IOException e1) {
			log.warn("read nothing:file not exist or can not be accessed. [filepath:"+file.getPath()+"]");
			return;
		}
		
		int i = 0 ;
		String tmp="";
		try {
			tmp = rFile.readLine();
			if(tmp==null){
				log.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&EOF");
			}
		} catch (IOException e) {
			log.warn("failed to readline from file. [exception message"+e.getMessage()+"]");
			return;
		}
		while(i<maxline && tmp!=null){
			bean.getLines().add(tmp);
			int lastread = bean.getLastread();
			System.out.println("****addlastread:");
			bean.setLastread(lastread+tmp.length()+1);
			i = i+1;
			try {
				tmp = rFile.readLine();
			} catch (IOException e) {
				log.warn("failed to readline from file. [exception message"+e.getMessage()+"]");
				return;
			}
		}
		try {
			rFile.close();
		} catch (IOException e) {
			log.warn("failed to close file. [exception message"+e.getMessage()+"]");
		}
					
			//log.warn("got exception while reading status file. [filepath:"+file.getPath()+"][exception:"+e.getMessage()+"]");
			
		
			
	}
}
