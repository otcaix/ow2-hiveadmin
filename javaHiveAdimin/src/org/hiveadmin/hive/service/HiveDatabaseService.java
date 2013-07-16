package org.hiveadmin.hive.service;

import java.sql.ResultSet;
import java.util.Map;

/**
 * @ClassName HiveDatabaseService
 * @Description HiveDatabase操作类
 */
public interface HiveDatabaseService {
	public ResultSet listDatabase() throws Exception;
	public void createDatebase(String name,String comment,String hdfspath,Map<String, String> dbproperties) throws Exception;
	public void deleteDatebase(String name,String type) throws Exception;
}
