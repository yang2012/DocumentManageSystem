<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>Struts2 File Upload</title>
</head>
<body>
<form action="/document/upload" method="POST" enctype="multipart/form-data">
    选择文件：<input type="file" name="upload" size="50"/><br/>
    <input type="submit" value=" 上传 "/>
</form>
</body>
</html> 