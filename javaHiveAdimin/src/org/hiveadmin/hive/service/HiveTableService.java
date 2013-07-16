/**  
* @Project: javaHiveAdimin
* @Title: HiveTableService.java
* @Package org.hiveadmin.hive.service
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 16, 2013 9:01:42 AM
* @version V1.0  
*/
package org.hiveadmin.hive.service;

/**
 * @ClassName HiveTableService
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 16, 2013 9:01:42 AM
 */
class TableColum{
	String col_name;
	String col_data_type;
	String col_comment;
	/**
	 * 
	 */
	public TableColum(String name) {
	}
	
}
public class HiveTableService {
	boolean isExternal=false;
	boolean createIfNotExist=true;
	String dataBaseName="";
	
	public void createTable(){
		;
	}
}
