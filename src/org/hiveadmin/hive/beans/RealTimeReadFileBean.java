/**  
* @Project: javaHiveAdimin
* @Title: RealTimeReadFileBean.java
* @Package org.hiveadmin.hive.beans
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Aug 17, 2013 11:21:13 AM
* @version V1.0  
*/
package org.hiveadmin.hive.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.booleanValue_return;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @ClassName RealTimeReadFileBean
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Aug 17, 2013 11:21:13 AM
 */
@Component
public class RealTimeReadFileBean {
	private boolean more;
	private List<String> lines;
	private int lastread;
	public RealTimeReadFileBean(){
		lines = new ArrayList<String>();
		more=true;
	}
	public int getLastread() {
		return lastread;
	}
	public void setLastread(int lastread) {
		this.lastread = lastread;
	}
	public boolean getMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}


}
