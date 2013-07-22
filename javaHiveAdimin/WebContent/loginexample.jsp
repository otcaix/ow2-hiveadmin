<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
</head>
<body>
    <h2>success</h2>
 	current user:<%=session.getAttribute("user")%><p>
 	<hr>
 	<h2> database list:</h2>
 	<s:iterator value="databaseList" id="s"> 
 		<s:property value="s"/>
 	</s:iterator>
 	<hr>
 	<h2>user operation history list:</h2>
 	<s:iterator value="historyRecordList"> 
 		<s:property value="op_record_id"/> : <s:property value="op_user_name"/>:<s:property value="op_desc"/>:<s:property value="op_sql"/>
 	</s:iterator><p>
 	<hr>
	<h2>HiveSqlTemplateService---fetchHiveSqlTemplate</h2>
	<s:property value="hiveSqlTemplateBean.temp_name"/>
	<s:property value="hiveSqlTemplateBean.owner_name"/>
	<s:property value="hiveSqlTemplateBean.temp_description"/>
	<s:property value="hiveSqlTemplateBean.temp_content"/>
	<s:property value="hiveSqlTemplateBean.temp_ts"/>
	<hr>

	<h2>HiveSqlTemplateService---fetchHiveSqlTemplateList</h2>
	<s:iterator value="hiveSqlTemplateList">
		<s:property value="temp_name"/>
		<s:property value="owner_name"/>
		<s:property value="temp_description"/>
		<s:property value="temp_content"/>
		<s:property value="temp_ts"/>	
	</s:iterator>
	<hr>
	<h2>HiveSqlTemplateService---executeHiveSqlTemplate</h2>
	<s:iterator value="executeHiveSqlTemplateResultset" id="res">
		
	</s:iterator>
	<hr>
	
	<s:debug/>
</body>
</html>
