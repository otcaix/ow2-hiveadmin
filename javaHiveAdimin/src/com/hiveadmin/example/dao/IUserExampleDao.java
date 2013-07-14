package com.hiveadmin.example.dao;

import com.hiveadmin.example.beans.UserExample;

public interface IUserExampleDao {
	public UserExample getUser(Integer id);
	public void addUser(UserExample user);

}
