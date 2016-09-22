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
    <title>User edit</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
    <c:url var="saveUrl" value="/user/edit" />
    <form:form modelAttribute="editUser" method="POST" action="${saveUrl}">
        <table>
            <tr>
                <td><form:label path="id">Id:</form:label></td>
                <td><form:input path="id" readonly="true"/></td>
            </tr>

            <tr>
                <td><form:label path="username">User name:</form:label></td>
                <td><form:input path="username"/></td>
            </tr>

            <tr>
                <td><form:label path="password">Password:</form:label></td>
                <td><form:input path="password"/></td>
            </tr>

                <td>Role:</td>
               <td>
                   <form:select path="roles">
                       <c:forEach items="${roles}" var="roles">
                           <c:choose>
                               <c:when test="${roles.roleName==roleName}">
                                   <option value="${roles.roleName}" selected>
                                       <c:out value="${roles.roleName}"/>
                                   </option>
                               </c:when>
                               <c:otherwise>
                                   <option value="${roles.roleName}">
                                       <c:out value="${roles.roleName}"/>
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