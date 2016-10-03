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
    <c:url var="saveUrl" value="/task/edit" />
    <form:form modelAttribute="task" method="post" action="${saveUrl}">
        <table>
            <tr>
                <td><form:label path="id">Id:</form:label></td>
                <td><form:input path="id"/></td>
            </tr>
            <tr>
                <td><form:label path="title">Title:</form:label></td>
                <td><form:input path="title"/></td>
            </tr>
            <tr>
                <td><form:label path="taskType">Type:</form:label></td>
                <td>
                    <form:select path="taskType">
                        <c:forEach items="${type}" var="type">
                            <c:choose>
                                <c:when test="${type == task.taskType}">
                                    <option value="${type}" selected>
                                        <c:out value="${task.taskType}"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${type}">
                                        <c:out value="${type}"/>
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><form:label path="description">Description:</form:label></td>
                <td><form:textarea path="description"/></td>
            </tr>
            <tr>
                <td><form:label path="startTime">Date of end</form:label></td>
                <td><form:input data-format="yyyy-MM-dd" type="date" path="startTime"/></td>
            </tr>
            <tr>
                <td><form:label path="empId">Employee:</form:label></td>
                <td>
                    <form:select path="empId">
                        <c:forEach items="${employees}" var="employee">
                            <c:choose>
                                <c:when test="${employee.id == task.empId}">
                                    <option value="${employee.id}" selected>
                                        <c:out value="${employee.lastName}"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${employee.id}">
                                        <c:out value="${employee.lastName}"/>
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><form:label path="priority">Priority:</form:label></td>
                <td>
                    <form:select path="priority">
                        <c:forEach items="${pr}" var="pr">
                            <c:choose>
                                <c:when test="${pr == task.priority}">
                                    <option value="${pr}" selected>
                                        <c:out value="${pr}"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${pr}">
                                        <c:out value="${pr}"/>
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><form:label path="complete">Complete:</form:label></td>
                <td>
                    <form:select path="complete">
                        <c:forEach items="${comp}" var="comp">
                            <c:choose>
                                <c:when test="${comp == task.complete}">
                                    <option value="${comp}" selected>
                                        <c:out value="${comp}"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${comp}">
                                        <c:out value="${comp}"/>
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
        </table>
        <input type="submit" value="Save" />
    </form:form>
</div>
</body>
</html>
