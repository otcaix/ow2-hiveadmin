<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
</head>
<body>
    <h1>success</h1>
 	current user:<%=session.getAttribute("user")%><p>
 	database list:<p>
 	<s:iterator value="databaseList" id="s"> 
 		<s:property value="s"/>
 	</s:iterator>
 	
 	user history list:<p>
 	<s:iterator value="historyRecordList"> 
 		<s:property value="op_record_id"/> : <s:property value="op_user_name"/>:<s:property value="op_desc"/>:<s:property value="op_sql"/>
 	</s:iterator><p>
</body>
</html>
