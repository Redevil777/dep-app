<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<html>
<head>
    <title>Home page</title>
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
    <div id="right">
        <form name="logout" method="get" action="/logout">
            <br>
            <a onclick="hidetxt('logout'); return false;" href="#" rel="nofollow">${employee.lastName}</a>
            <div id="logout" style="display: none">
                <input type="submit" value="Log out">
            </div>
        </form>
    </div>

</@security.authorize>

</div>
</body>
</html>