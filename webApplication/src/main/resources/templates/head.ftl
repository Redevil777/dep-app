<#assign security=JspTaglibs["/WEB-INF/security.tld"]/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <style>
        #right{
            position:fixed;
            width:100px;
            height:300px;
            top:10px;
            margin-left:100%;
            left:-100px;
        }
        .user-menu{
            display: none;
        }
        a{
            text-decoration: none;
        }
    </style>
    <script>
        $(document).ready(function(){
            $(".flip").click(function(){
                $(".user-menu").toggle(700);
            });
        });
    </script>
</head>
<body>
<div id="right">
    <a class="flip" onclick="">${employee.lastName} ${employee.firstName}</a>
<@security.authorize access="hasAnyRole('ROLE_ADMIN')">
    <p class="user-menu"><a href="/user/all" title="users">users</a></p>
</@security.authorize>
<@security.authorize access="hasAnyRole('ROLE_ADMIN')">
    <p class="user-menu"><a href="/department/all" title="departments">departments</a></p>
</@security.authorize>
<@security.authorize access="hasAnyRole('ROLE_ADMIN')">
    <p class="user-menu"><a href="/employee/all" title="employees">employees</a></p>
</@security.authorize>
<@security.authorize access="hasAnyRole('ROLE_ADMIN')">
    <p class="user-menu"><a href="/task/title/${employee.id}" title="my-tasks">My tasks</a><p>
</@security.authorize>
<@security.authorize access="hasAnyRole('ROLE_ADMIN')">
    <p class="user-menu"><a href="/logout" title="employee-all">logout</a></p>
</@security.authorize>
</div>
</div>
</body>
</html>