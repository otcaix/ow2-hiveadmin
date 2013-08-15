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

import com.mysql.jdbc.log.Log;

/**
 * @ClassName RealTimeReadFile
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 13, 2013 5:42:13 PM
 */
public class RealTimeReadFile {
	
	private Logger log = Logger.getLogger(RealTimeReadFile.class);
	
	public String read(File file, long lastReadSize,int maxline){
		if(maxline<0){
			return null;
		}
		
		RandomAccessFile rFile = null; 
		
		try {
			rFile = new RandomAccessFile(file, "r");
			System.out.println("file size:"+rFile.length());
			System.out.println("find lastRead:"+lastReadSize);
			rFile.seek(lastReadSize);
		} catch (IOException e1) {
			System.out.println("get exception.");
			log.warn("file not exist or can not be accessed. [filepath:"+file.getPath()+"]");
			return null;
		}
		String tmp = "";
		String retString = "";
		int i = 0 ;
		try {
			while(i<maxline && (tmp = rFile.readLine())!=null){
				System.out.println("cur read String:"+retString);
				System.out.println("new readline:"+tmp);
				retString +="\n"+ tmp;
				i = i+1;
			}
			rFile.close();
			log.debug("return status lines:"+retString);
			System.out.println("return string:"+retString);
			System.out.println("return string size:"+retString.length());
			return retString;
		} catch (IOException e) {
			log.warn("got exception while reading status file. [filepath:"+file.getPath()+"][exception:"+e.getMessage()+"]");
			return null;
		}
			
	}
}
