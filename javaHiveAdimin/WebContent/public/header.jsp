<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  
<div class="row-fluid">
		<div class="span12">
			<div class="navbar navbar-inverse navbar-fixed-top">
				<div class="navbar-inner">
					<div class="container-fluid">
						<a class="btn btn-navbar" data-target=".navbar-responsive-collapse" data-toggle="collapse"></a> <a class="brand" href="#">JavaHiveAdmin</a>
						<div class="nav-collapse collapse navbar-responsive-collapse">
							<ul class="nav" style="text-shadow: 0 1px #B4CDE6;">
								<li class="active">
									<a href="<%=basePath%>hdbList.action">Hive查询</a>
								</li>
								<li>
									<a href="<%=basePath%>hdfsList.action">HDFS浏览</a>
								</li>
								<li>
									<a href="<%=basePath%>hqltList.action">HiveQL模板</a>
								</li>
							</ul>
						</div>
						
					</div>
				</div>
				
			</div>
		</div>
	</div>