/**  
* @Project: javaHiveAdimin
* @Title: HDFSUtils.java
* @Package org.hiveadmin.hdfs.utils
* @Description: this util is intend to do hdfs operations.
* @author wangjie, wangjie12@otcaix.iscas.ac.cn
* @date Jul 16, 2013 1:12:36 PM
* @version V1.0  
*/
package org.hiveadmin.hdfs.utils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;	
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hive.ql.parse.HiveParser.indexComment_return;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.booleanValue_return;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Bean;
import org.aspectj.apache.bcel.generic.NEW;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hive.beans.FileStatusBean;
import org.springframework.stereotype.Component;
/***
 * utils to do HDFS operations.
 */
public class HDFSUtils {
	
	private String ip;
	
	private int port;

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	private  Logger log = Logger.getLogger(HDFSUtils.class);
	
	/**
	 * get hdfs FileSystem
	 * 
	 * @return : return a FileSystem Object if success, and null if not. 
	 */
	public synchronized FileSystem getFileSystem() {
		FileSystem fs = null;
		String url = "hdfs://" +ip + ":" + String.valueOf(port);
		Configuration config = new Configuration();
		  URI pathURI = URI.create(url);
		//config.set("fs.default.name", url);
		try {
			fs = FileSystem.get( pathURI,config);
			log.info("get filesystem ok. hdfs url:"+url);
		} catch (IOException e) {
			log.error("getFileSystem failed. url:"+url);
			return null;
		}
		return fs;
	}

	/**
	 * get hdfs FileSystem
	 * 
	 * @param ip: hdfs ip
	 * @param port: hdfs port
	 * @return : return a FileSystem Object if success, and null if not. 
	 */
	public synchronized static FileSystem getFileSystem(String ip,int port) {
		FileSystem fs = null;
		String url = "hdfs://" +ip + ":" + String.valueOf(port)+"/home/baimei";
		Configuration config = new Configuration();
		URI pathURI = URI.create(url);
		//config.set("fs.default.name", url);
		try {
			fs = FileSystem.get(pathURI,config);
			//log.info("get filesystem ok. hdfs url:"+url);
		} catch (IOException e) {
			//log.error("getFileSystem failed. url:"+url);
			return null;
		}
		return fs;
	}
	/**
	 * get the informaton of datanodes
	 * 
	 * @param fs 
	 * @return DatanodeInfo list if success, or null if not
	 */
	public synchronized DatanodeInfo[] getDataNodeInfos(FileSystem fs) {
		DistributedFileSystem dfs = (DistributedFileSystem) fs;
		DatanodeInfo[] infos=null;
		try {
			infos = dfs.getDataNodeStats();
			for (DatanodeInfo node : infos) {
				log.info("HostName: " + node.getHostName() + "/n"
						+ node.getDatanodeReport());
			}
		} catch (Exception e) {
			log.error("list node list failed ");
			return null;
		}
		return infos;
	}
	
	/**
	 * get the config information of hdfs filesystem
	 * 
	 * @param fs
	 * @return the iterator of the configEntrys
	 */
	public synchronized Iterator<Entry<String, String>> getConfigEntrys(FileSystem fs) {
		Iterator<Entry<String, String>> entrys = fs.getConf().iterator();
		while (entrys.hasNext()) {
			Entry<String, String> item = entrys.next();
			log.info("config [key:"+item.getKey() + "][vlaue:"+item.getValue()+"]");
		}
		return entrys;
	}
	
	/**
	 * make dirs.
	 * 
	 * @param fs
	 * @param dirName the file path of dir
	 * @param isroot is true if the user is a root user.
	 * @throws Exception failed to create the dir.
	 * 
	 */
	public synchronized void mkdirs(FileSystem fs, String dirName,boolean isroot) throws Exception {
		String dir;
		if(isroot)
			dir=dirName;
		else
			dir = fs.getWorkingDirectory() + "/" + dirName;
		Path src = new Path(dir);
		boolean succ;
		try{
			succ = fs.mkdirs(src);
			if (succ) {
				log.info("make dir success. [dir:" + dir + "]");
			} else {
				log.error("make dir failed:" + dir + "]");
				throw new Exception("make dir failed. [dir:" + dir + "]");
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * delete a given dir.
	 * 
	 * @param fs 
	 * @param dirName the path of the director
	 * @param isroot is true if the user is root user.
	 * @throws Exception  failed to delete the given director.
	 * 
	 */
	public synchronized void rmdirs(FileSystem fs, String dirName,boolean isroot) throws Exception {
		String dir;
		if(isroot)
			dir = dirName;
		else {
			dir = fs.getWorkingDirectory()+"/"+dirName;
		}
		Path src = new Path(dir);
		boolean succ;
		try{
			succ = fs.delete(src, true);
			if (succ) {
				log.info("remove directory success. [dir:" + dir + "]");
			} else {
				log.error("remove directory failed. [dir:" + dir + "]");
				throw new Exception ("remove directory failed. [dir:" + dir + "]");
			}	
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * make an empty file on the HDFS FileSystem
	 * 
	 * @param fs
	 * @param fileName the path of the file 
	 * @param isroot is true if the user is a root user
	 * @throws Exception failed to create a new file.
	 */
	public synchronized void createNewFile(FileSystem fs,String fileName,boolean isroot) throws Exception{
		String path;
		if(isroot)
			path=fileName;
		else {
			path=fs.getWorkingDirectory()+"/"+fileName;
		}
		Path src =new Path(path);
		boolean succ;
		succ = fs.createNewFile(src);
		try{
			if (succ) {
				log.info("create file success. [file path:" + path + "]");
			} else {
				log.error("create file failed. [file path:" + path + "]");
				throw new Exception("create file failed. [file path:" + path + "]");
			}
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * delete the given file
	 * 
	 * @param fs
	 * @param fileName the name of the file to delete
	 * @param isroot is true if the user is a root user.
	 * @throws Exception  failed to delete the given file
	 *
	 */
	public synchronized void deleteFile(FileSystem fs,String fileName,boolean isroot) throws Exception{
		String path;
		if(isroot)
			path=fileName;
		else {
			path=fs.getWorkingDirectory()+"/"+fileName;
		}
		Path src =new Path(path);
		boolean succ;
		try{
			succ = fs.delete(src);
			if (succ) {
				log.info("delete file success. [path:" + path + "]");
			} else {
				log.error("delete file failed. [path:" + path + "]");
				throw new Exception("delete file failed. [path:" + path + "]");
			}
		}catch (Exception e ){
			throw e;
		}
	}
	
	/**
	 * upload files to the HDFS FileSystem
	 * 
	 * @param fs
	 * @param local the file path fo the local file
	 * @param remote the file path to upload file to
	 * @param isOverWrite whether to overwrite the file if a file of the same name  already exists.
	 * @param isroot is true if the user is a root user.
	 * @throws Exception 
	 */
	public synchronized void upload(FileSystem fs, String local,
			String remote,boolean isOverWirte,boolean isroot) throws Exception{
		String dstString;
		if(isroot)
			dstString=remote;
		else {
			dstString=fs.getWorkingDirectory()+"/"+remote;
		}
		Path dst = new Path(dstString);
		Path src = new Path(local);
		long begin = System.currentTimeMillis();
		try{
			fs.copyFromLocalFile(false, isOverWirte, src, dst);
			log.info("upload from local to remote success. [local:" + local + "][remote:" + remote + "]");
			log.info("[time costs to upload:"+(System.currentTimeMillis() - begin)+"]");
		}catch(Exception e){
			log.info("upload from local to remote failed. [local:" + local + "][remote:" + remote + "]");
			throw e;
		}
}
	
	
	/**
	 * download files from hdfs
	 * @param fs
	 * @param local the path of the local file
	 * @param remote the path of the hdfs file
	 * @param isroot is true if the user is a root user.
	 * @throws Exception  failed to donwload.
	 */
	public synchronized void download(FileSystem fs, String local,
			String remote,boolean isroot) throws Exception {
		String dstString;
		if(isroot)
			dstString =remote;
		else {
			dstString = fs.getWorkingDirectory()+"/"+remote;
		}
		
		Path dst = new Path(dstString);
		Path src = new Path(local);
		try {
			fs.copyToLocalFile(false, dst, src);
			log.info("download from " + remote + " to  " + local + " successed. ");
		} catch (Exception e) {
			log.error("download from " + remote + " to  " + local + " failed :");
			throw e;
		}
	}
	
	/**
	 * to rename a file or a dir
	 * 
	 * @param fs
	 * @param oldName the original name of the file
	 * @param newName the new name of the file
	 * @throws Exception failed to rename the givent file
	 */
	public synchronized void rename(FileSystem fs,String oldName,String newName,boolean isroot) throws Exception{
		String oldpathString,newpathString;
		if(isroot){
			oldpathString =oldName;
			newpathString =newName;
		}
		else{
			oldpathString = fs.getWorkingDirectory()+"/"+oldName;
			newpathString = fs.getWorkingDirectory()+"/"+newName;
		}
		
			Path oldpath = new Path(oldpathString);
			Path newpath = new Path(newpathString);
			try{
				boolean isRenamed = fs.rename(oldpath, newpath);
				if(isRenamed){
					log.info("succeed to rename file/dir. file/dir name:"+oldName);
				}else{
					log.error("failed to rename file/dir. file/dir name:"+oldName);
					throw new Exception("failed to rename file/dir. file/dir name:"+oldName);
				}
			}catch(Exception e){
				throw e;
			}
			
		}
	
	
	/**
	 * to judge if the file exists
	 * 
	 * @param fs
	 * @param dirName the path of the file
	 * @param isroot is true if the user is a root file
	 * @return true if exist, or false if not
	 * @throws IOException
	 */
	public synchronized boolean exists(FileSystem fs,String dirName,boolean isroot) throws IOException{
		String pathString;
		if(isroot){
			pathString = dirName;
		}else {
			pathString= fs.getWorkingDirectory()+"/"+dirName;
		}
		
		Path src=new Path(pathString);
		
		if(fs.exists(src))
		{
			log.info("file exists. fileName:"+dirName);
			return true;
		}
		else
		{
			log.info("file not exists. fileName:"+dirName);
			return false;
		}
	}
	
	/**
	 * get the information about the replications
	 * 
	 * @param fs
	 * @param dirName the name of the file
	 * @param isroot is true if the user is a root user.
	 * @return the number of the replications
	 * @throws IOException
	 */
	public synchronized int getrep(FileSystem fs,String dirName,boolean isroot) throws IOException{
		String pathString;
		if(isroot){
			pathString = dirName;
		}else {
			pathString = fs.getWorkingDirectory()+"/"+dirName;
		}
		
		Path src =new Path(pathString);
		if(exists(fs,dirName,isroot))
		{
			return fs.getReplication(src);
		}
		else 
			return 0;
	}
	
	
	/**
	 * set the replications number
	 * 
	 * @param fs
	 * @param dirName
	 * @param replication the number of replications
	 * @param isroot is true if the user is a root user. 
	 * @return  true if success
	 * @throws IOException
	 */
	public synchronized boolean setrep(FileSystem fs,String fileName,short replication, boolean isroot) throws IOException{
		String pathString;
		if(isroot){
			pathString = fileName;
		}else {
			pathString = fs.getWorkingDirectory()+"/"+fileName;
		}
		Path src =new Path(pathString);
		if(exists(fs,fileName,isroot))
		{
			return fs.setReplication(src, replication);
		}
		else 
			return false;
	}
	
	
	/**
	 * get the last modification time
	 * 
	 * @param fs
	 * @param dirName the path of file
	 * @param isroot is true if the user is a root user
	 * @return the last modification time
	 */
	public synchronized long getLastModifyTime(FileSystem fs,String dirName,boolean isroot){
		String pathString;
		if(isroot){
			pathString=dirName;
		}else {
			pathString = fs.getWorkingDirectory()+"/"+dirName;
		}
			try{
				Path src =new Path(pathString);
				FileStatus fileStatus=fs.getFileStatus(src);
				long modificationTime=fileStatus.getModificationTime();
				return modificationTime;
			}catch(Exception e){
				log.error("failed to get modification time. file:"+dirName+" exception msg:"+e.getMessage());
				return -1;
			}
		}
	
		
	/**
	 * 
	 * 
	 * @param fs
	 * @param dirName
	 * @throws IOException
	 */
	/*
	public synchronized static void FileLoc(FileSystem fs,String dirName) throws IOException{
			Path workDir =fs.getWorkingDirectory();
			String dir =workDir +"/"+ dirName;
			Path src=new Path(dir);
			FileStatus fileStatus=fs.getFileStatus(src);
			BlockLocation[] blkLocation = fs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
			int blockLen =blkLocation.length;
			for(int i=0;i<blockLen;i++)
			{
				String[]hosts = blkLocation[i].getHosts();
				System.out.println("block"+i+"location:"+hosts[i]);
			}
		}
		
	*/
	/**
	 * a function to convert the size
	 * 
	 * @param  real size of byte
	 * @return the size with proper unit
	 */
	public synchronized String convertSize(long size) {
		String result = String.valueOf(size);
		if (size < 1024 * 1024) {
			result = String.valueOf(size / 1024) + " KB";
		} else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
			result = String.valueOf(size / 1024 / 1024) + " MB";
		} else if (size >= 1024 * 1024 * 1024) {
			result = String.valueOf(size / 1024 / 1024 / 1024) + " GB";
		} else {
			result = result + " B";
		}
		return result;
	}
	
	/**
	 * get the properties of the given file or dir.
	 * 
	 * @param fs
	 * @param path the path of the file
	 * @param isroot is true if the user is a root user.
	 * @return the file status
	 * @throws Exception 
	 * 
	 */
	public synchronized List<FileStatusBean> listFileStatus(FileSystem fs, String path,boolean isroot) throws Exception {
		log.debug("<<<<<<<<<hdfsutil listfilestatus:");
		log.debug("param.[path:"+path);
		if (null == path || path.trim().length()==0){
			path="/";
			log.info("param path is null, reset path to root path:/");
		}
		if(!isroot){
			path=fs.getWorkingDirectory()+"/"+path;
		}
		log.info("caculalte path:"+path);
		Path dst = new Path(path);
	
		try {
			String relativePath = "";
			if(fs==null){
				log.warn("fs is null");
			}
			FileStatus[] flistStatus = fs.listStatus(dst);
			List<FileStatusBean> filstStatusBeans = new ArrayList<FileStatusBean>();
			for(FileStatus status : flistStatus){
				FileStatusBean bean = new FileStatusBean(status);
				filstStatusBeans.add(bean);
				System.out.println(bean.toString());
			}
			log.info("get fileListStatus. dirname:"+dst);
			return filstStatusBeans;
		} catch (Exception e) {
			log.error("failed to get file list status. [dirName:"+dst+"][exception msg:"+e.getMessage()+"]");
			e.printStackTrace();
			throw new Exception("failed to get file list status. [dirName:"+dst+"][exception msg:"+e.getMessage()+"]");
		}
	}
	
	
	/**
	 * 向文件中写入数据
	 * 
	 * @param fs
	 * @param path
	 * @param data
	 */
/*	public synchronized static void write(FileSystem fs, String path,
			String data) {
		Path workDir = fs.getWorkingDirectory();
		Path dst = new Path(workDir + "/" + path);
		try {
			FSDataOutputStream dos = fs.create(dst);
			dos.writeUTF(data);
			dos.close();
			System.out.println("write content to " + path + " successed. ");
		} catch (Exception e) {
			System.out.println("write content to " + path + " failed ��");
			e.printStackTrace();
		}
	}
	
	
*/	/**
	 * 向文件中追加数据
	 * 
	 * @param fs
	 * @param path
	 * @param data
	 */
/*	public synchronized static void append(FileSystem fs, String path,
			String data) {
		Path workDir = fs.getWorkingDirectory();
		Path dst = new Path(workDir + "/" + path);
		try {
			FSDataOutputStream dos = fs.append(dst);
			dos.writeUTF(data);
			dos.close();
			System.out.println("append content to " + path + " successed. ");
		} catch (Exception e) {
			System.out.println("append content to " + path + " failed :");
			e.printStackTrace();
		}
	}
	
*/	
	/**
	 * 读取文件数据
	 * 
	 * @param fs
	 * @param path
	 * @return
	 */
/*	public synchronized static String read(FileSystem fs, String path) {
		String content = null;
		Path workDir = fs.getWorkingDirectory();
		Path dst = new Path(workDir + "/" + path);
		try {
			// reading
			FSDataInputStream dis = fs.open(dst);
			content = dis.readUTF();
			dis.close();
			System.out.println("read content from " + path + " successed. ");
		} catch (Exception e) {
			System.out.println("read content from " + path + " failed :");
			e.printStackTrace();
		}
		return content;
	}
*/	public static void main(String[] args) throws Exception{
		FileSystem fs = HDFSUtils.getFileSystem("127.0.0.1", 9000);
		if(fs==null){
			System.out.println("null");
		}
		else{
			System.out.println("----oh yeah");
		}
		FileStatus statuslist = fs.getFileStatus(new Path("/"));
		
		//HDFSUtils.getDataNodeInfos(fs);
		//HDFSUtils.listConfig(fs);
		//HDFSUtils.mkdirs(fs, "/home/jiege/com/once/i/love/you");
		//HDFSUtils.rmdirs(fs, "/home/jiege/com/once/i/love/you");
		//HDFSUtils.createNewFile(fs, "/home/jiege/com/once/i/love/newfile");
		//HDFSUtils.deleteFile(fs, "/home/jiege/com/once/i/love/newfile");
		//FileStatus[] flist = HDFSUtils.listFileStatus(fs, "/user/jiege/home/jiege/com/once",true);
		//System.out.println("================");
		//for(FileStatus f:flist){
		//	System.out.println("path:"+f.getPath()+". owner:"+f.getOwner()+". len:"+f.getLen()+". accesstime:"+f.getAccessTime());
		//}
		//URI uri = new URI("htfs://127.0.0.1:9000/haha/xx/");
		//System.out.println(new File(uri.getPath()).getParent());
	}
	/** 
	* judge the file if it is a dir 
	*
	* @param path the path to judge 
	* @return true if the give file is a director, false if not
	* @throws 
	*/
	public boolean isDirectory(Path path) {
		try {
			boolean isdir = getFileSystem().isDirectory(path);
			System.out.println("isdir:"+isdir);
			return isdir;
		} catch (IOException e) {
			log.equals("failed to judge whether the path is directory. path:"+path.toString()+". exception:"+e.getMessage());
			return false;
		}
	}
	
}

