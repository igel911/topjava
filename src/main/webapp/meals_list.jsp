<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Meals</title>
    <style>
        .redtext{ color: red; }
        .greentext{ color: green; }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<p><a href="meals?action=add">Add Meal</a></p>
<table border="1" cellspacing=5 cellpadding=5>
    <tr>
        <th>DATE</th>
        <th>DESCRIPTION</th>
        <th>CALORIES</th>
        <th colspan="2">ACTION</th>
    </tr>
    <jsp:useBean id="mealsWithExceed" scope="request" type="java.util.List"/>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach items="${mealsWithExceed}" var="meal">
        <tr class="${meal.exceed ? "redtext" : "greentext"}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&mealId=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
