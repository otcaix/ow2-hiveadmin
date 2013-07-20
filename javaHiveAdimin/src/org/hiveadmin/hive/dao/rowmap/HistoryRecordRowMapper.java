/**  
* @Project: javaHiveAdimin
* @Title: HistoryRecordRowMapper.java
* @Package org.hiveadmin.hive.dao.rowmap
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 11:08:52 PM
* @version V1.0  
*/
package org.hiveadmin.hive.dao.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.hive.ql.parse.HiveParser.restrictOrCascade_return;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.springframework.jdbc.core.RowMapper;

/**
 * @ClassName HistoryRecordRowMapper
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 11:08:52 PM
 */
public class HistoryRecordRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Object mapRow(ResultSet res, int index) throws SQLException {
		HistoryRecord historyRecord = new HistoryRecord(res.getInt("op_record_id"),res.getString("op_user_name"),res.getString("op_desc"),res.getString("op_sql"),res.getBoolean("op_res"),res.getTimestamp("op_ts"));
		return historyRecord;
	}

}
