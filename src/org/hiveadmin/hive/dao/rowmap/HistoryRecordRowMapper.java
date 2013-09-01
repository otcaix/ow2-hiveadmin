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
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setOp_record_id(res.getInt("op_record_id"));
		historyRecord.setOp_user_name(res.getString("op_user_name"));
		historyRecord.setOp_desc(res.getString("op_desc"));
		historyRecord.setOp_sql(res.getString("op_sql"));
		historyRecord.setOp_res(res.getBoolean("op_res"));
		historyRecord.setResultfile(res.getString("op_resultfile"));
		historyRecord.setOp_ts(res.getTimestamp("op_ts"));
		return historyRecord;
	}

}
