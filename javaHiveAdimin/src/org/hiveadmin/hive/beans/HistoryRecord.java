/**  
* @Project: javaHiveAdimin
* @Title: HistoryRecord.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 19, 2013 8:41:27 PM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.sql.Timestamp;

/**
 * @ClassName HistoryRecord
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 8:41:27 PM
 */
public class HistoryRecord {
	
	private int op_record_id;
	private String op_user_name;
	private String op_desc;
	private String op_sql;
	private boolean op_res;
	private Timestamp op_ts;
	private String status;
	private String resultfile;
	
	public HistoryRecord(){}
	
	public HistoryRecord(int recordid,String username,String desc,String sql,boolean res,Timestamp ts){
		this.op_record_id = recordid;
		this.op_user_name = username;
		this.op_desc=desc;
		this.op_sql = sql;
		this.op_res = res;
		this.op_ts = ts;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultfile() {
		return resultfile;
	}

	public void setResultfile(String resultfile) {
		this.resultfile = resultfile;
	}

	public String getOp_user_name() {
		return op_user_name;
	}
	public void setOp_user_name(String op_user_name) {
		this.op_user_name = op_user_name;
	}
	
	public int getOp_record_id() {
		return op_record_id;
	}
	public void setOp_record_id(int op_record_id) {
		this.op_record_id = op_record_id;
	}
	
	public String getOp_desc() {
		return op_desc;
	}
	public void setOp_desc(String op_desc) {
		this.op_desc = op_desc;
	}
	public String getOp_sql() {
		return op_sql;
	}
	public void setOp_sql(String op_sql) {
		this.op_sql = op_sql;
	}
	public boolean isOp_res() {
		return op_res;
	}
	public void setOp_res(boolean op_res) {
		this.op_res = op_res;
	}
	public Timestamp getOp_ts() {
		return op_ts;
	}
	public void setOp_ts(Timestamp op_ts) {
		this.op_ts = op_ts;
	}
}
