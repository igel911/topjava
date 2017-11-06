<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>Meal</title>
</head>
<body>
    <form method="POST" action='meals'>
        ID : <input type="text" readonly="readonly" name="mealId" value="${meal.id}" /> <br />
        DATE : <input type="datetime-local" name="dateTime" value="${meal.dateTime}" /> <br />
        DESCRIPTION : <input type="text" name="description" value="${meal.description}" /> <br />
        CALORIES : <input type="text" name="calories" value="${meal.calories}" /> <br />
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
