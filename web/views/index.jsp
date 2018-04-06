<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-5
  Time: 下午9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.GlobalConfigHelper" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><%= "首页 - " +
            GlobalConfigHelper.getConfigFromContext(application).getProperty("WebsiteName") %>
    </title>
    <link href="${pageContext.request.contextPath}/static/css/index-container.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index-text.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="content-container">
    <div class="left-container">
        <c:if test="${session.getAttribute(\"uid\") != null}">
            <c:out value="${session.getAttribute(\"uid\")}"/>
        </c:if>
    </div>
    <div class="right-container">
        <c:if test="${error != null}">
            <div>
                <p class="error-msg"><c:out value="${error}"/></p>
            </div>
        </c:if>
        <c:if test="${msg != null}">
            <div>
                <p class="msg"><c:out value="${msg}"/></p>
            </div>
        </c:if>
        <div class="login-wrapper">
            <form method="post" action="/auth/login">
                <div class="inline-input-wrapper">
                    <input type="text" class="text-input" name="username" placeholder="用户名"/>
                </div>
                <div class="inline-input-wrapper">
                    <input type="password" class="text-input" placeholder="密码"/>
                </div>
                <div class="inline-input-wrapper">
                    <button type="submit" class="login-button">登录</button>
                </div>
            </form>
        </div>
        <div class="register-wrapper">
            <i class="fa fa-handshake-o fa-5x main-icon"></i>
            <h2 class="intro-text">分享你的所想</h2>
            <h3 class="intro-text">现在就加入SlothBlog</h3>
            <form method="post" action="/auth/register">
                <div class="input-wrapper">
                    <input type="text" class="text-input" name="username" placeholder="用户名"/>
                </div>
                <div class="input-wrapper">
                    <input type="password" class="text-input" name="password" placeholder="密码"/>
                </div>
                <div class="input-wrapper">
                    <input type="password" class="text-input" name="re-password" placeholder="确认密码"/>
                </div>
                <div class="input-wrapper">
                    <button type="submit" class="login-button">注册</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="include/basic-css.jsp" %>
</body>
</html>
