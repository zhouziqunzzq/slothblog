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
    <form method="post" action="auth/login">
        <div class="input-wrapper">
            <span class="input-tag">用户名</span>
            <input type="text" class="text-input" name="username"/>
        </div>
        <div class="input-wrapper">
            <span class="input-tag">密码</span>
            <input type="password" class="text-input" name="password"/>
        </div>
        <button type="submit" class="login-button">登录</button>
    </form>
    <c:forEach items="${users}" var="user">
        <p><c:out value="${user.id}"/></p>
        <p><c:out value="${user.username}"/></p>
        <p><c:out value="${user.password}"/></p>
        <p><c:out value="${user.salt}"/></p>
    </c:forEach>
</div>
</body>
</html>
