<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-7
  Time: 下午5:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post" action="/auth/register">
    <div class="input-wrapper">
        <input type="text" class="text-input text-input-longer"
               value="${ sessionScope["flash_reg_username"] }"
               name="username" id="register-username" placeholder="用户名"/>
    </div>
    <div class="input-wrapper">
        <input type="password" class="text-input text-input-longer"
               value="${ sessionScope["flash_reg_password"] }"
               name="password" id="register-password" placeholder="密码"/>
    </div>
    <div class="input-wrapper">
        <input type="password" class="text-input text-input-longer"
               name="repassword" id="register-repassword" placeholder="确认密码"/>
    </div>
    <div class="input-wrapper flex-container-column">
        <button type="submit" class="basic-button register-button"
                id="register-submit">注册
        </button>
    </div>
</form>
