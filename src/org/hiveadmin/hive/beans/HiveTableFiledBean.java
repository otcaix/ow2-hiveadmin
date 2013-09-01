/**  
* @Project: javaHiveAdimin
* @Title: HiveTableFiledBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 18, 2013 4:05:13 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

/**
 * @ClassName HiveTableFiledBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 18, 2013 4:05:13 PM
 */
public class HiveTableFiledBean{
	private String col_name;
	private String col_type;
	private String col_comment;
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getCol_type() {
		return col_type;
	}
	public void setCol_type(String col_type) {
		this.col_type = col_type;
	}
	public String getCol_comment() {
		return col_comment;
	}
	public void setCol_comment(String col_comment) {
		this.col_comment = col_comment;
	}
}