/**  
* @Project: javaHiveAdimin
* @Title: HiveSqlTemplateServiceImpl.java
* @Package org.hiveadmin.hive.service.impl
* @Description: TODO
* @author wangjie wangjie370124@163.com
* @date Jul 20, 2013 5:01:02 PM
* @version V1.0  
*/
package org.hiveadmin.hive.service.impl;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.hadoop.hive.ql.parse.HiveParser.restrictOrCascade_return;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.nullCondition_return;
import org.apache.log4j.Logger;
import org.hiveadmin.hive.beans.HiveSqlTemplateBean;
import org.hiveadmin.hive.dao.rowmap.HiveSqlTemplateRowMapper;
import org.hiveadmin.hive.hiveutils.HiveConnectionBean;
import org.hiveadmin.hive.service.HiveSqlTemplateService;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * HiveSqlTemplateServiceImpl
 * 
 * @author wangjie wangjie370124@163.com
 * @date Jul 20, 2013 5:01:02 PM
 */
@Component(value="hiveSqlTemplateService")
@Scope("prototype")
public class HiveSqlTemplateServiceImpl implements HiveSqlTemplateService {
	
	private JdbcTemplate jdbcTemplate;
	private HiveConnectionBean hiveConnection;
	Logger log = Logger.getLogger(HiveSqlTemplateServiceImpl.class);

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public HiveConnectionBean getHiveConnectionBean() {
		return hiveConnection;
	}
	@Resource
	public void setHiveConnectionBean(HiveConnectionBean hiveConnection) {
		this.hiveConnection = hiveConnection;
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#createHiveSqlTemplate(org.hiveadmin.hive.beans.HiveSqlTemplateBean)
	 */
	@Override
	public void createHiveSqlTemplate(HiveSqlTemplateBean temp) throws Exception {
		if (temp.getTemp_name()==null || temp.getTemp_name().trim().length()<1){
			log.error("template name is null.");
			throw new Exception("template name is null.");
		}
		String sql=null;
		try{
			
			sql = "insert into template(temp_name,owner_name,temp_description,temp_content) values(?,?,?,?)";
			jdbcTemplate.update(sql, new Object[]{temp.getTemp_name(),temp.getOwner_name(),temp.getTemp_description(),temp.getTemp_content()});
		}catch(Exception e){
			log.error("catch exception when execute sql. [sql:"+sql+"][exception:"+e.getMessage()+"]");
			throw e;
		}
		
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#updataHiveSqlTemplate(org.hiveadmin.hive.beans.HiveSqlTemplateBean)
	 */
	@Override
	public void updataHiveSqlTemplate(HiveSqlTemplateBean temp) {
		String sql = "update template set owner_name=?,temp_description=?,temp_content=? where temp_name=?";
		try{
			jdbcTemplate.update(sql,new Object[]{temp.getOwner_name(),temp.getTemp_description(),temp.getTemp_content(),temp.getTemp_name()});
			log.info("update HiveSqlTemplate success. [temp_name:"+temp.getTemp_name()+"]");
		}catch(Exception e){
			log.error("update HiveSqlTemplate failed. [temp_name:"+temp.getTemp_name()+"][sql:"+sql+"]");
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#deleteHiveSqlTemplate(java.lang.String)
	 */
	@Override
	public void deleteHiveSqlTemplate(String temp_name) {
		String sql = "delete from template where temp_name=?";
		try{
			jdbcTemplate.update(sql, new Object[]{temp_name});
			log.info("delete HiveSqlTemplate failed. [temp_name:"+temp_name+"]");
		}catch(Exception e){
			log.error("delete HiveSqlTemplate failed. [temp_name:"+temp_name+"][sql:"+sql+"]");
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#getHiveSqlTemplateList(java.lang.String)
	 */
	@Override
	public List<HiveSqlTemplateBean> getHiveSqlTemplateList(String ownerName) {
		String sql = "select * from template where owner_name=?";
		try{
			log.info("success to get hiveSqlTemplate list. [owner:"+ownerName+"]");
			return (List<HiveSqlTemplateBean>) jdbcTemplate.query(sql, new Object[]{ownerName}, new HiveSqlTemplateRowMapper());
		}catch(Exception e){
			log.error("get HiveSqlTemplate failed. ["+ownerName+"]["+e.getMessage()+"][sql:"+sql+"]");
			throw e;
		}
	}

	/* (non-Javadoc)
	 * 执行template中保存的temp_content的内容.
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#executeHiveSqlTemplate(org.hiveadmin.hive.beans.HiveSqlTemplateBean)
	 */
	/*
	@Override
	public ResultSet executeHiveSqlTemplate(HiveSqlTemplateBean temp) throws Exception {
		try {
			if(temp.getTemp_content()==null || temp.getTemp_content().trim().length()<1){
				log.error("template content is null. do nothing");
				throw new Exception("template content is null. do nothing");
			}
			Connection conn = HiveConnectionBean.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(temp.getTemp_content());
			int colnum = res.getMetaData().getColumnCount();
			log.debug("====colnum:"+colnum);
			int i = 0;
			while(res.next()){
				for(i=0;i<colnum;i++){
					log.debug(res.getObject(i+1)+"\t");
				}
			}
			//conn.close();
			return res;
		} catch (Exception e) {
			log.error("execute hiveSqlTemplate failed. [template content:"+temp.getTemp_content()+"]");
			throw e;
		}
	}*/
	@Override
	public List executeHiveSqlTemplate(String database,HiveSqlTemplateBean temp) throws Exception {
		try {

			
			return HiveConnectionBean.executQuery(database,temp.getTemp_content());
			
		} catch (Exception e) {
			log.error("execute hiveSqlTemplate failed. [template content:"+temp.getTemp_content()+"]");
			throw e;
		}
	}
	/* (non-Javadoc)
	 * @see org.hiveadmin.hive.service.HiveSqlTemplateService#getHiveSqlTemplate(java.lang.String)
	 */
	@Override
	public HiveSqlTemplateBean getHiveSqlTemplate(String temp_name) {
		if(temp_name==null || temp_name.trim().length()<1){
			log.error("template name is null. do nothing");
			throw new RuntimeException("template name is null. do nothing");
		}
		String sql = "select * from template where temp_name=?";
		try{
			return (HiveSqlTemplateBean) jdbcTemplate.queryForObject(sql,new Object[]{temp_name},new HiveSqlTemplateRowMapper());

		}catch(Exception e){
			log.error("get Template info failed. [sql:"+sql+"][exception msg:"+e.getMessage()+"]");
			throw e;
		}
 	}
}
