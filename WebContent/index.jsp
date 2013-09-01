<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script src="js/SpryTabbedPanels.js" type="text/javascript"></script>
	<base href=<%=request.getContextPath()%>/>
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
	<link href="css/SpryTabbedPanels.css" rel="stylesheet">
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
		
			<div id="content" class="span10">
			<!-- content starts -->
            <div>
                <ul class="breadcrumb">
                     <li>
                         <a href="#">Home</a> <span class="divider">/</span>
                     </li>
                </ul>
            </div>
			
			<div class="row-fluid">
				<div class="box span12">
					<div class="box-header well">
						<h2><i class="icon-info-sign"></i> Introduction</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<div id="TabbedPanels1" class="TabbedPanels">
								<ul class="TabbedPanelsTabGroup">
									<li class="TabbedPanelsTab" tabindex="0"><span>Background</span></li>
									<li class="TabbedPanelsTab" tabindex="0"><span>Motivation</span></li>
									<li class="TabbedPanelsTab" tabindex="0"><span>Architecture</span></li>
								</ul>
								<div class="TabbedPanelsContentGroup">
									<div class="TabbedPanelsContent">
										<img src="img/hiveadmin-logo.png">
										<p>&nbsp&nbsp&nbsp&nbspWe are experiencing an age of
											information explosion, the so-called "big data era", where
											industry, academia, and governments are accumulating data at
											an unprecedented speed. MapReduce is a distributed computing
											programming framework with unique merits of automatic job
											parallelism and fault tolerance, which provides an effective
											solution to the data analysis challenge. It has now become
											the de facto, as an effective approach to big data analytics
											in large cluster systems.</p>
										<p>&nbsp&nbsp&nbsp&nbspIn the MapReduce framework, a
											computation is represented by a MapReduce job. A job has two
											phases: the map function phase and the reduce function phase.
											The underlying run-time system executes the function in a way
											that it automatically partitions the output of the map
											function and copies it to the input of reduce function. Fu
											rthermore, a complex computation process can be represented
											by a chain of jobs. MapReduce does not allow arbitrary
											interfaces of the map and reduce function. Rather, their
											input and output must be based on key/value pairs. A map
											function accepts a key/value pair (k1, v1) and emits another
											key/value pair (k2, v2). After the map phase, the run-time
											system collects a list of values for each distinct key in the
											map output. Then, for each k2, a reduce function accepts the
											inputs of (k2, list (v2)), and emits (k3, list (v3)).
											MapReduce allows users to define the format of a key/value
											pair. It can be a simple scalar value (e.g. an integer or a
											string) or a complex composite object. In this way, it
											provides high flexibility to express computations and data
											processing operations in MapReduce jobs. Hadoop is an
											open-source implementation to MapReduce designed for clusters
											of many nodes. It provides a Hadoop Distributed File System
											(HDFS) as a global file system running on a cluster.</p>
										<P>&nbsp&nbsp&nbsp&nbsp Although this low-level hand
											coding offers a high flexibility in programming applications,
											it increases the difficulty in program debugging. High level
											declarative languages can greatly simplify the effort on
											developing applications in MapReduce without hand-coding
											programs. Recently, several SQL-like declarative languages
											and their translators have been built and integrated with
											MapReduce to support these languages. These systems include
											Pig Latin/Pig, SCOPE, and HiveQL/Hive. In practice, these
											languages play a more important role than hand-coded
											MapReduce programs. For example, more than 95% Hadoop jobs in
											Facebook are not hand-coded but generated by Hive.</P>
										<p>&nbsp&nbsp&nbsp&nbsp In order to evaluate an SQL query
											in MapReduce, the query must be represented into a chain of
											MapReduce jobs. The critical issue is that each operation
											(e.g. selection, aggregation, join) must be implemented into
											a transformation between input key/value pairs and output
											key/value pairs. It is straightforward to implement selection
											and projection. For aggregation with grouping, the columns
											for grouping can be the keys for data partitioning in the map
											phase, and the aggregation is finished in the reduce phase.
											For join between two data sets, an efficient way is that each
											data set is partitioned by its columns for join condition,
											and the join is finished in the reduce phase. In this way,
											each key/value pair produced by a map function should have a
											tag to indicate the source of the pair so that the following
											reduce can know where an input pair comes from.</p>
									</div>

									<div class="TabbedPanelsContent">
										<img src="img/hiveadmin-logo.png">
										<p>User Experience:</p>
										<p>&nbsp&nbsp&nbsp&nbspThough Hive facilitates programmers
											by providing a high level language, it is not friendly for
											common users. In the domain of relational databases, users
											are familiar with various management platforms. However, the
											situation is completely different with Hive. Users need to
											contact with Hive console to get the query result, leading to
											a poor user experience.</p>
										<p>User Privilege:</p>
										<p>&nbsp&nbsp &nbsp&nbspNo matter Hive or its execution
											layer Hadoop has no powerful security mechanism.
										<p>
										<p>Performance Profiling:</p>
										<p>&nbsp&nbsp&nbsp&nbspMetrics and logs from Hive and its
											execution layer Hadoop are separated. It lacks the historical
											and statistical view of performance. Moreover, it is really
											confusing for users when coming across a query bottleneck.</p>
										<p>Resource Monitoring:</p>
										<p>&nbsp&nbsp&nbsp&nbspDue to difficulties to show the
											detail of MapReduce execution, it is hard to get the resource
											evaluation, which is inherent in many RDBMSs.</p>

									</div>
									<div class="TabbedPanelsContent">
										<img src="img/hiveadmin-logo.png">
										<p>&nbsp&nbsp&nbsp&nbspHiveAdmin is a web-based management
											studio for Hive. It mainly contains four components. The
											fundamental one is the web-based Hive management studio that
											implements Hive Cli. It supports for most common operations,
											such as showing databases, listing tables, submitting queries
											and displaying execution information.</p>
										<p>&nbsp&nbsp&nbsp&nbspThe second one is user privilege
											manager which is in the charge of security administering for
											multi-users. These two components do not require a deep
											understanding in Hive. They just communicate with Hive
											through the Thrift protocol.</p>
										<p>&nbsp&nbsp&nbsp&nbspThe performance profiler displays a
											Hive query in a HiveQL operator layer and shows each step.
											Through this component, users can clearly figure out the cost
											in each step and find out the query bottlenecks.</p>
										<p>&nbsp&nbsp&nbsp&nbspThe resource monitor is the most
											complicated component in HiveAdmin. The execution layer for
											Hive is Hadoop; Situation differs from single node to a
											distributed environment. Monitoring the resource (including
											CPU, IO and memory) cost of the whole cluster is a great
											challenge.</p>
									</div>
								</div>
							</div>
	<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
					</div>
				</div>
			</div>
			</div>
		</div>
		</div>
		
	
	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<script src="js/SpryTabbedPanels.js"></script>
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
    