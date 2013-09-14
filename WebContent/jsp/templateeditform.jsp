<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="box span12">
	<div class="box-header well" data-original-title="">
		<h2><i class="icon-edit"></i> Edit template form</h2>
		<div class="box-icon">
			<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
			<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
		</div>
	</div>
	<div class="box-content">
	
		<form class="form-horizontal" action='<%=request.getContextPath()+"/hiveSqlTemplate/createHiveSqlTemplate"%>'>
			<fieldset>
			  <div class="control-group">
				<label class="control-label" for="focusedInput">template name</label>
				<div class="controls">
				  <input class="input-xlarge focused" name="hiveSqlTemplateBean.temp_name" id="focusedInput" type="text">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="focusedInput2">description</label>
				<div class="controls">
				  <input class="input-xlarge focused" name="hiveSqlTemplateBean.temp_description" id="focusedInput2" type="text">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="focusedInput3">sql</label>
				<div class="controls">
				  <textarea name="hiveSqlTemplateBean.temp_content" id="focusedInput3" rows="10" cols="150"/>
				</div>
			</div>
			<div class="form-actions">
				<button class="btn btn-primary" type="submit">
					create
				</button>
				<a href="<%=request.getContextPath() %>/hiveSqlTemplate/fetchHiveSqlTemplateList" class="btn">
					cancel
				</a>
			</div>
			</fieldset>
		 </form>
	
	</div>
</div>