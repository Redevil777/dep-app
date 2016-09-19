<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department edit</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
    <c:url var="saveUrl" value="/department/edit" />
    <form:form modelAttribute="department" method="POST" action="${saveUrl}">
        <table>
            <tr>
                <td><form:label path="id">Id:</form:label></td>
                <td><form:input path="id" readonly="true"/></td>
            </tr>

            <tr>
                <td><form:label path="depName">Name:</form:label></td>
                <td><form:input path="depName"/></td>
            </tr>
        </table>
        <input type="submit" value="Save" />
    </form:form>
</body>
</html>