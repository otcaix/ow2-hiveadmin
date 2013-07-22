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
 * @ClassName HiveSqlTemplateService
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 4:52:38 PM
 */
public interface HiveSqlTemplateService {
	public void createHiveSqlTemplate(HiveSqlTemplateBean temp) throws Exception;
	public void updataHiveSqlTemplate(HiveSqlTemplateBean temp);
	public HiveSqlTemplateBean getHiveSqlTemplate(String temp_name);
	public void deleteHiveSqlTemplate(String temp_name);
	public List<HiveSqlTemplateBean> getHiveSqlTemplateList(String ownerName);
	public List executeHiveSqlTemplate(HiveSqlTemplateBean temp) throws Exception;
}
