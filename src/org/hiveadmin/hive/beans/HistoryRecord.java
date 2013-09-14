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

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class is to describe the history record of operation.
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 19, 2013 8:41:27 PM
 */
public class HistoryRecord implements Serializable{
	
	/**           
	 * op_record_id:this property is the id of this operation         
	 */
	private int op_record_id;
	/**           
	 * op_user_name:this property is the name of user who operate        
	 */
	private String op_user_name;
	/**           
	 * op_desc:this property is the description about this operation        
	 */
	private String op_desc;
	/**           
	 * op_sql:this property is the SQL sentence    
	 */
	private String op_sql;
	/**           
	 * op_res:this property is the result status        
	 */
	private boolean op_res;
	/**           
	 * op_ts:this property is the time of this operation occur        
	 */
	private Timestamp op_ts;
	/**           
	 * status:this property is status of operation          
	 */
	private String status;
	/**           
	 * resultfile:this property is the file which contains the result         
	 */	
	private String resultfile;
	
	 /** 
	  * This method is to create an object of HistoryRecord
	  */
	public HistoryRecord(){
		this.op_user_name="";
		this.op_desc="";
		this.op_sql="";
		this.op_res=false;
		this.resultfile="";
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
