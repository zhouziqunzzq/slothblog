<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-5
  Time: 下午9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <link href="${pageContext.request.contextPath}/static/css/index.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="login-wrapper">
    <h2>登录</h2>
    <c:if test="${error != null}">
        <div>
            <p class="error-msg"><c:out value="${error}"/></p>
        </div>
    </c:if>
    <form method="post" action="/auth/login">
        <div class="input-wrapper">
            <span class="input-tag">用户名</span>
            <input type="text" class="text-input" name="username"/>
        </div>
        <div class="input-wrapper">
            <span class="input-tag">密码</span>
            <input type="password" class="text-input" name="password"/>
        </div>
        <div class="input-wrapper">
            <button type="submit" class="login-button">登录</button>
        </div>
    </form>
</div>
<div class="login-wrapper">
    <h2>注册</h2>
    <c:if test="${error != null}">
        <div>
            <p class="error-msg"><c:out value="${error}"/></p>
        </div>
    </c:if>
    <form method="post" action="/auth/register">
        <div class="input-wrapper">
            <span class="input-tag">用户名</span>
            <input type="text" class="text-input" name="username"/>
        </div>
        <div class="input-wrapper">
            <span class="input-tag">密码</span>
            <input type="password" class="text-input" name="password"/>
        </div>
        <div class="input-wrapper">
            <span class="input-tag">确认密码</span>
            <input type="password" class="text-input" name="re-password"/>
        </div>
        <div class="input-wrapper">
            <button type="submit" class="login-button">注册</button>
        </div>
    </form>
</div>
</body>
</html>
