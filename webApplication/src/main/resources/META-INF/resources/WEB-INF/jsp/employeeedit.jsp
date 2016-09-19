<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee edit</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
<c:url var="saveUrl" value="/employee/edit" />
<form:form modelAttribute="employee" method="POST" action="${saveUrl}">
    <table>
        <tr>
            <td><form:label path="id">Id:</form:label></td>
            <td><form:input path="id" readonly="true"/></td>
        </tr>
        <tr>
            <td><form:label path="firstName">First name:</form:label></td>
            <td><form:input path="firstName"/></td>
        </tr>
        <tr>
            <td><form:label path="lastName">Last name:</form:label></td>
            <td><form:input path="lastName"/></td>
        </tr>
        <tr>
            <td><form:label path="middleName">Middle name:</form:label></td>
            <td><form:input path="middleName" /></td>
        </tr>
        <tr>
            <td><form:label path="birthday">Birthday:</form:label></td></td>
            <td><form:input data-format="yyyy-MM-dd" type="date" path="birthday" value="${employee.birthday}"/></td>
        </tr>
        <tr>
            <td><form:label path="email">Email:</form:label> </td>
            <td><form:input path="email"/></td>
        </tr>
        <tr>
            <td><form:label path="phone">Phone:</form:label> </td>
            <td><form:input path="phone"/></td>
        </tr>
        <tr>
            <td><form:label path="address">Address:</form:label> </td>
            <td><form:input path="address"/></td>
        </tr>
        <tr>
            <td><form:label path="salary" >Salary:</form:label></td>
            <td><form:input path="salary"/></td>
        </tr>
        <tr>
            <td>Department:</td>
            <td>
                <form:select path="depId">
                    <c:forEach items="${departments}" var="department">
                        <c:choose>
                            <c:when test="${department.id==employee.depId}">
                                <option value="${department.id}" selected>
                                    <c:out value="${department.depName}" />
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${department.id}">
                                    <c:out value="${department.depName}" />
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
</body>
</html>