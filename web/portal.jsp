<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>图书馆后台</title>
</head>
<body>
<h1>图书馆后台</h1>

<script language="javascript" type="text/javascript">
    function manageStudent(){
        document.location.href = "stuMng.jsp";
    }
    function manageBook(){
        document.location.href = "bookMng.jsp";
    }
    function manageLog(){
        document.location.href = "BRlog.jsp";
    }
</script>

<p>
    <input type="button" name="student" value="学生管理" onclick="manageStudent()">
</p>
<p>
    <input type="button" name="book" value="图书管理" onclick="manageBook()">
</p>
<p>
    <input type="button" name="log" value="借还记录" onclick="manageLog()">
</p>
</body>
</html>
