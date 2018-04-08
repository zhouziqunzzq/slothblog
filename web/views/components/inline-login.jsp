<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-7
  Time: 下午1:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex-container-row login-container">
    <form method="post" action="/auth/login">
        <div class="input-wrapper inline-input-wrapper
        <c:if test="${ error_type != null }" >
            ${ param.error_type == "INVALID_USERNAME" ? "error-x" : "ok-tick" }
        </c:if>">
            <input type="text" class="text-input
                    <c:if test="${ error_type != null }" >
                        ${ param.error_type == "INVALID_USERNAME" ? "text-input-error" : "text-input-ok" }
                    </c:if>" name="username"
                   value="${ sessionScope["flash_login_username"] }" placeholder="用户名"/>
        </div>
        <div class="input-wrapper inline-input-wrapper
        <c:if test="${ error_type != null && param.error_type == \"INVALID_PASSWORD\" }" >
            ${ param.error_type == "INVALID_PASSWORD" ? "error-x" : "ok-tick" }
        </c:if>">
            <input type="password" class="text-input
            <c:if test="${ error_type != null && param.error_type == \"INVALID_PASSWORD\" }" >
                ${ param.error_type == "INVALID_PASSWORD" ? "text-input-error" : "text-input-ok" }
            </c:if>" name="password"
                   value="${ sessionScope["flash_login_password"] }" placeholder="密码"/>
        </div>
        <div class="input-wrapper inline-input-wrapper">
            <button type="submit" class="basic-button login-button">登录</button>
        </div>
    </form>
</div>
