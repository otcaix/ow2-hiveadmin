package com.hiveadmin.example.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.dao.impl.ExampleUserDao;
import com.hiveadmin.example.service.ExampleUserService;

@Component
public class ExampleUserServiceImpl implements ExampleUserService{

	private ExampleUserDao exampleUserDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public ExampleUserDao getExampleUserDao() {
		return exampleUserDao;
	}
	@Resource
	public void setExampleUserDao(ExampleUserDao exampleUserDao) {
		this.exampleUserDao = exampleUserDao;
	}

	@Override
	public void login(UserExample user) {
		
	}

	@Override
	public void register(UserExample user) {
		log.info("=========ExampleUserServiceImpl:register");
		if(exampleUserDao==null){
			log.error("====exampleUserDao is null.");
		}
		
		else {
			exampleUserDao.addUser(user);
		}
	}

}
