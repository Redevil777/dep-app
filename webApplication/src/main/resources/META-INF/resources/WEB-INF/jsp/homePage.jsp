<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ser" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sping" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 14.05.2016
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
<h2>Home page</h2>

<br>
<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR')">
    <ser:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
        <li><a href="<spring:url value='/department/add' ></spring:url>"
               title="department-add">Add department</a></li><br>
        <li><a href="<spring:url value='/employee/add' ></spring:url>"
               title="employee-add">Add employee</a></li><br>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="<spring:url value='/user/add' ></spring:url>"
                   title="employee-all">Add user</a></li><br>
            <li><a href="<spring:url value='/user/all'></spring:url> "
                   title="user-all">All users</a></li><br>
        </sec:authorize>
    </ser:authorize>

    <li><a href="<spring:url value='/department/all' ></spring:url>"
           title="department-all">Show all departments</a></li><br>
    <li><a href="<spring:url value='/employee/all' ></spring:url>"
           title="employee-all">Show all employees</a></li>

</sec:authorize>
<sec:authorize access="isAnonymous()">
    <li><a href="<spring:url value='/login' ></spring:url>" title="login"> Log in
    </a> </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_USER', 'ROLE_GUEST')">
    <form:form method="post" action="/logout">
        <br>
        <input type="submit" value="Log out">
    </form:form>
</sec:authorize>


</body>
</html>
