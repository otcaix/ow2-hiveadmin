/**  
* @Project: javaHiveAdimin
* @Title: UserDao.java
* @Package org.hiveadmin.hive.dao
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 9:11:30 PM
* @version V1.0  
*/
package org.hiveadmin.hive.dao;

import org.hiveadmin.hive.beans.User;

/**
 * @ClassName UserDao
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 9:11:30 PM
 */
public interface UserDao {

	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(String name);
	public User getUser(int id);
	
	
}
