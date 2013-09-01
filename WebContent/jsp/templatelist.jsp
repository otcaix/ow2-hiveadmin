<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>


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
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			<script type="text/javascript">
				function temp_create(){
					$("#temp_op_container").load("jsp/templateeditform.jsp");
	
				}
			</script>
			<div id="content" class="span10">
			<!-- content starts -->
            <div>
                <ul class="breadcrumb">
                     <li>
                         <a href=<%=request.getContextPath() %>>Home</a> <span class="divider">/</span>
                         <a href="#">Template List</a>
                     </li>
                </ul>
            </div>
			
			<div id="temp_op_container" class="row-fluid">
				<div class="box span12">
					<div class="box-header well">
						<h2><i class="icon-info-sign"></i> Template list</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">	
							<div class="box-content">
								<button id="temp_create_btn" onclick="temp_create()" class="btn btn-mini btn-primary">create template</button>							
							</div>
							
							<table class="table table-striped table-bordered bootstrap-datatable datatable">
							  <thead>
								  <tr>
									  <th>TemplateName</th>
									  <th>Description</th>
									  <th>Owner</th>
									  <th>content</th>
									  <th>TimeStamp</th>
								  </tr>
							  </thead>   
							  <tbody>
							  <s:iterator value="hiveSqlTemplateList" id="status"> 
								<tr>
									<td class="temp_name">
										<s:property value="temp_name"/>
									</td>
									<td class="temp_desc"><s:property value="temp_description"/></td>
									<td class="owner_name"><s:property value="owner_name"/></td>
									<td class="temp_content"><s:property value="temp_content"/></td>
									<td><s:property value="temp_ts"/></td>
									<td class="centre">
										<a class="btn btn-danger" href=<%=request.getContextPath()+"/hiveSqlTemplate/deleteHiveSqlTemplate/?hiveSqlTemplateBean.temp_name="%><s:property value="temp_name"/>>
											<i class="icon-trash icon-white"></i> 
											Delete
										</a>
										<button class="temp_edit_btn btn btn-success">
											<i class="icon-zoom-in icon-edit"></i>  
											Edit or execute                                           
										</button>
									</td>
								</tr>
								</s:iterator>
							  </tbody>
						  </table>
						  
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
	
	<script type="text/javascript">
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		}
		$(function(){
			
			$(".temp_edit_btn").click(function(){

				var edit_temp_name=$(this).parent().parent().find(".temp_name").text();
				var edit_temp_desc=$(this).parent().parent().find(".temp_desc").text();
				var edit_temp_content=$(this).parent().parent().find(".temp_content").text();
				
				$("#temp_op_container").load("jsp/templateeditorexeform.jsp",function(){
					$("#focusedInput1").val(trim(edit_temp_name));					
					$("#focusedInput2").val(trim(edit_temp_desc));
					$("#focusedInput3").val(trim(edit_temp_content));
				});	
				
			});
			
			
		});
	</script>
</body>
</html>
    