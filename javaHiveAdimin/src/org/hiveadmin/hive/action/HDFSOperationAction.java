/**  
* @Project: javaHiveAdimin
* @Title: HDFSOperationAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 10:20:35 AM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileStatus;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hdfs.utils.HDFSUtils;
import org.hiveadmin.hive.beans.User;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName HDFSOperationAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 10:20:35 AM
 */
@Component
public class HDFSOperationAction extends ActionSupport {
	
	HDFSUtils hdfsUtils;
	private String dirName;
	private String errorMsg;
	private boolean isroot;
	private String fileName;
	private String remotepath;
	private String localpath;
	
	private String newName;
	private String oldName;
	private String listfilepath;
	
	Logger log = Logger.getLogger(HDFSOperationAction.class);
	private FileStatus[] fileStatusArray;
		
	public String getListfilepath() {
		return listfilepath;
	}
	public void setListfilepath(String listfilepath) {
		this.listfilepath = listfilepath;
	}

	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	
	public String getRemotepath() {
		return remotepath;
	}
	public void setRemotepath(String remotepath) {
		this.remotepath = remotepath;
	}
	public String getLocalpath() {
		return localpath;
	}
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HDFSUtils getHdfsUtils() {
		return hdfsUtils;
	}
	@Resource(name="hdfsUtils")
	public void setHdfsUtils(HDFSUtils hdfsUtils) {
		this.hdfsUtils = hdfsUtils;
	}
	
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public boolean isIsroot() {
		return isroot;
	}
	public void setIsroot(boolean isroot) {
		this.isroot = isroot;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void judgeRoot(){
		User curUser = (User)ServletActionContext.getContext().getSession().get("userInfo");
		if(curUser.getUsergroup().equals("admin")){
			log.info("current user is admin.");
			isroot = true;
		}else {
			log.info("current user is not admin");
			isroot=false;
		}
	}
	
	public String mkdirs(){
		try {
			judgeRoot();
			
			hdfsUtils.mkdirs(hdfsUtils.getFileSystem(), dirName, isroot);
			log.info("makedirs success. [dirName:"+dirName+"]");
		} catch (Exception e) {
			log.error("mkdirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "mkdirs failed. [dirName:"+dirName+"][excpetion msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("mkdirs success. [dirName:"+dirName+"]");
		return SUCCESS;
	}
	public String deletedirs(){
		judgeRoot();
		try {
			hdfsUtils.rmdirs(hdfsUtils.getFileSystem(), dirName, isroot);
		} catch (Exception e) {
			log.error("rm dirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "rm dirs failed. [dirName:"+dirName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("rm dirs success. [dirName:"+dirName+"]");
		return SUCCESS;
	}
	
	public String mkfile(){
		judgeRoot();
		try {
			hdfsUtils.createNewFile(hdfsUtils.getFileSystem(), fileName, isroot);
		} catch (Exception e) {
			log.error("mkfile failed. [fileName:"+fileName+"]["+e.getMessage()+"]");
			errorMsg ="mkfile failed. [fileName:"+fileName+"]["+e.getMessage()+"]"; 
			return ERROR;
		}
		log.info("mkfile success. [fileName:"+fileName+"]");
		return SUCCESS;
	}
	public String deletefile(){
		judgeRoot();
		try {
			hdfsUtils.deleteFile(hdfsUtils.getFileSystem(), fileName, isroot);
		} catch (Exception e) {
			log.error("delete file failed. [fileName:"+fileName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "delete file failed. [fileName:"+fileName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("delete file success. [fileName:"+fileName+"]");
		return SUCCESS;
	}
	public String uploadfile(){
		judgeRoot();
		try {
			hdfsUtils.upload(hdfsUtils.getFileSystem(), localpath, remotepath, isroot);
		} catch (Exception e) {
		
			log.error("upload file failed. [localpath:"+localpath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "upload file failed. [localpath:"+localpath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("upload file success. [localpath:"+localpath+"][remotepath"+remotepath+"]");
		return SUCCESS;
	}
	
	public String downloadfile(){
		judgeRoot();
		try {
			hdfsUtils.download(hdfsUtils.getFileSystem(), localpath, remotepath, isroot);
		} catch (Exception e) {
			log.info("download file failed. [localpath:"+localpath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]");
			errorMsg = "download file failed. [localpath:"+localpath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]";
			return ERROR;
		}
		log.info("download file success. [localpath:"+localpath+"][rmotepath:"+remotepath+"]");
		return SUCCESS;
	}
	public String renamefile(){
		judgeRoot();
		try {
			hdfsUtils.rename(hdfsUtils.getFileSystem(), oldName, newName, isroot);
		} catch (Exception e) {
			log.info("rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("rename file success. [oldNae:"+oldName+"][newName:"+newName+"]");
		return SUCCESS;
	}
	
	public String listFileStatus(){
		judgeRoot();
		try {
			fileStatusArray = hdfsUtils.listFileStatus(hdfsUtils.getFileSystem(), listfilepath, isroot);
		} catch (Exception e) {
			log.error(e.getMessage());
			errorMsg = e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}

}
