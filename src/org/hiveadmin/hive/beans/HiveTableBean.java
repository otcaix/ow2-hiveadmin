/**  
* @Project: javaHiveAdimin
* @Title: HiveTableBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 18, 2013 3:54:07 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HiveTableBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 18, 2013 3:54:07 PM
 */
public class HiveTableBean {
	private String database;
	private boolean tableType;
	private String tablename;
	private boolean createifnotexist;
	private String tableComment;
	private String partitionedby;
	private List<HiveTableFiledBean> fieldList;
	private List<String> primaryTypeList;
	
	public HiveTableBean(){
		primaryTypeList = new ArrayList<>();
		primaryTypeList.add("TINYINT");
		primaryTypeList.add("SMALLINT");
		primaryTypeList.add("INT");
		primaryTypeList.add("BIGINT");
		primaryTypeList.add("BOOLEAN");
		primaryTypeList.add("FLOAT");
		primaryTypeList.add("DOUBLE");
		primaryTypeList.add("STRING");
		primaryTypeList.add("BINARY");
		primaryTypeList.add("TIMESTAMP");
		primaryTypeList.add("DECIMAL");
	}
	
	public boolean isTableType() {
		return tableType;
	}
	public void setTableType(boolean tableType) {
		this.tableType = tableType;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public boolean isCreateifnotexist() {
		return createifnotexist;
	}
	public void setCreateifnotexist(boolean createifnotexist) {
		this.createifnotexist = createifnotexist;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getPartitionedby() {
		return partitionedby;
	}
	public void setPartitionedby(String partitionedby) {
		this.partitionedby = partitionedby;
	}
	public List<HiveTableFiledBean> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<HiveTableFiledBean> fieldList) {
		this.fieldList = fieldList;
	}
	public List<String> getPrimaryTypeList() {
		return primaryTypeList;
	}
}

