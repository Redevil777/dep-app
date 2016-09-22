<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<div>
    <%@include file="head.jsp"%>
    <form:form modelAttribute="task" method="get">
        <table>
            <tr>
                <td><form:label path="title">Title:</form:label></td>
                <td><form:input path="title"/></td>
            </tr>
            <tr>
                <td><form:label path="taskType">Type:</form:label></td>
                <td><form:input path="taskType" readonly="true"/></td>
            </tr>
            <tr>
                <td><form:label path="description">Description</form:label></td>
                <td><form:textarea path="description" readonly="true"/></td>
            </tr>
            <tr>
                <td><form:label path="dateWhen">End date:</form:label></td>
                <td><form:input data-format="yyyy-MM-dd" type="date" path="dateWhen" value="${task.dateWhen}" readonly="true"/></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
