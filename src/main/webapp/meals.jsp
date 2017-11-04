<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .redtext{ color: red; }
        .greentext{ color: green; }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="1" cellspacing=5 cellpadding=5>
    <tr>
        <td>DATE</td>
        <td>DESCRIPTION</td>
        <td>CALORIES</td>
    </tr>
    <jsp:useBean id="mealWithExceeds" scope="request" type="java.util.List"/>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach items="${mealWithExceeds}" var="meal">
        <tr class="${meal.exceed ? "redtext" : "greentext"}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
