package com.hiveadmin.example.action;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.service.ExampleUserService;
import com.hiveadmin.example.service.impl.ExampleUserServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
@Component
public class ExampleUserAction extends ActionSupport{

	private String msg;
	private ExampleUserService exampleUserService;
	Logger log = Logger.getLogger(this.getClass());
	
	
	public ExampleUserService getExampleUserService() {
		return exampleUserService;
	}
	@Resource(name="exampleUserServiceImpl")
	public void setExampleUserService(ExampleUserService exampleUserService) {
		this.exampleUserService = exampleUserService;
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
			exampleUserService.register(new UserExample(1,"liguyi"));
		}catch(Exception e){
			log.error("catch exception when regist user.");
			return ERROR;
		}
		return SUCCESS;
	}
}
