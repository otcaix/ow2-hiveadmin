<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login</title>
<!--   <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0"> -->
  <meta http-equiv="description" content="User Login">
  <link rel="stylesheet" href="<%=basePath%>public/css/loginstyle.css">
  <script src="<%=basePath %>public/js/jquery.js"></script>
  <script src="<%=basePath %>public/js/bootstrap.js"></script>
  <script type="text/javascript" src="<%=basePath %>public/js/hiveadmin.js"></script>
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
  <form class="login" method="post">
    <p>
      <label for="login">Email:</label>
      <input type="text" name="user.username" id="name" >
    </p>

    <p>
      <label for="password">Password:</label>
      <input type="password" name="user.userpass" id="password">
    </p>

    <p class="login-submit">
      <button  type="submit" class="login-button" id="submit" onclick="javascript:login();">Login</button>
    </p>
	 <div id="message"></div>
    <p class="forgot-password"><a href="help.jsp">Forgot your password?</a></p>
  </form>
  <jsp:include page="public/footer.jsp"></jsp:include>
</body>
</html>