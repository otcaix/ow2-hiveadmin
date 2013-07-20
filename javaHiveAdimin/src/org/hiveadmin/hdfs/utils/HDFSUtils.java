/**  
* @Project: javaHiveAdimin
* @Title: HDFSUtils.java
* @Package org.hiveadmin.hdfs.utils
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 16, 2013 1:12:36 PM
* @version V1.0  
*/
package org.hiveadmin.hdfs.utils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;	
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Component;
/***
 * HDFS Utils
 * @author 
 *
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
	/**
	 * 获取hdfs FileSystem
	 * 
	 * @param ip: hdfs ip
	 * @param port: hdfs port
	 * @return : 若获取成功,返回文件系统引用,否则返回null 
	 */
	private  Logger log = Logger.getLogger(HDFSUtils.class);
	
	public synchronized FileSystem getFileSystem() {
		FileSystem fs = null;
		String url = "hdfs://" +ip + ":" + String.valueOf(port);
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			fs = FileSystem.get(config);
			log.info("get filesystem ok. hdfs url:"+url);
		} catch (IOException e) {
			log.error("getFileSystem failed. url:"+url);
			return null;
		}
		return fs;
	}
	
	
	/**
	 * 获取datanode信息
	 * 
	 * @param fs:文件系统引用
	 * @return : 若获取成功返回DatanodeInfo列表,否则,若获取失败,返回null
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
	 * 获取hadoop配置信息列表
	 * 
	 * @param fs
	 * @return: 返回hdfs配置信息列表的迭代器
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
	 * 创建文件夹.
	 * note:若输入文件的路径已经存在,不会报错.
	 * @param fs
	 * @param dirName:文件路径
	 * @param isroot:若为true,则dirName为相对于根目录的path,否则为相对于当前用户默认工作目录的path
	 * @throws Exception:若创建失败,抛出异常.
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
	 * 删除文件夹.
	 * note:若路径不存在,则抛出异常
	 * 
	 * @param fs
	 * @param dirName:文件的路径
	 * @param isroot:若为true,则dirName为相对于根目录的路径,否则为相对于当前用户默认的工作目录
	 * @throws Exception :删除失败
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
	 * 创建文件
	 * 若路径已经存在,则抛出异常
	 * @param fs
	 * @param fileName,文件路径
	 * @param isroot:若为true,则是相对于根目录的路径,否则为相对于当前用户默认工作目录的路径
	 * @throws Exception 
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
	 * 删除文件
	 * 若文件不存在,则抛出异常 
	 * @param fs
	 * @param fileName:文件名称
	 * @param isroot:若为true,则是相对于根目录的路径,否则为相对于当前用户默认工作目录的路径
	 * @throws Exception :删除失败
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
	 * 上传文件到hdfs
	 * 本地文件不存在时抛出异常
	 * 
	 * @param fs
	 * @param local:本地文件路径
	 * @param remote:hdfs文件路径
	 * @param isroot:hdfs路径是否是相对于根目录
	 * @throws Exception 
	 */
	public synchronized void upload(FileSystem fs, String local,
			String remote,boolean isroot) throws Exception{
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
			fs.copyFromLocalFile(false, true, src, dst);
			log.info("upload from local to remote success. [local:" + local + "][remote:" + remote + "]");
			log.info("[time costs to upload:"+(System.currentTimeMillis() - begin)+"]");
		}catch(Exception e){
			log.info("upload from local to remote failed. [local:" + local + "][remote:" + remote + "]");
			throw e;
		}
}
	
	
	/**
	 * 从hdfs下载文件到本地
	 * @param fs
	 * @param local:本地文件路径
	 * @param remote:hdfs文件路径
	 * @param isroot:hdfs文件路径是否是相对于根目录
	 * @throws Exception :下载失败
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
	 * 重命名文件或文件夹
	 * 
	 * @param fs
	 * @param oldName:文件或文件夹原名
	 * @param newName:文件或文件夹新名
	 * @throws Exception:创建失败
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
	 * 判断文件/目录是否存在
	 * 
	 * @param fs
	 * @param dirName:文建路径
	 * @param isroot:文件名称是否是相对于根目录
	 * @return:若存在,返回true,否则返回false.
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
	 * 获取hdfs副本信息
	 * 
	 * @param fs
	 * @param dirName:文件名称.
	 * @param isroot:是否是相对于根目录的路径.
	 * @return :副本数目
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
	 * 设置hdfs文件副本信息
	 * 
	 * @param fs
	 * @param dirName
	 * @param replication:文件副本数目
	 * @param isroot:文件是否是相对于根目录的路径
	 * @return :若设置成功,返回true,否则false
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
	 * 获取文件最后修改时间
	 * 
	 * @param fs
	 * @param dirName:文件路径
	 * @param isroot:是否是相对于根目录的路径
	 * @return:获取成功返回>0的数,否则返回-1
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
	 * 按照大小,获取对应单位的大小
	 * 
	 * @param size
	 * @return
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
	 * 获取某个目录或文件的属性信息.
	 * 
	 * @param fs
	 * @param path:文件目录,若传入路径为空,则列出当前用户的默认目录
	 * @param isroot:是否是相对于根目录的路径
	 * @return 
	 * @throws Exception 
	 * 
	 */
	public synchronized FileStatus[] listFileStatus(FileSystem fs, String path,boolean isroot) throws Exception {
		
		Path dst;
		if (null == path || "".equals(path)) {
			dst = fs.getWorkingDirectory();
		} else {
			String dstString;
			if(isroot){
				dstString = path;
			}else {
				dstString=fs.getWorkingDirectory()+"/"+path;
			}
			dst = new Path(dstString);
		}
		try {
			String relativePath = "";
			FileStatus[] flistStatus = fs.listStatus(dst);
			log.info("get fileListStatus. dirname:"+dst);
			return flistStatus;
		} catch (Exception e) {
			log.error("failed to get file list status. [dirName:"+dst+"][exception msg:"+e.getMessage()+"]");
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
		//FileSystem fs = HDFSUtils.getFileSystem("127.0.0.1", 9000);
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
}
	
}

