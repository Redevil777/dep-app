<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee search</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
<h2>You choose date between ${from} and ${to} </h2>

<table style="border: 1px solid; width: 500px; text-align:center" border="1">

    <tr>
        <td>№</td>
        <td>First name</td>
        <td>Last name</td>
        <td>Middle Name</td>
        <td>Birthday</td>
        <td>Salary</td>
        <td>Department</td>
    </tr>


    <c:forEach items="${employees}" var="employee">

        <tr>
            <td><c:out value="${employee.id}" /></td>
            <td><c:out value="${employee.firstName}" /></td>
            <td><c:out value="${employee.lastName}" /></td>
            <td><c:out value="${employee.middleName}" /></td>
            <td><c:out value="${employee.birthday}" /></td>
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
<br>
<c:url var="mainUrl" value="/" />
<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>
