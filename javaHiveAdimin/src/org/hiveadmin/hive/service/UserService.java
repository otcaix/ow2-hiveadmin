package org.hiveadmin.hive.service;

import org.hiveadmin.hive.beans.User;

public interface UserService {
	public User getUserByName(String username);
}
