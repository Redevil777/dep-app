<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 15.05.2016
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee all</title>
    <style>
        <%@include file="css/styles.css" %>
    </style>
</head>
<body>
<c:url var="addUrl" value="/employee/add" />
<c:url var="mainUrl" value="/" />
<c:url var="all" value="/employee/all" />
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
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

<div>
    <%@include file="head.jsp"%>
    <c:choose>
        <c:when test="${empty employees}">
            There are currently no employees in the list.
            <br>
            <br>
        </c:when>
        <c:otherwise>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
                <br>
                <a onclick="hidetxt('addEmployee'); return false;" href="#" rel="nofollow">Add new employee</a>
            </sec:authorize>
            <h3>All employees</h3>
            <div style="float: left;">
                <table style="border: 1px solid; width: 500px; text-align:center" border="1">

                    <tr>
                        <td>№</td>
                        <td>First name</td>
                        <td>Last name</td>
                        <td>Middle Name</td>
                        <td>Birthday</td>
                        <td>Salary</td>
                        <td>Department</td>
                        <td></td>
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
                            <td></td>
                            <td></td>
                        </sec:authorize>

                    </tr>


                    <c:forEach items="${employees}" var="employee">

                        <c:url var="deleteUrl" value="/employee/delete/${employee.id}" />
                        <c:url var="editUrl" value="/employee/edit/${employee.id}" />
                        <tr>
                            <td><c:out value="${employee.id}" /></td>
                            <td><c:out value="${employee.firstName}" /></td>
                            <td><c:out value="${employee.lastName}" /></td>
                            <td><c:out value="${employee.middleName}"/></td>
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

                            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
                                <td>
                                    <a href="${editUrl}">Edit</a>
                                </td>
                                <td>
                                    <a href="${deleteUrl}">Delete</a>
                                </td>
                            </sec:authorize>
                            <td>
                                <a onclick="hidetxt(${employee.id}); return false;" href="#" rel="nofollow">Show details</a>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div style="float: left; margin-left: 100px; margin-top: 100px;">


                <c:forEach items="${employees}" var="employee">
                    <div id="${employee.id}" style="display: none">
                        <table style="border: 1px solid; width: 500px; text-align:center" border="1">
                            <tr>
                                <td>Email:</td>
                                <td>Phone number:</td>
                                <td>Address:</td>
                                <td>Create at:</td>
                                <td>Update at:</td>
                                <td>Create by:</td>
                                <td>Update by:</td>
                            </tr>
                            <tr>
                                <td><c:out value="${employee.email}" /></td>
                                <td><c:out value="${employee.phone}" /></td>
                                <td><c:out value="${employee.address}" /></td>
                                <td><c:out value="${employee.createAt}" /></td>
                                <td><c:out value="${employee.updateAt}" /></td>
                                <td>
                                    <c:forEach items="${users}" var="user">
                                        <c:choose>
                                            <c:when test="${user.id==employee.createBy}">
                                                <c:out value="${user.username}"/>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${users}" var="user">
                                        <c:choose>
                                            <c:when test="${user.id==employee.updateBy}">
                                                <c:out value="${user.username}"/>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:forEach>
                <div id="addEmployee" style="display: none; float: left">
                    <form:form modelAttribute="employee" method="post" action="${addUrl}">

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
                </div>
            </div>
            <div style="clear: both">
                <br><br>
                <a onclick="hidetxt('id'); return false;" href="#" rel="nofollow">Search employees between date of birthday</a>
                <div id="id" style="display: none">
                    <br>
                    <br>
                    <c:url var="search" value="/employee/between" />
                    <form:form method="get" action="${search}">
                        From <input type="date" name="from" required>
                        To <input type="date" name="to" required>
                        <input type="submit" value="search">
                    </form:form>
                </div>

                <br>
                <br>
                <a onclick="hidetxt('q'); return false;" href="#" rel="nofollow">Search employees by date of birthday</a>
                <div id="q" style="display: none">
                    <br>
                    <br>
                    <c:url var="search2" value="/employee/date" />
                    <form:form method="get" action="${search2}">
                        Date <input type="date" name="date" required>
                        <input type="submit" value="search">
                    </form:form>
                </div>
                <br>

            </div>
        </c:otherwise>
    </c:choose>

    <div style="clear: both">
        <p>Return to <a href="${mainUrl}">Home page</a></p>
    </div>
</div>
</body>
</html>
