<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="box span12">
	<div class="box-header well" data-original-title="">
		<h2><i class="icon-edit"></i>mkdir form</h2>
		<div class="box-icon">
			<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
			<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
		</div>
	</div>
	<div class="box-content">
		<form class="form-horizontal" action='<%=request.getContextPath()+"/hdfsOperation/mkdirs"%>'>
			<fieldset>
			  <div class="control-group">
				<label class="control-label" for="focusedInput">dir path</label>
				<div class="controls">
				  <input class="input-xlarge focused" name="dirName" id="hdfs_default_filepath" type="text" value=''>
				</div>
			</div>
			<div class="form-actions">
				<button class="btn btn-primary" type="submit">
					create
				</button>
				<a href="<%=request.getContextPath() %>/hdfsOperation/listFileStatus" class="btn">
					cancel
				</a>
			</div>
			</fieldset>
		 </form>
	
	</div>
</div>