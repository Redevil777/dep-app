<html>
<head>
    <title>Departments</title>
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
<a onclick="hidetxt('addDep'); return false;" href="#" rel="nofollow ">add new department</a>
<#if !departments?has_content>
There are currently haven't any departments.
<#else >
<table style="border: 1px solid;">
    <tr>
        <td>№</td>
        <td>Name</td>
        <td>Del</td>
        <td>Edit</td>
        <td></td>
    </tr>
    <#list departments as department>
        <tr>
            <td>${department.id}</td>
            <td>${department.depName}</td>
            <td><a href="/department/delete/${department.id}">Delete</a> </td>
            <td><a onclick="hidetxt(1${department.id}); return false;" href="#" rel="nofollow">Edit</a> </td>
            <td><a onclick="hidetxt(2${department.id}); return false;" href="#" rel="nofollow">Details</a> </td>
        </tr>
    </#list>
</table>
</#if>

<div id="addDep" style="display: none">
    <form action="/department/add" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table style="border: 1px solid;">
            <tr>
                <td><label for="depName">Name:</label> </td>
                <td><input type="text" name="depName"></td>
            </tr>
        </table>
        <input type="submit" value="add">
    </form>
</div>

<#list departments as department>
<div id="1${department.id}" style="display:none;">
    <form action="/department/edit" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <table>
            <tr style="display: none">
                <td><label for="id">Id:</label></td>
                <td><input type="number" name="id" value="${department.id}"></td>
            </tr>
            <tr>
                <td><label for="depName">Name:</label> </td>
                <td><input type="text" name="depName" value="${department.depName}"></td>
            </tr>
        </table>
        <input type="submit" value="save">
    </form>
</div>
<div id="2${department.id}" style="display: none">
    <table style="border: 1px solid; width: 500px; text-align:center" border="1">
        <tr>
            <td>Create at:</td>
            <td>Update at:</td>
            <td>Create by:</td>
            <td>Update by:</td>
        </tr>
        <tr>
            <td>${department.createAt}</td>
            <td>${department.updateAt}</td>
            <#list users as user>
                <#if user.id == department.createBy>
                    <td>${user.username}</td>
                </#if>
                <#if user.id = department.updateBy>
                    <td>${user.username}</td>
                </#if>
            </#list>
        </tr>
    </table>
</div>
</#list>
<table border="1px;">

</table>
</body>
</html>