<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Users</h2>
    <hr>
    <h3>Current user = ${param.selectUser == '1' ? 'Admin' : 'User'}</h3>
    <h3><a href="meals">Meals</a></h3>
</body>
</html>