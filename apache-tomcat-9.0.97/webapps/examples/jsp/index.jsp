<%--
  Created by IntelliJ IDEA.
  User: CC
  Date: 2024/11/12
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>index</title>
</head>
<body>
<%
  //接收是否登录成功数据
  String flag=request.getParameter("flag");
  if(flag==null){
    flag="";
  }
  if(flag.equals("sucessful")){
    String okname=(String)request.getAttribute("okname");
%>
<%=okname %>你好!/<a href="index.jsp">注销</a>
<%
}else{
%>
<a href="login.jsp">登录</a>/
<a href="reg.jsp">注册</a>
<%
  }
%>

</body>
</html>
