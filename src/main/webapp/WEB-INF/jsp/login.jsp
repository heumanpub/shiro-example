<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <style>
        .label {
            display: inline-block;
            width: 80px;
            height: 25px;
            line-height: 25px;
            font-size: 20px;
            text-align: right;
        }

        input.inp {
            width: 200px;
            height: 30px;
            border-radius: 5px;
            font-size: 20px;
            border: solid 1px #bbb;
            margin: 2px 5px;
        }
    </style>
</head>
<body>
<form action="login" method="post">
    <%=request.getAttribute("errorMsg") == null ? "" : request.getAttribute("errorMsg").toString() %><br>
    <label class="label">用户名</label><input type="text" name="username" class="inp"><br>
    <label class="label">密码</label><input type="password" name="password" class="inp"><br>
    <label class="label">验证码</label><input type="text" name="vCode" class="inp">
    <img src="captcha?timestamp=<%=System.currentTimeMillis()%>" alt="验证码"><br>
    <input type="checkbox" name="rememberMe" value="true"/>记住我<br>
    <button type="submit">登录</button>
</form>
</body>
</html>