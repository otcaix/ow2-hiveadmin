<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>HiveSqlTemplateService---createHiveSqlTemplate</h2>
<s:form action="createHiveSqlTemplate" namespace="/hiveSqlTemplate">
	<s:textfield label="template name" name="hiveSqlTemplateBean.temp_name"></s:textfield>
	<s:textfield label="template description" name="hiveSqlTemplateBean.temp_description"></s:textfield>
	<s:textfield label="tmplate content" name="hiveSqlTemplateBean.temp_content"></s:textfield>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>HiveSqlTemplateService---deleteHiveSqlTemplate</h2>
<s:form action="deleteHiveSqlTemplate" namespace="/hiveSqlTemplate">
	<s:textfield label="template name:" name="hiveSqlTemplateBean.temp_name"/>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>HiveSqlTemplateService---updateHiveSqlTemplate</h2>
<s:form action="updateHiveSqlTemplate" namespace="/hiveSqlTemplate">
	<s:textfield label="template name:" name="hiveSqlTemplateBean.temp_name"/>
	<s:textfield label="template description" name="hiveSqlTemplateBean.temp_description"></s:textfield>
	<s:textfield label="tmplate content" name="hiveSqlTemplateBean.temp_content"></s:textfield>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>HiveSqlTemplateService---fetchHiveSqlTemplate</h2>
<s:form action="fetchHiveSqlTemplate" namespace="/hiveSqlTemplate">
	<s:textfield label="template name:" name="hiveSqlTemplateBean.temp_name"/>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>HiveSqlTemplateService---fetchHiveSqlTemplateList</h2>
<s:form action="fetchHiveSqlTemplateList" namespace="/hiveSqlTemplate">
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>HiveSqlTemplateService---executeHiveSqlTemplate</h2>
<s:form action="executeHiveSqlTemplate" namespace="/hiveSqlTemplate">
	<s:textfield label="template name" name="hiveSqlTemplateBean.temp_name" value="temp_4"></s:textfield>
	<s:textfield label="template description" name="hiveSqlTemplateBean.temp_description" value="seletc"></s:textfield>
	<s:textfield label="tmplate content" name="hiveSqlTemplateBean.temp_content"></s:textfield>
	<s:submit value="submit"/>
</s:form>

<hr>
<s:debug/>
</body>
</html>