<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<html>
<head>
    <title>All employees</title>
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
<#include "homePage.ftl">
<#if !employees?has_content>
There are currently no employees in the list.
<#else >
    <@security.authorize access = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
    <br>
    <a onclick="hidetxt('addEmployee'); return false;" href="#" rel="nofollow">Add new employee</a>
    </@security.authorize>
<h3>All employees</h3>
<div style="float: left; overflow: auto; height: 500px;">
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
        <@security.authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
            <td></td>
            <td></td>
        </@security.authorize>
    </tr>
    <#list employees as employee>
    <tr>
        <td>${employee.id}</td>
        <td>${employee.firstName}</td>
        <td>${employee.lastName}</td>
        <td>${employee.middleName}</td>
        <td>${employee.birthday}</td>
        <td>${employee.salary}</td>
        <td>
            <#list departments as department>
            <#if department.id == employee.depId>
            ${department.depName}
            </#if>
        </#list>
        </td>
        <@security.authorize access = "hasRole('ROLE_ADMIN')">
            <td><a onclick="hidetxt(3${employee.id}); return false;" href="#" rel="nofollow"">edit</a> </td>
            <td><a href="/employee/delete/${employee.id}">delete</a> </td>
        </@security.authorize>
        <td>
            <a onclick="hidetxt(1${employee.id}); return false;" href="#" rel="nofollow">Show details</a>
        </td>
        <td>
            <a onclick="hidetxt(2${employee.id}); return false;" href="#" rel="nofollow">Show tasks</a>
        </td>

        <td></td>

    </#list>
</tr>
</#if>
</table>
</div>


<#list tasks as task>
<div id="${task.id}" style="display:none;">
    <table border="1px;">
        <tr>
            <td>Title:</td>
            <td>${task.title}</td>
        </tr>
        <tr>
            <td>Task type:</td>
            <td>${task.taskType}</td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>${task.description}</td>
        </tr>
        <tr>
            <td>Date of the beginning:</td>
            <td>${task.dateWhen}</td>
        </tr>
        <tr>
            <td>Progress:</td>
            <td>${task.complete}</td>
        </tr>
        <tr>
            <td>Priority:</td>
            <td>${task.priority}</td>
        </tr>
    </table>
</div>
</#list>

<#list employees as employee>
<div id="2${employee.id}" style="display: none">
    <table border="1px;">
        <tr><td>Task title</td></tr>
        <#list tasks as task>
            <#if employee.id == task.empId>
                <tr>
                    <td><a onclick="hidetxt(${task.id}); return false;" href="#" rel="nofollow">${task.title}</a></td>
                </tr>
            </#if>
        </#list>
    </table>
</div>

<div id="3${employee.id}" style="display: none">
    <form action="/employee/edit" name="employee"  method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table>
            <tr style="display: none">
                <td><label for="id">Id:</label> </td>
                <td><input type="number" name="id" value="${employee.id}"></td>
            </tr>
            <tr>
                <td><label for="firstName">First name:</label> </td>
                <td><input type="text" name="firstName" value="${employee.firstName}"></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name:</label></td>
                <td><input type="text" name="lastName" value="${employee.lastName}"></td>
            </tr>
            <tr>
                <td><label for="middleName">Middle name:</label> </td>
                <td><input type="text" name="middleName" value="${employee.middleName}"></td>
            </tr>
            <tr>
                <td><label for="birthday">Birthday:</label> </td>
                <td><input type="date" name="birthday" value="${employee.birthday}"></td>
            </tr>
            <tr>
                <td><label for="email">Email:</label> </td>
                <td><input type="email" name="email" value="${employee.email}"></td>
            </tr>
            <tr>
                <td><label for="phone">Phone:</label> </td>
                <td><input type="text" name="phone" value="${employee.phone}"></td>
            </tr>
            <tr>
                <td><label for="address">Address:</label> </td>
                <td><input type="text" name="address" value="${employee.address}"></td>
            </tr>
            <tr>
                <td><label for="salary">Salary:</label> </td>
                <td><input type="number" name="salary" value="${employee.salary}"></td>
            </tr>
            <tr>
                <td><label for="depId">Department:</label> </td>
                <td><select name="depId">
                    <#list departments as department>
                        <#if department.id == employee.depId>
                            <option value="${department.id}" selected>
                            ${department.depName}
                            </option>
                        <#else >
                            <option value="${department.id}">
                            ${department.depName}
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

<div id="1${employee.id}" class="det"  style="display: none">
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
            <td>${employee.email}</td>
            <td>${employee.phone}</td>
            <td>${employee.address}</td>
            <td>${employee.createAt}</td>
            <td>${employee.updateAt}</td>
            <td>
                <#list users as user>
                        <#if user.id == employee.createBy>
                ${user.username}
                </#if>
                    </#list>
            </td>
            <td>
                <#list users as user>
                        <#if user.id == employee.updateBy>
                ${user.username}
                </#if>
                    </#list>
            </td>
        </tr>
    </table>
</div>
</#list>



<div id="addEmployee" style="display: none; float: left">
    <form action="/employee/add" name="employee"  method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table style="border: 1px solid;">
            <tr>
                <td><label for="firstName" >First name:</label></td>
                <td><input type="text" name="firstName"/></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name:</label></td>
                <td><input type="text" name="lastName"/></td>
            </tr>
            <tr>
                <td><label for="middleName">Middle name:</label></td>
                <td><input type="text" name="middleName"/></td>
            </tr>
            <tr>
                <td><label path="birthday">Birthday:</label></td>
                <td><input type="date" name="birthday"/></td>
            </tr>
            <tr>
                <td><label path="email">Email:</label></td>
                <td><input type="email" name="email"/></td>
            </tr>
            <tr>
                <td><label path="phone">Phone number:</label></td>
                <td><input type="text" name="phone"/></td>
            </tr>
            <tr>
                <td><label path="address">Address:</label></td>
                <td><input type="text" name="address"/></td>
            </tr>
            <tr>
                <td><label path="salary">Salary:</label></td>
                <td><input type="text" name="salary"/></td>
            </tr>
            <tr>
                <td>Department:</td>
                <td>
                    <select name="depId">
                    <#list departments as department>
                        <option name="${department.id}" value="${department.id}">
                        ${department.depName}
                        </option>
                    </#list>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="Add">
    </form>
</div>

</body>
</html>