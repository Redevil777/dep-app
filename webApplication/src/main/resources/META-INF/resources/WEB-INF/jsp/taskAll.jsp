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
    <script>
        var show;
        function hidetxt(type){
            param=document.getElementById(type);
            if(param.style.display == "none") {
                if(show) show.style.display = "none";
                param.style.display = "block";
                show = param;
            }else param.style.display = "none"
        }
    </script>
</head>
<body>
<div>
    <%@include file="head.jsp"%>
    <table style="border: 1px solid; width: 500px; text-align:center" border="1">
        <tr>
            <td>№</td>
            <td>Type</td>
            <td>Title</td>
            <td>Description</td>
            <td>Date</td>
            <td>Employee</td>
            <td>Complete</td>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td>Delete</td>
                <td>Edit</td>
            </sec:authorize>
        </tr>
        <c:forEach items="${tasks}" var="task">
            <c:url var="deleteUrl" value="/task/delete/${task.id}" />
            <c:url var="editUrl" value="/task/edit/${task.id}" />
            <tr>
                <td><c:out value="${task.id}"/></td>
                <td><c:out value="${task.taskType}"/></td>
                <td><c:out value="${task.title}"/></td>
                <td><c:out value="${task.description}"/></td>
                <td><c:out value="${task.dateWhen}"/></td>
                <c:forEach items="${employees}" var="employee">
                    <c:choose>
                        <c:when test="${employee.id == task.empId}">
                            <td><c:out value="${employee.lastName}"/></td>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${task.complete==false}">
                        <td><c:out value="Not yet"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="Yes"/> </td>
                    </c:otherwise>
                </c:choose>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <td>
                            <a href="${editUrl}">Edit</a>
                        </td>
                        <td>
                            <a href="${deleteUrl}">Delete</a>
                        </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
