<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<html>
<head>
    <style>
        #right{
            position:fixed;
            width:100px;
            height:300px;
            top:10px;
            margin-left:100%;
            left:-100px;
        }
    </style>
</head>
<body>
<div id="right">
    <a onclick="hidetxt('logout'); return false;" href="#" rel="nofollow">${employee.firstName} ${employee.lastName}</a>
    <br>
    <div id="logout" style="display: none">
    <@security.authorize access="hasAnyRole('ROLE_ADMIN')">
        <a href="/user/all" title="users">users</a>
    </@security.authorize>
    <@security.authorize access="hasAnyRole('ROLE_ADMIN')">
        <a href="/department/all" title="departments">departments</a>
    </@security.authorize>
    <@security.authorize access="hasAnyRole('ROLE_ADMIN')">
        <a href="/employee/all" title="employees">employees</a><br>
    </@security.authorize>
    <@security.authorize access="hasAnyRole('ROLE_ADMIN')">
        <a href="/task/title/${employee.id}" title="my-tasks">My tasks</a>
    </@security.authorize>
    <@security.authorize access="hasAnyRole('ROLE_ADMIN')">
        <a href="/logout" title="employee-all">logout</a>
    </@security.authorize>
    </div>
</div>
</body>
</html>