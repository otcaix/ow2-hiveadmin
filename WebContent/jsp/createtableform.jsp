<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type='text/javascript' src='../js/jquery.js'></script>
 <script type='text/javascript' src='../js/addcolumn.js'></script> 	
	<base href=<%=request.getContextPath() %>/>
	<meta charset="utf-8">
	<title>HiveAdmin</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
	<meta name="author" content="Muhammad Usman">

	<!-- The styles -->
	<link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="css/bootstrap-responsive.css" rel="stylesheet">
	<link href="css/charisma-app.css" rel="stylesheet">
	<link href="css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='css/fullcalendar.css' rel='stylesheet'>
	<link href='css/fullcalendar.print.css' rel='stylesheet'  media='print'>
	<link href='css/chosen.css' rel='stylesheet'>
	<link href='css/uniform.default.css' rel='stylesheet'>
	<link href='css/colorbox.css' rel='stylesheet'>
	<link href='css/jquery.cleditor.css' rel='stylesheet'>
	<link href='css/jquery.noty.css' rel='stylesheet'>
	<link href='css/noty_theme_default.css' rel='stylesheet'>
	<link href='css/elfinder.min.css' rel='stylesheet'>
	<link href='css/elfinder.theme.css' rel='stylesheet'>
	<link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
	<link href='css/opa-icons.css' rel='stylesheet'>
	<link href='css/uploadify.css' rel='stylesheet'>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="img/favicon.ico">
	
</head>
<body>
	<jsp:include page="/jsp/top_nav.jsp" />
	<div class="container-fluid">
		<div class="row-fluid">

			<jsp:include page="/jsp/left_menu.jsp" />

			<noscript>
				<div class="alert alert-block span9">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href=<%=request.getContextPath()%>>Home</a> <span
							class="divider">/</span> <a href="#">tables</a></li>
					</ul>
				</div>


				<div class="row-fluid" id="tablelistcontainer">
					<div class="box span12">
						<div class="box-header well">

							<h2>
								<i class="icon-info-sign"></i> Table form
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>

						<div class="box-content">
							<p class="btn-group">
									<a class="btn btn-small btn-primary" href='tableaction/tablelist?database_name=<s:property value="%{#parameters.database_name}"/>'>
											<i class="icon-arrow-left icon-white"></i>  
											back                                            
										</a>
							</p>
							<form class="span9"
								action='<%=request.getContextPath() + "/tableaction/create"%>'>
								<fieldset>
									<legend>Columns</legend>
									<s:text name="table name" />
									<s:textfield name="table_name" />
									<br>
									<s:text name="Table type" />
									<s:select list="#{'true':'External'}" name="external"
										listKey="key" listValue="value" headerKey="false"
										headerValue="Defualt"></s:select>
									<br>
									<s:text name="Database name" />
									<s:textfield name="database_name"
										value="%{#parameters.database_name}" readonly="true" />
									<br>

									<s:text name="table comment" />
									<s:textfield name="table_comment" />
									<br>
									<s:text name="if database is not exist, create it" />
									<s:select list="#{'false':'False'}" name="if_not_exists"
										listKey="key" listValue="value" headerKey="true"
										headerValue="True"></s:select>

								</fieldset>
								<p>
								<fieldset class="form-inline" id="column">
									<legend>Fields</legend>
									<div id="cols">
										<div align="right" style="display:none">
											
											<p id="column_num">1</p>
										</div>
										<s:text name="Column name" />
										<s:textfield id="cols_name" name="columns[0].cols_name" />
										<br>
										<s:text name="Column type" />
										<s:select id="cols_type" list="#{'int':'Int'}"
											name="columns[0].cols_type" listKey="key" listValue="value"
											headerKey="String" headerValue="String"></s:select>
										<s:text name="Column comment" />
										<s:textfield id="cols_comment" name="columns[0].cols_comment" />
										<br>
										<div id="cols_-1" style="display: none">
											<s:text name="Column name" />
											<s:textfield id="cols_name" />
											<br>
											<s:text name="Column type" />
											<s:select id="cols_type" list="#{'int':'Int'}" listKey="key"
												listValue="value" headerKey="String" headerValue="String"></s:select>
											<s:text name="Column comment" />
											<s:textfield id="cols_comment" />
											<br>
										</div>
									</div>
                                 <div align="right"></div> <input type="button" class=" btn btn-small btn-warning"id="newColumn" value="New Column" /> 
									
										<input type="button" class="btn btn-small btn-info" id="removeColumn" value="Remove Column" />
								</fieldset>
								<p>
								 <fieldset class="form-inline">
									<legend>Partitioned by</legend>
									<div id="P_cols" style="display:none">
										<p id="P_column_num" align="left">0</p>
										
										<div id="P_cols_-1" style="display: none">
											<s:text name="Column name" />
											<s:textfield id="cols_name" />
											<br>
											<s:text name="Column type" />
											<s:select id="cols_type" list="#{'int':'Int'}" listKey="key"
												listValue="value" headerKey="String" headerValue="String"></s:select>

											<s:text name="Column comment" />
											<s:textfield id="cols_comment" />
											<br>

										</div>
									</div>

									<input type="button" id="newP_Column" class=" btn btn-small btn-warning" value="New Column" /> <input
										type="button" id="removeP_Column" class="btn btn-small btn-info"  value="Remove Column" />
								</fieldset> 
								<p>
				
								<fieldset class="form-inline">
									<legend>Buckets number</legend>
									<s:text name="Buckets number" />
									<s:textfield name="num_buckets" maxlength="100" />
								</fieldset>

								<p>
								<fieldset class="form-horizontal">
									<legend>Location</legend>
									<s:text name="HDFS path" />
									<s:textfield name="hdfs_path" maxlength="100" />
								</fieldset>

								<p>
								<div class="form-actions">
									<button class="btn btn-small btn-primary" type="submit"><i class="icon-ok icon-white"></i>Create </button>
									<button class="btn">cancel</button>
								</div>

							</form>

						</div>
					</div>
				</div>
				
			</div>
		</div>
	<jsp:include page="/jsp/foot.html" />
	</div>
	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="js/excanvas.js"></script>
	<script src="js/jquery.flot.min.js"></script>
	<script src="js/jquery.flot.pie.min.js"></script>
	<script src="js/jquery.flot.stack.js"></script>
	<script src="js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
</body>
</html>
