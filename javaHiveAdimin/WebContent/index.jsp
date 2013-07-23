<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link href="<%= basePath %>public/css/bootstrap.css" rel="stylesheet">
    <link href="<%= basePath %>public/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="<%= basePath %>public/css/custome.css" rel="stylesheet">
     <script type="text/javascript">
		function check()
		{
		   if(typeof(<%=session.getAttribute("user")%>)== 'undefined'||<%=session.getAttribute("user")%>==null)
		   {
		   alert("未登陆，请先登陆！");
		   window.location.href="<%=basePath%>login.jsp";
		   }
		   else
		   {
			   window.location.href="<%=basePath%>login.jsp";
		   }
		}
		check();
	</script> 
  </head>

  	<body>
	
	</body>
</html>