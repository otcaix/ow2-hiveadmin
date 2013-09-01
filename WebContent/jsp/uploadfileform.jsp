<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="box span12">
	<div class="box-header well" data-original-title="">
		<h2><i class="icon-edit"></i> create database form</h2>
		<div class="box-icon">
			<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
			<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
		</div>
	</div>
	<div class="box-content">
		
		<s:form action="uploadfile" namespace="/hdfsOperation" enctype="multipart/form-data">
		  	<s:file name="upload" label="choose file"/><br/>
	
			<s:textfield name="remotepath" label="hdfs path"/>

			<s:submit class="btn btn-primary" value="upload"/>

		</s:form>
	</div>
</div>