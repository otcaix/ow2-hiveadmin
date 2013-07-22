/**  
* @Project: javaHiveAdimin
* @Title: HiveSqlTemplateBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 4:49:57 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.sql.Timestamp;

/**
 * @ClassName HiveSqlTemplateBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 4:49:57 PM
 */
public class HiveSqlTemplateBean {
	private String temp_name;
	private String owner_name;
	private String temp_description;
	private String temp_content;
	private Timestamp temp_ts;

	public String getTemp_description() {
		return temp_description;
	}
	public void setTemp_description(String temp_description) {
		this.temp_description = temp_description;
	}

	
	public String getTemp_name() {
		return temp_name;
	}
	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getTemp_content() {
		return temp_content;
	}
	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}
	public Timestamp getTemp_ts() {
		return temp_ts;
	}
	public void setTemp_ts(Timestamp temp_ts) {
		this.temp_ts = temp_ts;
	}
}
