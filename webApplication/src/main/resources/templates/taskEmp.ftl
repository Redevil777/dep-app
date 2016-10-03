<html>
<head>
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
<#include "head.ftl">

<a onclick="hidetxt('addTask'); return false;" href="#" rel="nofollow">add task</a>
<br>
<br>
<table style="border: 1px solid; width: 500px; text-align:center" border="1">
    <tr>
        <td>Id:</td>
        <td>Title:</td>
        <td>Type:</td>
        <td>Description:</td>
        <td>Start time:</td>
        <td>End time:</td>
        <td>Priority:</td>
        <td>Progress:</td>
        <td>Edit:</td>
    </tr>
<#list tasks as task>
    <tr>
        <td>${task.id}</td>
        <td>${task.title}</td>
        <td>${task.taskType}</td>
        <td>${task.description}</td>
        <td>${task.startTime}</td>
        <td>${task.endTime}</td>
        <td>${task.priority}</td>
        <td>${task.complete}</td>
        <td> <a onclick="hidetxt(${task.id}); return false;" href="#" rel="nofollow">edit</a></td>
    </tr>
</#list>
</table>
<#list tasks as task>
<div id="${task.id}" style="display: none;">
    <form action="/task/edit" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table>
            <tr style="display: none">
                <td><label for="id">Id:</label> </td>
                <td><input type="number" name="id" value="${task.id}"></td>
            </tr>
            <tr>
                <td><label for="title">Title:</label> </td>
                <td><input type="title" name="title" value="${task.title}"></td>
            </tr>
            <tr>
                <td><label for="taskType">Type:</label> </td>
                <td>
                    <select name="taskType">
                        <#list types as type>
                            <#if type == task.taskType>
                                <option value="${type}" selected>
                                ${type}
                                </option>
                            <#else >
                                <option value="${type}">
                                ${type}
                                </option>
                            </#if>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="description">Description:</label> </td>
                <td><input type="description" name="description" value="${task.description}"></td>
            </tr>
            <tr>
                <td><label for="startTime">Start time:</label> </td>
                <td><input type="datetime-local" name="startTime" value="${task.startTime}"></td>
            </tr>
            <tr>
                <td><label for="endTime">End time:</label> </td>
                <td><input type="datetime-local" name="endTime" value="${task.endTime}"></td>
            </tr>
            <tr style="display: none;">
                <td><label for="empId">Employee:</label> </td>
                <td><input type="number" name="empId" value="${task.empId}"></td>
            </tr>
            <tr>
                <td><label for="priority">Priority:</label> </td>
                <td>
                    <select name="priority">
                        <#list priority as priority>
                            <#if priority == task.priority>
                                <option value="${priority}" selected>
                                ${priority}
                                </option>
                            <#else >
                                <option value="${priority}">
                                ${priority}
                                </option>
                            </#if>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="complete">Progress:</label> </td>
                <td>
                    <select name="complete">
                        <#list complete as complete>
                            <#if complete == task.complete>
                                <option value="${complete}" selected>
                                ${complete}
                                </option>
                            <#else >
                                <option value="${complete}">
                                ${complete}
                                </option>
                            </#if>
                        </#list>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="save">
    </form>
</div>
</#list>
<div id="addTask" style="display: none;">
    <form action="/task/add" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table style="border: 1px solid;">
            <tr>
                <td><label for="title">Title:</label> </td>
                <td><input type="text" name="title"></td>
            </tr>
            <tr>
                <td><label for="taskType">Type:</label> </td>
                <td>
                    <select name="taskType">
                    <#list types as type>
                        <option value="${type}">
                        ${type}
                        </option>
                    </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="description">Description:</label> </td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td><label for="startTime">Start time:</label> </td>
                <td><input type="datetime-local" name="startTime"></td>
            </tr>
            <tr>
                <td><label for="endTime">End time:</label> </td>
                <td><input type="datetime-local" name="endTime"></td>
            </tr>
            <tr style="display: none;">
                <td><label for="empId">Employee:</label> </td>
                <td><input type="number" name="empId" value="${employee.id}"></td>
            </tr>
            <tr>
                <td><label for="priority">Priority:</label> </td>
                <td>
                    <select name="priority">
                    <#list priority as priority>
                        <option value="${priority}">
                        ${priority}
                        </option>
                    </#list>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="add">
    </form>
</div>
</body>
</html>