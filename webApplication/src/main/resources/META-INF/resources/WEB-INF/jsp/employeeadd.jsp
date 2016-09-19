<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee add</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
<h2>Add employee</h2>
    <c:url var="addUrl" value="/employee/add" />


    <c:choose>
        <c:when test="${not empty error}">
            <div class="message">${error}</div>
            <br>
            There are currently no departments in the list.
            <br>
            <a href="<spring:url value='/department/add' ></spring:url>"
               title="department-add">Add department</a>
        </c:when>
        <c:otherwise>
            <form:form modelAttribute="employees" method="post" action="${addUrl}">

                <table>
                    <tr>
                        <td><form:label path="firstName" >First name:</form:label></td>
                        <td><form:input path="firstName"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="lastName">Last name:</form:label></td>
                        <td><form:input path="lastName"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="middleName">Middle name:</form:label></td>
                        <td><form:input path="middleName"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="birthday">Birthday:</form:label></td>
                        <td><form:input path="birthday" type="date" date-format="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="email">Email:</form:label></td>
                        <td><form:input path="email"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="phone">Phone number:</form:label></td>
                        <td><form:input path="phone"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="address">Address:</form:label></td>
                        <td><form:input path="address"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="salary">Salary:</form:label></td>
                        <td><form:input path="salary"/></td>
                    </tr>
                    <tr>
                        <td>Department:</td>
                        <td>
                            <form:select path="depId">
                                <c:forEach items="${departments}" var="department">
                                    <option value="${department.id}">
                                        <c:out value="${department.depName}" />
                                    </option>
                                </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                </table>

                <input type="submit" value="Add">
            </form:form>
        </c:otherwise>
    </c:choose>


    <br>

<c:url var="mainUrl" value="/" />

<p>Return to <a href="${mainUrl}">Home page</a> </p>
</body>
</html>
