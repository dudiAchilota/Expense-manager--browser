<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Error Message</h1>
<%=  request.getAttribute("errormessage").toString() %>
</body>
</html>
