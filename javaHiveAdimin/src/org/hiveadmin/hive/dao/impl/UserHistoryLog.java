/**  
* @Project: javaHiveAdimin
* @Title: UserHistoryLog.java
* @Package org.hiveadmin.hive.dao.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 8:49:09 PM
* @version V1.0  
*/
package org.hiveadmin.hive.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.hive.ql.parse.HiveParser.restrictOrCascade_return;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.nullCondition_return;
import org.apache.hadoop.mapred.JobHistory.Values;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.HiveConst;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.UserHistory;
import org.hiveadmin.hive.dao.rowmap.HistoryRecordRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserHistoryLog
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 8:49:09 PM
 */
@Component
public class UserHistoryLog implements UserHistory{

	private JdbcTemplate jdbcTemplate;
	
	Logger log = Logger.getLogger(UserHistoryLog.class);
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.dao.UserHistory#addHistotyRecord(org.hiveadmin.hive.beans.HistoryRecord)
	 */
	@Override
	public void addHistotyRecord(HistoryRecord record) {
		String sql = "insert into op_record (op_user_name,op_desc,op_sql,op_res,op_ts) values(?,?,?,?,?)";
		try{
			if(record.getOp_ts()==null){
				record.setOp_ts(new Timestamp(new Date().getTime()));
			}
			jdbcTemplate.update(sql, new Object[]{record.getOp_user_name(),record.getOp_desc(),record.getOp_sql(),record.isOp_res(),record.getOp_ts()});
		}catch(Exception e){
			log.error("add user history record failed.[exception msg:"+e.getMessage()+"]");
		}
	}
	/* (non-Javadoc)
	 * @return: 若查询成功则返回历史列表;查询失败,返回空 
	 * @see org.hiveadmin.hive.dao.UserHistory#getHistoryRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HistoryRecord> getHistoryRecord(String userName, String sort) {
		if(sort == null ||sort.trim().length()==0){
			log.info("sort type param is null. use default sort type. [default sortType:ASC");
			sort=HiveConst.ASC;
		}
		String sql = "select op_record_id,op_user_name,op_desc,op_sql,op_res,op_ts from op_record order by op_ts "+sort;
		try{
			return jdbcTemplate.query(sql, new HistoryRecordRowMapper());
		}catch(Exception e){
			log.error("get history record list error.[exception msg:"+e.getMessage()+"]");
			return null;
		}
	}

}
