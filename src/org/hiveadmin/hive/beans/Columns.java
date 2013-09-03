/**  
 * @Project: javaHiveAdimin
 * @Title: Columns.java
 * @Package org.hiveadmin.hive.beans
 * @author xuliang,xuliang12@octaix.iscas.ac.cn
 * @Copyright: 2013 www.1000chi.comInc. All rights reserved.
 * @version V1.0  
 */
package org.hiveadmin.hive.beans;


 /**
  * Columns is a class which describe the hive table's column.
  * This class has three properties to store the data which from the table's column<p>
  * @author xuliang,xuliang12@octaix.iscas.ac.cn
  */

public class Columns {
	
	/**           
	 * cols_name:this property is to describe column's name<br>
	 */
	private String cols_name;
	
	/**           
	 * cols_type:this property is to describe the column's type <br>          
	 */
	private String cols_type;
	
	/**           
	 * cols_comment:this property is to describe the column's comment<br>          
	 */
	private String cols_comment="";
	
	
	 /** 
	  * This method can return the column's name.
	  * @return String
	  */
	public String getCols_name() {
		return cols_name;
	}


	 /** 
	  * This method can reset the column's name.
	  * @param cols_name
	  */
	public void setCols_name(String cols_name) {
		this.cols_name = cols_name;
	}


	 /** 
	  * This method can return the column's type.
	  * @return String
	  */
	public String getCols_type() {
		return cols_type;
	}


	 /** 
	  * This method  can reset the column's type
	  * @param cols_type
	  */
	public void setCols_type(String cols_type) {
		this.cols_type = cols_type;
	}


	 /** 
	  * This method can return the content of this column's comment.
	  * @return String
	  */
	public String getCols_comment() {
		return cols_comment;
	}


	 /** 
	  * This method can reset the coumn's comment.
	  * @param cols_comment
	  */
	public void setCols_comment(String cols_comment) {
		this.cols_comment = cols_comment;
	}


	 /** 
	 * This method can create a new columns object. 
	 */
	public Columns(){}

}
