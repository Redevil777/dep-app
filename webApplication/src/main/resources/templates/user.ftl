<html>
<head>
    <title>Users</title>
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
<#if !users?has_content>
There are currently haven't any users.
<#else>
<table style="border: 1px solid; width: 200px; text-align:center" border="1">
    <tr>
        <td>Id</td>
        <td>Username</td>
        <td>Role</td>
        <td>Employee</td>
        <td></td>
    </tr>

    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <#list user.roles as role>
                <td>${role.roleName}</td>
            </#list>
            <#list employees as employee>
                <#if user.empId == employee.id>
                    <td>${employee.lastName}</td>
                </#if>
            </#list>
            <td><a onclick="hidetxt(1${user.id}); return false;" href="#" rel="nofollow">edit</a> </td>
        </tr>
    </#list>
</table>

    <#list users as user>
    <div id="1${user.id}" style="display: none">
        <form action="/user/edit" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <table>
                <tr>
                    <td><label for="id">Id:</label> </td>
                    <td><input type="number" name="id" value="${user.id}"></td>
                </tr>
                <tr>
                    <td><label for="username">Username:</label> </td>
                    <td><input type="text" name="username" value="${user.username}"></td>
                </tr>
                <tr>
                    <td><label for="password">Pass:</label> </td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td><label for="roles">Role:</label></td>
                    <td><select name="roles">

                        <#list roles as role>
                            <#list user.roles as userRoles>
                                <#if role.roleName == userRoles.roleName>
                                    <option value="${role.roleName}" selected>
                                    ${role.roleName}
                                    </option>
                                <#else >
                                    <option value="${role.roleName}">
                                    ${role.roleName}
                                    </option>
                                </#if>
                            </#list>
                        </#list>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="empId">Employee</label> </td>
                    <td>
                        <select name="empId">
                            <#list employees as employee>
                                <#if user.empId == employee.id>
                                    <option value="${employee.id}" selected>
                                    ${employee.lastName}
                                    </option>
                                <#else >
                                    <option value="${employee.id}">
                                    ${employee.lastName}
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
</#if>
</body>
</html>