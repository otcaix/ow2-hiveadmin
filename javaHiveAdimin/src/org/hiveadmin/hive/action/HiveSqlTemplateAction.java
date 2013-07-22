/**  
* @Project: javaHiveAdimin
* @Title: HiveSqlTemplateAction.java
* @Package org.hiveadmin.hive.action
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 5:09:01 PM
* @version V1.0  
*/
package org.hiveadmin.hive.action;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.hadoop.hive.ql.parse.HiveParser.mapType_return;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hiveadmin.hive.beans.HiveSqlTemplateBean;
import org.hiveadmin.hive.service.HiveSqlTemplateService;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName HiveSqlTemplateAction
 * @Description TODO
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 5:09:01 PM
 */
@Component
public class HiveSqlTemplateAction extends ActionSupport{
	
	private HiveSqlTemplateService hiveSqlTemplateService;
	private HiveSqlTemplateBean hiveSqlTemplateBean;
	private String errorMsg;
	private Logger log = Logger.getLogger(HiveSqlTemplateAction.class);
	private String temp_name_to_delete;
	private String temp_owner_name;
	private List<HiveSqlTemplateBean> hiveSqlTemplateList;
	private ResultSet executeHiveSqlTemplateResultset;
	private List executeHiveSqlTemplateList;
	
	public HiveSqlTemplateService getHiveSqlTemplateService() {
		return hiveSqlTemplateService;
	}
	@Resource
	public void setHiveSqlTemplateService(
			HiveSqlTemplateService hiveSqlTemplateService) {
		this.hiveSqlTemplateService = hiveSqlTemplateService;
	}

	public HiveSqlTemplateBean getHiveSqlTemplateBean() {
		return hiveSqlTemplateBean;
	}
	public void setHiveSqlTemplateBean(HiveSqlTemplateBean hiveSqlTemplateBean) {
		this.hiveSqlTemplateBean = hiveSqlTemplateBean;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getTemp_name_to_delete() {
		return temp_name_to_delete;
	}
	public void setTemp_name_to_delete(String temp_name_to_delete) {
		this.temp_name_to_delete = temp_name_to_delete;
	}
	
	public String getTemp_owner_name() {
		return temp_owner_name;
	}
	public void setTemp_owner_name(String temp_owner_name) {
		this.temp_owner_name = temp_owner_name;
	}
	public void setHiveSqlTemplateList(List<HiveSqlTemplateBean> hiveSqlTemplateList) {
		this.hiveSqlTemplateList = hiveSqlTemplateList;
	}
	
	public List<HiveSqlTemplateBean> getHiveSqlTemplateList() {
		return hiveSqlTemplateList;
	}
	
	
	public ResultSet getExecuteHiveSqlTemplateResultset() {
		return executeHiveSqlTemplateResultset;
	}
	public void setExecuteHiveSqlTemplateResultset(
			ResultSet executeHiveSqlTemplateResultset) {
		this.executeHiveSqlTemplateResultset = executeHiveSqlTemplateResultset;
	}
	
	
	public List getExecuteHiveSqlTemplateList() {
		return executeHiveSqlTemplateList;
	}
	public void setExecuteHiveSqlTemplateList(List executeHiveSqlTemplateList) {
		this.executeHiveSqlTemplateList = executeHiveSqlTemplateList;
	}
	public String createHiveSqlTemplate(){
		try {
			hiveSqlTemplateBean.setOwner_name((String) ServletActionContext.getContext().getSession().get("user"));
			hiveSqlTemplateService.createHiveSqlTemplate(hiveSqlTemplateBean);
			
		} catch (Exception e) {
			this.errorMsg="create hivesql template error. [exception msg:"+e.getMessage()+"]";
			log.error("create HiveSqlTemplate success.[msg:"+e.getMessage()+"]");
			return ERROR;
		}
		log.info("create HiveSqlTemplate success");
		return SUCCESS;
	}

	public String deleteHiveSqlTemplate(){
		try{
			hiveSqlTemplateService.deleteHiveSqlTemplate(hiveSqlTemplateBean.getTemp_name());
		}catch(Exception e){
			errorMsg = "delete HiveSqlTemplate failed. [msg:"+e.getMessage()+"]";
			log.error("delete HiveSqlTemplate failed. [msg:"+e.getMessage()+"]");
			return ERROR;
		}
		log.info("delete HiveSqlTemplate success");
		return SUCCESS;
	}
	public String updateHiveSqlTemplate(){
		try {
			hiveSqlTemplateBean.setOwner_name((String) ServletActionContext.getContext().getSession().get("user"));
			hiveSqlTemplateService.updataHiveSqlTemplate(hiveSqlTemplateBean);
			
		} catch (Exception e) {
			errorMsg = "update HiveSalTemplate failed.[msg:"+e.getMessage()+"]";
			log.error("update HiveSalTemplate failed.[msg:"+e.getMessage()+"]");
			return ERROR;
		}
		log.info("update HiveSalTemplate success.");
		return SUCCESS;
	}
	public String fetchHiveSqlTemplate(){
		try {
			System.out.println("===================template name1111");
			System.out.println(hiveSqlTemplateBean.getTemp_name());
			String temp_name = hiveSqlTemplateBean.getTemp_name();
			hiveSqlTemplateBean = hiveSqlTemplateService.getHiveSqlTemplate("temp_1");
			System.out.println("===================template name");
			System.out.println(hiveSqlTemplateBean.getTemp_name());
		} catch (Exception e) {
			log.error("get hiveSqlTemplate failed. [temp_name:"+hiveSqlTemplateBean.getTemp_content()+"][exception msg:"+e.getMessage()+"]");
			errorMsg = "get hiveSqlTemplate failed. [temp_name:"+hiveSqlTemplateBean.getTemp_content()+"][exception msg:"+e.getMessage()+"]";
			return ERROR;
		}
		log.info("get hiveSqlTemplate successs. [temp_name:"+hiveSqlTemplateBean.getTemp_name()+"]");
		return SUCCESS;
	}
	public String fetchHiveSqlTemplateList(){
		try {
			hiveSqlTemplateList = hiveSqlTemplateService.getHiveSqlTemplateList((String) ServletActionContext.getContext().getSession().get("user"));
			System.out.println("==============="+hiveSqlTemplateList.size());
		} catch (Exception e) {
			errorMsg = "get HiveSqlTempList failed. [owner:"+temp_owner_name+"][exception msg:"+e.getMessage()+"]";
			log.error("get HiveSqlTempList failed. [owner:"+temp_owner_name+"][exception msg:"+e.getMessage()+"]");
			return ERROR;
		}
		log.info("get HiveSqlTempList success. [owner:"+temp_owner_name+"]");
		return SUCCESS;
	}
	
	public String executeHiveSqlTemplate(){
		try {
			//executeHiveSqlTemplateResultset = hiveSqlTemplateService.executeHiveSqlTemplate(hiveSqlTemplateBean);
			executeHiveSqlTemplateList = hiveSqlTemplateService.executeHiveSqlTemplate(hiveSqlTemplateBean);
			Iterator it = executeHiveSqlTemplateList.iterator();
			Map map=null;
			while(it.hasNext()){
				map = (Map) it.next();
				Set mapkeyset = map.keySet();
				Iterator setit = mapkeyset.iterator();
				while(setit.hasNext()){
					System.out.print(map.get(setit.next())+"\t");
				}
				System.out.println();
				
			}
		
		} catch (Exception e) {
			errorMsg = "execute HiveSqlTemplate error. [exeption msg:"+e.getMessage()+"]";
			log.error("execute HiveSqlTemplate error. [exeption msg:"+e.getMessage()+"]");
			return ERROR;
		}
		log.info("execute HiveSqlTemplate success.");
		return SUCCESS;
	}
}
