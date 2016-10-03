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
</head>
<body>
<div>
    <%@include file="head.jsp"%>
    <div>
        <div style="float: left;">

            <table style="border: 1px solid; width: 500px; text-align:center" border="1">
                <c:forEach items="${tasks}" var="task">
                    <c:url var="showTask" value="/task/id/${task.id}"/>
                    <tr>
                        <td>
                            <c:out value="${task.title}"/>
                        </td>
                        <td> <a onclick="hidetxt(${task.id}); return false;" href="#" rel="nofollow">show task details</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div style="float: left; margin-left: 100px">
            <c:forEach items="${tasks}" var="task">
                <div id="${task.id}" style="display: none">
                    <table style="border: 1px solid; width: 500px; text-align:center" border="1">
                        <tr>
                            <td>Type:</td>
                            <td><c:out value="${task.taskType}"/></td>
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td><c:out value="${task.description}"/></td>
                        </tr>
                        <tr>
                            <td>Date:</td>
                            <td><c:out value="${task.startTime}"/></td>
                        </tr>
                        <tr>
                            <td>Priority:</td>
                            <td><c:out value="${task.priority}"/></td>
                        </tr>
                        <tr>
                            <td>Complete:</td>
                            <td><c:out value="${task.complete}"/></td>
                        </tr>
                    </table>
                </div>
            </c:forEach>

        </div>
    </div>
</div>
</body>
</html>
