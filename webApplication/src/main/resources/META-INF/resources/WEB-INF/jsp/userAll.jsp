<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>all users</title>
    <link rel="stylesheet" href="css/styles.css" type="text/css">
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<%@include file="head.jsp"%>
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
    <c:url var="saveUrl" value="/user/edit" />
<div>

    All users.
    <br>
    <a onclick="hidetxt('addUser'); return false;" href="#" rel="nofollow">Add new user</a>
    <br><br>
    <div id="" style="float: left;">
        <table style="border: 1px solid; width: 500px; text-align:center" border="1">
            <tr>
                <td>№</td>
                <td>User name</td>
                <td>Password</td>
                <td></td>
                <td></td>
            </tr>
            <c:forEach items="${users}" var="user">
                <c:url var="deleteUrl" value="/user/delete/${user.id}" />
                <c:url var="editUrl" value="/user/edit/${user.id}" />
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.username}" /></td>
                    <td><c:out value="${user.password}"/></td>
                    <td><a href="${editUrl}">Edit</a></td>
                    <td><a href="${deleteUrl}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>



    <c:url var="mainUrl" value="/" />


    <div id="addUser" style="display: none; float: left; margin-left: 100px; margin-top: 50px;">
        <c:url var="addUrl" value="/user/add" />
        <form:form modelAttribute="user" method="post" action="${addUrl}">
            <table>
                <tr>
                    <td><form:label path="username">User name:</form:label></td>
                    <td><form:input path="username"/></td><tr>
                    <td><form:label path="password">Password:</form:label></td>
                    <td><form:password path="password"/></td><tr>
                    <td>Role name</td>
                    <td>
                        <form:select path="roles">
                            <c:forEach items="${roles11}" var="role">
                                <option value="${role.roleName}">
                                    <c:out value="${role.roleName}" />
                                </option>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
            </table>

            <input type="submit" value="Add">
        </form:form>
    </div>
</div>
<br>
<div style="clear: both">
    <p>Return to <a href="${mainUrl}">Home page</a></p>
</div>
</body>
</html>