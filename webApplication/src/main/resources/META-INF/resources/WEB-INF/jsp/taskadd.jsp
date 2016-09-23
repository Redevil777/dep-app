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
    <c:url var="addUrl" value="/task/add/${empId}" />
    <%@include file="head.jsp"%>
    <form:form modelAttribute="task" method="POST" action="${addUrl}">
        <table>
            <tr>
                <td><form:label path="title">Title:</form:label></td>
                <td><form:input path="title"/></td>
            </tr>
            <tr>
                <td><form:label path="taskType">Type:</form:label></td>
                <td>
                    <select name="taskType">
                        <option value="CALL">CALL</option>
                        <option value="MEET">MEET</option>
                        <option value="OTHER">OTHER</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><form:label path="description">Description:</form:label></td>
                <td><form:textarea path="description"/></td>
            </tr>
            <tr>
                <td><form:label path="dateWhen">Date of end:</form:label></td>
                <td><form:input path="dateWhen" data-format="yyyy-MM-dd" type="date"/></td>
            </tr>
            <tr>
                <td><form:label path="priority">Priority:</form:label></td>
                <td>
                    <select name="priority">
                        <option value="HIGH">HIGH</option>
                        <option value="MEDIUM">MEDIUM</option>
                        <option value="LOW">LOW</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="Add">
    </form:form>
</div>
</body>
</html>
