<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<html>
<head>
    <title>Home page</title>
</head>
<body>
<div id="menu">
    <@security.authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR')">
        <@security.authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR')">
            <li><a href="/department/add"
                   title="department-add">Add department</a></li><br>
            <li><a href="/employee/add"
                   title="employee-add">Add employee</a></li><br>
            <@security.authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="/user/add"
                       title="employee-all">Add user</a></li><br>
                <li><a href="/user/all"
                       title="user-all">All users</a></li><br>
            </@security.authorize>
        </@security.authorize>

        <li><a href="/department/all"
               title="department-all">Show all departments</a></li><br>
        <li><a href="/employee/all"
               title="employee-all">Show all employees</a></li>

    </@security.authorize>
    <@security.authorize access="isAnonymous()">
        <li><a href="/login" title="login"> Log in
        </a> </li>
    </@security.authorize>
    <@security.authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_HR', 'ROLE_USER', 'ROLE_GUEST')">
        <form name="logout" method="get" action="/logout">
            <br>
            <input type="submit" value="Log out">
        </form>
    </@security.authorize>

</div>
</body>
</html>