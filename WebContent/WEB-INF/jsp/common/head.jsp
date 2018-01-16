<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="bootstrap/js/jquery/2.0.0/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap/3.3.6/bootstrap.min.css"
	rel="stylesheet">
<script src="bootstrap/js/bootstrap/3.3.6/bootstrap.min.js"></script>
<script src="bootstrap/js/myjs.js"></script>
<link href="bootstrap/css/mycss.css" rel="stylesheet">



<!-- 

静态资源必须放在WebContent下面
不能放在WEB-INF下面

因为WEB-INF下面的文件夹，tomcat做了安全访问的问题。
只有服务器才可以访问，客户端是没有权限访问的

就算在springmvc文件中配置了

servletMapping 映射路径： "/" 
 由于拦截了所有url，配置静态资源 
<mvc:default-servlet-handler />

也是没有用的

所以记住必须放在WebContent下面

-->





