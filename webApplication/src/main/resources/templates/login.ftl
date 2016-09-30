<html>
<head>
    <title>Login</title>
</head>

<body>
<form action="/login" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <table>
        <tr><td>User:</td><td><input type='text' name='username' value=''/></td></tr>
        <tr><td>Password:</td><td><input type='password' name='password' value=''/></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
    </table>
</form>
</body>
</html>