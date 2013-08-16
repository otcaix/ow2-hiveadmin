/**  
* @Project: javaHiveAdimin
* @Title: FileStatusBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 15, 2013 7:20:13 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.NEW;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hdfs.utils.HDFSUtils;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.log.Log;

/**
 * @ClassName FileStatusBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 15, 2013 7:20:13 PM
 */
@Component
public class FileStatusBean {
	private String owner;
	private Path path;
	private String basePath;
	private long blockSize;
	private String group;
	private long len;
	private Date accessTime;
	private String modificationTime;
	private boolean isDir;
	private static HDFSUtils hdfsUtils;
	Logger log = Logger.getLogger(FileStatusBean.class);
	
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public HDFSUtils getHdfsUtils() {
		return hdfsUtils;
	}
	@Resource(name="hdfsUtils")
	public void setHdfsUtils(HDFSUtils hdfsUtils) {
		this.hdfsUtils = hdfsUtils;
	}

	public boolean getDir() throws IOException {
		return hdfsUtils.isDirectory(this.path);
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public long getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(long blockSize) {
		this.blockSize = blockSize;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(String modificationTime) {
		this.modificationTime = modificationTime;
	}
	public FileStatusBean(){}
	
	public FileStatusBean(FileStatus status) throws URISyntaxException{
		System.out.println("==========status");
		this.path = status.getPath();
		URI uri = null;
		try {
			uri = new URI(this.path.toString());
		} catch (URISyntaxException e) {
			log.error("not a valid path.");
			throw e;
		}
		this.basePath = uri.getPath();
		this.blockSize = status.getBlockSize();
		this.len = status.getLen();
		this.owner = status.getOwner();
		this.group = status.getGroup();
		this.accessTime = new Date(status.getAccessTime());
		this.modificationTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(status.getModificationTime()));
		System.out.println(this.path);
	}
	
}
