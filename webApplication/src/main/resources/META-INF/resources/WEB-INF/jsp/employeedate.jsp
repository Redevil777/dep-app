<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee by date</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>

<body>
<%@include file="head.jsp"%>
<h2>Employee by date</h2>
<br>
<br>
<table style="border: 1px solid; width: 500px; text-align:center" border="1">

    <tr>
        <td>№</td>
        <td>First name</td>
        <td>Last name</td>
        <td>Middle Name</td>
        <td>Salary</td>
        <td>Department</td>
    </tr>


    <c:forEach items="${employees}" var="employee">

        <tr>
            <td><c:out value="${employee.id}" /></td>
            <td><c:out value="${employee.firstName}" /></td>
            <td><c:out value="${employee.lastName}" /></td>
            <td><c:out value="${employee.middleName}" /></td>
            <td><c:out value="${employee.salary}" /></td>
            <td>
                <c:forEach items="${departments}" var="department">
                    <c:choose>
                        <c:when test="${department.id == employee.depId }">
                            <c:out value="${department.depName}" />
                        </c:when>
                    </c:choose>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>
<br><br>
<c:url var="back" value="/employee/all"/>
<p><a href="${back}">Back</a> </p>
<c:url var="mainUrl" value="/" />
<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>