<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
    <script type="text/javascript">
        var i18n = [];
        i18n["addTitle"] = '<spring:message code="${param.addTitle}"/>';
        i18n["editTitle"] = '<spring:message code="${param.editTitle}"/>';
        <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus"}%>'>
            i18n["${key}"] = "<spring:message code="${key}"/>";
        </c:forEach>
    </script>
</div>

