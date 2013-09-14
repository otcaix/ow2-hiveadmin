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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.nullCondition_return;
import org.apache.hadoop.mapred.JobHistory.HistoryCleaner;
import org.apache.log4j.Logger;
import org.apache.naming.java.javaURLContextFactory;
import org.apache.struts2.ServletActionContext;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext;
import org.hiveadmin.hdfs.utils.HDFSUtils;
import org.hiveadmin.hive.beans.FileStatusBean;
import org.hiveadmin.hive.beans.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.ws.api.pipe.NextAction;

/**
 * @ClassName HDFSOperationAction
 * this action is intend to HDFS operations
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 10:20:35 AM
 */
@Component
@Scope("session")
public class HDFSOperationAction extends ActionSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * hdfsUtils to do relative operations
	 */
	HDFSUtils hdfsUtils;
	
	/**
	 * dirName
	 */
	private String dirName;
	/**
	 * errorMsg to return to the views
	 */
	private String errorMsg;
	/**
	 * mark the user if he is a admin user
	 */
	private boolean isroot;
	/**
	 * fileName
	 */
	private String fileName;
	/**
	 * remotepath
	 */
	private String remotepath;
	/**
	 * remoteFileName
	 */
	/**
	 * remoteFileName
	 */
	private String remoteFileName;
	/**
	 * localpath
	 */
	private String localpath;
	
	/**
	 * newName
	 */
	private String newName;
	/**
	 * oldName
	 */
	private String oldName;
	/**
	 * listfilepath
	 */
	private String listfilepath;
	/**
	 * listfilepathparent
	 */
	private String listfilepathparent;
	
	/** 
	* getListfilepathparent 
	*  
	* @param listfilepath
	* @return the parent file the path to list
	* @throws 
	*/
	public String getListfilepathparent() {
		this.listfilepathparent = (new File(this.listfilepath)).getParent();
		if(this.listfilepath==null)
			this.listfilepath="/";
		return this.listfilepathparent;
	}
	
	/** 
	* @Title: setListfilepathparent 
	* @Description: TODO
	* @param listfilepathparent
	*/
	public void setListfilepathparent(String listfilepathparent) {
		this.listfilepathparent = listfilepathparent;
	}
	/**
	 * title
	 */
	private String title;
	/**
	 * upload
	 */
	private File upload;
	/**
	 * uploadContentType
	 */
	private String uploadContentType;
	/**
	 * uploadFileName
	 */
	private String uploadFileName;
	
	/**
	 * log
	 */
	Logger log = Logger.getLogger(HDFSOperationAction.class);
	/**
	 * fileStatusArray
	 */
	private List<FileStatusBean> fileStatusArray;
	/**
	 * savePath
	 */
	private String savePath;
	/**
	 * listfilepathfreg
	 */
	private List<File> listfilepathfreg;

	public List<File> getListfilepathfreg() {
		return listfilepathfreg;
	}
	
	public void setListfilepathfreg(List<File> listfilepathfreg) {
		this.listfilepathfreg = listfilepathfreg;
	}
	
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

	
	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
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
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
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
	public String getRemoteFileName() {
		return remoteFileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public List<FileStatusBean> getFileStatusArray() {
		return fileStatusArray;
	}
	public void setFileStatusArray(List<FileStatusBean> fileStatusArray) {
		this.fileStatusArray = fileStatusArray;
	}
	public String setSavePath() throws Exception{
		return ServletActionContext.getServletContext().getRealPath("WEB-INF/"+savePath);
	}
	
	
	/** 
	* judgeRoot 
	* <p>judge if the current user is an administrator<br>
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
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
	
	/** 
	* mkdirs 
	* <p>create a derector<br>
	*  
	*/
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
	/** 
	* deletedirs 
	* <p>delete the given dir<br>
	*/
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
	
	/** 
	* mkfile 
	* <p>create a dir<br>
	* 
	*/
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
	
	/** 
	* deletefile 
	* <p>delete the givne file<br>
	*/
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
	/** 
	* uploadfile 
	* <p>to upload a file<br>
	* 
	*/
	public String uploadfile(){
		judgeRoot();
		String tempFilePath = null;
		log.info("=====before upload");
		try {
			//tempFilePath = getSavePath()+"/"+getUploadFileName();
			String  tempUploadPath = ServletActionContext.getServletContext().getRealPath("/")+"/upload/";
			File file = new File(tempUploadPath);
			if(!file.exists()){
				file.mkdirs();
			}
			tempFilePath = tempUploadPath +"/"+getUploadFileName();
			FileOutputStream fos = new FileOutputStream(tempFilePath);
			FileInputStream fis = new FileInputStream(getUpload());
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer, 0, len);
			}
			log.info("upload, tempFilePath:"+tempFilePath);
			log.info("remotefilepath:"+remoteFileName);
			hdfsUtils.upload(hdfsUtils.getFileSystem(), tempFilePath, remotepath,true,isroot);
		} catch (Exception e) {
		
			log.error("upload file failed. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "upload file failed. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("upload file success. [tempFilePath:"+tempFilePath+"][remotepath"+remotepath+"]");
		return SUCCESS;
	}
	
	/** 
	* getDownloadfile 
	* <p>to doload a file<br>
	*/
	public InputStream getDownloadfile(){
		judgeRoot();
		String tempFilePath = null;
		try {
			String remoteFileNameString = new File(remotepath).getName();
			tempFilePath = ServletActionContext.getServletContext().getRealPath("/")+"/download/"+remoteFileNameString;
			setRemoteFileName(remoteFileNameString);
			
			log.info("remote file name:"+remoteFileNameString);
			hdfsUtils.download(hdfsUtils.getFileSystem(), tempFilePath, remotepath, isroot);
			log.info("download file success. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"]");
			return new FileInputStream(tempFilePath);
		} catch (Exception e) {
			log.info("download file failed. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]");
			errorMsg = "download file failed. [tempFilePath:"+tempFilePath+"][rmotepath:"+remotepath+"][exception msg:"+remotepath+"]";
			return null;
		}
	}

	/** 
	* renamefile 
	* <p>to rename a file<br>
	*/
	public String renamefile(){
		judgeRoot();
		try {
			hdfsUtils.rename(hdfsUtils.getFileSystem(), oldName, newName, isroot);
		} catch (Exception e) {
			log.info("rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "rename file failed. [oldNae:"+oldName+"][newName:"+newName+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("rename file success. [oldName:"+oldName+"][newName:"+newName+"]");
		return SUCCESS;
	}
	
	/** 
	* listFileStatus 
	* <p> to get the list of the file propertises<br>
	*/
	public String listFileStatus(){
		judgeRoot();
		try {
			this.listfilepathfreg = getFilePathFreg(listfilepath);
			for(File path:this.listfilepathfreg){
				System.out.println(path.getName());
				System.out.println(path.getParent());
			}
			log.info("get file list status.[listfilepath:]"+listfilepath);
			fileStatusArray = hdfsUtils.listFileStatus(hdfsUtils.getFileSystem(), listfilepath, isroot);
			System.out.println("----list filestatus");
			
			Iterator<FileStatusBean> it = fileStatusArray.iterator();
			while(it.hasNext()){
				FileStatusBean next = it.next();
				
				System.out.println(next.toString());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			errorMsg = e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/** 
	* getFilePathFreg 
	* <p>to get the list of file fregments.<br> 
	*/
	private List<File> getFilePathFreg(String path) {
		//judgeRoot();
		isroot = true;
		if(path==null || path.trim().length()==0){
			path="/";
			System.out.println("input param is null.set to root:/");
		}
		if(!isroot){
			path = hdfsUtils.getFileSystem().getWorkingDirectory().toString()+"/"+path;
			System.out.println("not root.reset:"+path);
		}
		
		System.out.println("caculated path:"+path);
		File filePath = new File(path);
		List<File> pathlist = new ArrayList<File>();

		while(filePath.getParent()!=null){
			System.out.println("add filepath:"+filePath.getPath());
			pathlist.add(0,filePath);
			filePath=new File(filePath.getParent());
		}
		System.out.println("<<<<<<<<,,hdfsaction:listfreg");
		Iterator<File> it = pathlist.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getName());
		}
		System.out.println(">>>>>>hdfs action down");
		return pathlist;
	}
	public static void main(String[] args){
		new HDFSOperationAction().getFilePathFreg("/home");
	}

}
