package org.hiveadmin.hive.beans;

/**
 * @ClassName Columns
 * @Description 数据库列类型
 */
public class Columns {
	private String cols_name;
	private String cols_type;
	private String cols_comment="";
	
	
	public String getCols_name() {
		return cols_name;
	}


	public void setCols_name(String cols_name) {
		this.cols_name = cols_name;
	}


	public String getCols_type() {
		return cols_type;
	}


	public void setCols_type(String cols_type) {
		this.cols_type = cols_type;
	}


	public String getCols_comment() {
		return cols_comment;
	}


	public void setCols_comment(String cols_comment) {
		this.cols_comment = cols_comment;
	}


	public Columns(){}

}
