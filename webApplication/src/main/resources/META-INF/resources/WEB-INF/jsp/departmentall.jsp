<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>all department</title>
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

<c:url var="addUrl" value="/department/add" />
<c:url var="all" value="/department/all" />

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a onclick="hidetxt('addDepartment'); return false;" href="#" rel="nofollow">Add new department</a>
    <br>
    <br>
</sec:authorize>
<c:choose>
    <c:when test="${empty departments}">
        There are currently no departments in the list.
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>

    </c:when>
    <c:when test="${not empty error}">
        <div class="message">${error}</div>
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>
    </c:when>
    <c:otherwise>

        All departments.

        <br><br>
        <div>
            <div style="float: left">
                <table style="border: 1px solid; width: 500px; text-align:center" border="1">
                    <tr>
                        <td>№</td>
                        <td>Name</td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td></td>
                            <td></td>
                        </sec:authorize>
                        <td></td>
                        <td></td>
                    </tr>
                    <c:forEach items="${departments}" var="department">

                        <c:url var="deleteUrl" value="/department/delete/${department.id}" />
                        <c:url var="editUrl" value="/department/edit/${department.id}" />
                        <c:url var="empDep" value="/department/employees/${department.id}"/>
                        <tr>
                            <td><c:out value="${department.id}" /></td>
                            <td><c:out value="${department.depName}" /></td>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td><a href="${editUrl}">Edit</a> </td>
                                <td><a href="${deleteUrl}">Delete</a></td>
                            </sec:authorize>
                            <td>
                                <a onclick="hidetxt(${empDep}); return false;" href="#" rel="nofollow">Show employees</a>
                            </td>
                            <%--<td><a href="${empDep}">Show employees</a> </td>--%>
                            <td>
                                <a onclick="hidetxt(${department.id}); return false;" href="#" rel="nofollow">Show details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div style="float: left; margin-left: 100px; margin-top: 100px;">

                    <c:forEach items="${departments}" var="department">
                         <div id="${department.id}" style="display: none">
                             <table style="border: 1px solid; width: 500px; text-align:center" border="1">
                                 <tr>
                                     <td>Create at:</td>
                                     <td>Update at:</td>
                                     <td>Create by:</td>
                                     <td>Update by:</td>
                                 </tr>
                                 <tr>
                                     <td><c:out value="${department.createAt}"/></td>
                                     <td><c:out value="${department.updateAt}"/></td>
                                     <td>
                                         <c:forEach items="${users}" var="user">
                                             <c:choose>
                                                 <c:when test="${user.id == department.createBy}">
                                                     <c:out value="${user.username}"/>
                                                 </c:when>
                                             </c:choose>
                                         </c:forEach>
                                     </td>
                                     <td>
                                         <c:forEach items="${users}" var="user">
                                             <c:choose>
                                                 <c:when test="${user.id == department.updateBy}">
                                                     <c:out value="${user.username}"/>
                                                 </c:when>
                                             </c:choose>
                                         </c:forEach>
                                     </td>
                                 </tr>
                             </table>
                         </div>
                </c:forEach>
            </div>

            <div id="addDepartment" style="float: left; display: none; margin-left: 100px; margin-top: 100px;">
                <form:form modelAttribute="department" method="post" action="${addUrl}">

                    <table>
                        <tr>
                            <td><form:label path="depName">Department name:</form:label></td>
                            <td><form:input path="depName"/></td>
                        </tr>
                    </table>

                    <input type="submit" value="Add">
                </form:form>
            </div>
        </div>

    </c:otherwise>
</c:choose>

<c:url var="mainUrl" value="/" />
<div STYLE="clear: both">
<p>Return to <a href="${mainUrl}">Home page</a></p>
</div>
</body>
</html>