package com.hiveadmin.example.beans;

public class UserExample {
	int id;
	String name;
	public UserExample(){}
	public UserExample(int id,String name){
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
