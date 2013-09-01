			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class="nav-header hidden-tablet">Hive Database Manage</li>
						<li><a class="ajax-link" href=<%=request.getContextPath()+"/databaseAction/getDatabaseList"%>><span class="hidden-tablet">Databases and tables</span></a></li>
						
						<li><a class="ajax-link" href=<%=request.getContextPath()+"/jsp/hivecliquery.jsp"%>><span class="hidden-tablet">hive sqls</span></a></li>
						
						<li class="nav-header hidden-tablet">HDFS Manage</li>
						<li><a class="ajax-link" href=<%=request.getContextPath()+"/hdfsOperation/listFileStatus" %>><span class="hidden-tablet">File Explorerer</span></a></li>
						<li class="nav-header hidden-tablet">Template Manage</li>
						<li><a class="ajax-link" href="<%=request.getContextPath()+"/hiveSqlTemplate/fetchHiveSqlTemplateList" %>"><span class="hidden-tablet">Template List</span></a></li>
												
						<li class="nav-header hidden-tablet">User History Manage</li>
						<li><a class="ajax-link" href="<%=request.getContextPath()+"/userHistory/getUserHistoryRecordList" %>"><span class="hidden-tablet">HistoryRecords</span></a></li>
						<li class="nav-header hidden-tablet">User Manage</li>
						<li><a class="ajax-link" href="#"><span class="hidden-tablet">UserList</span></a></li>
					</ul>
				</div><!--/.well -->
			</div><!--/span-->
			<!-- left menu ends -->