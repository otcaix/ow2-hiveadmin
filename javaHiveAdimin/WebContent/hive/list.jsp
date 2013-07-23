<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"><head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>JavaHiveAdmin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="./public/css/bootstrap.css" rel="stylesheet">
    <link href="./public/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="./public/css/custome.css" rel="stylesheet">
  </head>

  <body>
	<jsp:include page="../public/header.jsp"></jsp:include>
   <div class="container-fluid">
			<div class="row-fluid">
				<div class="span3">
					<s:iterator value="databaseList">
					  <div class="row">
					  	<s:property></s:property>
					  </div>
					</s:iterator>
				</div>
				<div class="span9">
					<table class="table">
						<thead>
							<tr>
								<th>
									编号
								</th>
								<th>
									产品
								</th>
								<th>
									交付时间
								</th>
								<th>
									状态
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									1
								</td>
								<td>
									TB - Monthly
								</td>
								<td>
									01/04/2012
								</td>
								<td>
									Default
								</td>
							</tr>
							<tr class="success">
								<td>
									1
								</td>
								<td>
									TB - Monthly
								</td>
								<td>
									01/04/2012
								</td>
								<td>
									Approved
								</td>
							</tr>
							<tr class="error">
								<td>
									2
								</td>
								<td>
									TB - Monthly
								</td>
								<td>
									02/04/2012
								</td>
								<td>
									Declined
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
    <jsp:include page="../public/footer.jsp"></jsp:include>
</body>
</html>