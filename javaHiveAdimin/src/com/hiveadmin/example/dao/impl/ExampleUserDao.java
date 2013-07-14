package com.hiveadmin.example.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.dao.IUserExampleDao;
import com.hiveadmin.example.dao.rowmap.ExampleUserRowMapper;

import javax.annotation.*;
import javax.sql.DataSource;

@Component
public class ExampleUserDao implements  IUserExampleDao{
	@Resource
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(this.getClass());
	
	public JdbcTemplate getJdbc() {
		return jdbcTemplate;
	}
	
	@Resource(name="jdbcTemplate")
	public void setJdbc(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public UserExample getUser(Integer id) {
		String sql="select name from testUser where id="+id;
		DataSource ds = jdbcTemplate.getDataSource();
		log.info("dasource:"+ds.toString());
		log.info("===========get user");
		return (UserExample) jdbcTemplate.queryForObject(sql, new ExampleUserRowMapper());
	}

	@Override
	public void addUser(UserExample user) {
		log.info("ExampleUserDao:addUser");
		String sql = "insert into testuser(name) values('"+user.getName()+"')";
		log.info("sql:"+sql);
		if(jdbcTemplate==null)
			log.error("jdbcTemplate is null");
		else{
			try {
				log.info("do add user");
				
				jdbcTemplate.execute(sql);
			} catch (Exception e) {
				log.info("add user exception. msg:"+e.getMessage());
				throw e;
			}
		}
	}

}
