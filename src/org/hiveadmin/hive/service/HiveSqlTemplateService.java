/**  
* @Project: javaHiveAdimin
* @Title: HiveSqlTemplateService.java
* @Package org.hiveadmin.hive.service.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 4:52:38 PM
* @version V1.0  
*/
package org.hiveadmin.hive.service;


import java.sql.ResultSet;
import java.util.List;

import org.hiveadmin.hive.beans.HiveSqlTemplateBean;

/**
 * HiveSqlTemplateService
 * hive sql template operations 
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 4:52:38 PM
 */
public interface HiveSqlTemplateService {
	/** 
	* createHiveSqlTemplate 
	* <p>to create a template<br>
	* 
	* @throws trow exception if failed
	*/
	public void createHiveSqlTemplate(HiveSqlTemplateBean temp) throws Exception;
	/** 
	* updataHiveSqlTemplate 
	* <p>update a template<br>
	* @param temp
	* 
	*/
	public void updataHiveSqlTemplate(HiveSqlTemplateBean temp);
	/** 
	* getHiveSqlTemplate 
	* <p>get hive template by given user<br>
	* @param temp_name the template name 
	*  
	*/
	public HiveSqlTemplateBean getHiveSqlTemplate(String temp_name);
	/** 
	* deleteHiveSqlTemplate 
	* <p><br>
	* @param 
	* @return
	* @throws 
	*/
	public void deleteHiveSqlTemplate(String temp_name);
	/** 
	* getHiveSqlTemplateList 
	* <p>get template list of a given user<br>
	* @param 
	* @return
	* @throws 
	*/
	public List<HiveSqlTemplateBean> getHiveSqlTemplateList(String ownerName);
	/** 
	* executeHiveSqlTemplate 
	* <p>to execute the sql from a template<br>
	* @param database the database to execute the sql
	* @param tmep the template
	* @return the result. It is a list of maps.
	* @throws 
	*/
	public List executeHiveSqlTemplate(String database,HiveSqlTemplateBean temp) throws Exception;

}
