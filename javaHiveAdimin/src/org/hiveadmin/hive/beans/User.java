/**  
* @Project: javaHiveAdimin
* @Title: User.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 9:09:26 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

/**
 * @ClassName User
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 9:09:26 PM
 */

public class User {
	private int userid;
	private String username;
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
}
