/**  
 * @Project: javaHiveAdimin
 * @Title: User.java
 * @Package org.hiveadmin.hive.beans
 * @author wangjie,wangjie370124@163.com
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.beans;

import java.io.Serializable;

/**
 *This class is to describe user information.
 * @author Wangjie wangjie370124@163.com
 * @date Jul 19, 2013 9:09:26 PM
 */

public class User implements Serializable{
	/**           
	 * userid:this property is the id of user         
	 */
	private int userid;
	/**           
	 * username:this property is the name of user         
	 */
	private String username;
	/**           
	 * usergroup:this property is the group which this user belongs to         
	 */
	private String usergroup;
	/**           
	 * userpass:this property is the user's password         
	 */
	private String userpass;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	
}
