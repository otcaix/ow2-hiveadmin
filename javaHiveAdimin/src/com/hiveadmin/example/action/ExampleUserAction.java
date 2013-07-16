package com.hiveadmin.example.action;
import org.apache.hadoop.hive.jdbc.HiveDriver;
import org.apache.hadoop.hive.jdbc.HiveDataSource;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hive.service.HiveDatabaseService;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.service.ExampleUserService;
import com.hiveadmin.example.service.impl.ExampleUserServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
@Component
public class ExampleUserAction extends ActionSupport{

	private String msg;
	private ExampleUserService exampleUserService;
	private HiveDatabaseService hiveDatabaseService;
	Logger log = Logger.getLogger(this.getClass());
	
	
	public ExampleUserService getExampleUserService() {
		return exampleUserService;
	}
	@Resource(name="exampleUserServiceImpl")
	public void setExampleUserService(ExampleUserService exampleUserService) {
		this.exampleUserService = exampleUserService;
	}
	@Resource(name="hiveDatabaseServiceImpl")
	public void setHiveDatabaseService(HiveDatabaseService hiveDatabaseService){
		this.hiveDatabaseService = hiveDatabaseService;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String login() throws Exception {
		
		
		msg="hello wangjie";
		try {
			//exampleUserService.register(new UserExample(1,"liguyi"));
			hiveDatabaseService.createDatebase("xxxxx", null, null, null);
			//hiveDatabaseService.listDatabase();
		}catch(Exception e){
			log.error("catch exception when regist user. msg:"+e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
}
