<%--
  Created by IntelliJ IDEA.
  User: HSF2015
  Date: 2018/10/8
  Time: 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<html>
<head>
    <title>fileUpload</title>
</head>
<body>
    <form enctype="multipart/form-data" name="form1" action="/springmvc-demo/fileUpload/cmrFileUpload.action" method="post">
        <h1>采用流的方式上传文件</h1>
        <input type="file" name="file">
        <input type="submit" />
    </form>
     
    <form enctype="multipart/form-data" action="/springmvc-demo/fileUpload/transferTo" method="post">
        <h1>采用multipart提供的file.transfer方法上传文件</h1>
        <input type="file" name="file">
        <input type="submit" value="upload"/>
    </form>
     
    <form name="Form2" action="/springmvc-demo/fileUpload/springUpload" method="post" enctype="multipart/form-data">
        <h1>使用spring mvc提供的类的方法上传文件</h1>
        <input type="file" name="file">
        <input type="submit" value="upload"/>
    </form>
</body>
</html>
