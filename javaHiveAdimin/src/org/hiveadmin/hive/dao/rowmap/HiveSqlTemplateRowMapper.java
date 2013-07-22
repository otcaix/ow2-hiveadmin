/**  
* @Project: javaHiveAdimin
* @Title: HiveSqlTemplateRowMapper.java
* @Package org.hiveadmin.hive.dao.rowmap
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 11:35:09 PM
* @version V1.0  
*/
package org.hiveadmin.hive.dao.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hiveadmin.hive.beans.HiveSqlTemplateBean;
import org.springframework.jdbc.core.RowMapper;

/**
 * @ClassName HiveSqlTemplateRowMapper
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 11:35:09 PM
 */
public class HiveSqlTemplateRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		HiveSqlTemplateBean hiveSqlTemplateBean = new HiveSqlTemplateBean();
		hiveSqlTemplateBean.setTemp_name(res.getString("temp_name"));
		hiveSqlTemplateBean.setOwner_name(res.getString("owner_name"));
		hiveSqlTemplateBean.setTemp_description(res.getString("temp_description"));
		hiveSqlTemplateBean.setTemp_content(res.getString("temp_content"));
		hiveSqlTemplateBean.setTemp_ts(res.getTimestamp("temp_ts"));
		
		return hiveSqlTemplateBean;
	}

}
