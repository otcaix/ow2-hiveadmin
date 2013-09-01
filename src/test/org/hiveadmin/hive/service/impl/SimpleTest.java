/**  
* @Project: javaHiveAdimin
* @Title: SimpleTest.java
* @Package test.org.hiveadmin.hive.service.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 4:17:49 PM
* @version V1.0  
*/
package test.org.hiveadmin.hive.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.hadoop.hive.jdbc.HiveConnection;
import org.hiveadmin.hive.hiveutils.HiveConnectionBean;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName SimpleTest
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 4:17:49 PM
 */
public class SimpleTest {
	@Test
	public void  testxx(){
		try {
			Connection conn = HiveConnectionBean.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
