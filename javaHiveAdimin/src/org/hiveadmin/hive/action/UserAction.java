package org.hiveadmin.hive.action;

import org.apache.struts2.json.annotations.JSON;
import org.hiveadmin.hive.beans.User;
import org.hiveadmin.hive.service.UserService;
import org.hiveadmin.hive.service.impl.UserServiceImpl;
@SuppressWarnings("serial")
public class UserAction extends BaseAction{
	private User user;
	private UserService userService = new UserServiceImpl();;
	@JSON(name="USER")
	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public String login(){
		System.out.println("username:"+this.user.getUsername()+" userpass:"+this.user.getUserpass());
		if (this.userService.getUserByName(this.user.getUsername()) != null) {
			this.user=null;
		} else {
			getSession().setAttribute("userid", this.user.getUserid());
			getSession().setAttribute("username", this.user.getUsername());
		}
		return SUCCESS;
	}
}
