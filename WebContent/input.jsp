<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="asset/js/jquery-1.10.1.js"></script>
</head>
<body>
<h2>HDFS upload---------/hdfsOperation/uploadfile</h2>
<s:form action="uploadfile" namespace="/hdfsOperation" enctype="multipart/form-data" method="post">
	<s:file name="upload" label="choose file"/><br/>
	<s:textfield name="remotepath" label="hdfs path"/>
	<s:submit value="upload"/>
</s:form>

<hr>
<h2>HDFS download---------/hdfsOperation/downloadfile</h2>
<s:form action="downloadfile" namespace="/hdfsOperation">

	<s:textfield name="remotepath" label="hdfs path"/>
	<s:submit value="download"/>
</s:form>

<hr>
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
	<s:textfield label="tmplate content" name="hiveSqlTemplateBean.temp_content"></s:textfield>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>Hivecli--------------cliQuery</h2>
<s:form action="hiveCliQuery" namespace="/hivecli">
	<s:textfield label="database" name="database" value="default"></s:textfield>
	<s:textfield label="sql" name="sql" value="select * from userinfo1"></s:textfield>
	<s:submit value="submit"/>
</s:form>
<hr>
<h2>getQueryStatus--------------getQueryStatus===执行并查看状态</h2>
<script type="text/javascript">
	var lastRead = 0;
	function hivecliquery(){
		var hivecliqueryurl = "/javaHiveAdimin/hivecli/hiveCliQuery";
		var databaseParam=$("#getQueryStatus_database").val();
		var sqlParam = $("#getQueryStatus_sql").val();
		$.ajax({
			url:hivecliqueryurl,
			type:"POST",
			data:{database:databaseParam,sql:sqlParam},
			error:function(){
				alert("failed to do hivecli query");
			}
			
		});
		lastRead=0;
	}
	
	function freshstatus(){
		
		var getStatusUrl="/javaHiveAdimin/hivecli/getQueryStatus";
		
		$.ajax({
			url:getStatusUrl,
			type:'POST',
			dataType:'text',
			data:{lastReadSize:lastRead},
			timeout:1000,
			error:function(){
				//alert("error while loading status");
			},
			success:function(resData){
				if(resData==null || resData=="" ||resData=="null"){
					return;
				}
				resLen = resData.length;
				lastRead = lastRead+resLen;
				var newtext = $("#statusArea").html()+resData.replace("\n","<br>");
				$("#statusArea").html(newtext);
			}
			
		});
	}
	function previewResult(){
		alert("preview");
		var getResultUrl="/javaHiveAdimin/hivecli/getResult";
		$.ajax({
			url:getResultUrl,
			type:'POST',
			dataType:'text',
			error:function(){
				
			},
			success:function(resData){
				$("#resultpreview").html(resData);
			}
		})
	}
</script>
<form id="getQueryStatusForm">
	database:<input type="text" id="getQueryStatus_database"  name="database" value="default"/>
	sql:<input type="text" id="getQueryStatus_sql"  name="sql" value="select * from userinfo1"/>
	<input type="submit" id="status_submit" onclick="hivecliquery();setInterval(freshstatus,1500);return false" value="submit"/>
	<input type="submit" id="result_submit" onclick="previewResult();return false" value="preview"/>
</form>
<h3>##########show status############</h3>
<div id="statusArea">
</div>
<h3>#############show result##########</h3>
<div id="resultpreview"/>
<hr>
hahha
<s:debug/>
</body>
</html>