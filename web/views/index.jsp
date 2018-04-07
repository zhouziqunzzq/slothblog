<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-5
  Time: 下午9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="include/title.jsp" flush="true">
        <jsp:param name="title" value="首页"/>
    </jsp:include>
    <link href="${pageContext.request.contextPath}/static/css/index-container.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index-text.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="flex-container-row content-container">
    <div class="flex-container-column left-container">
        <c:if test="${session.getAttribute(\"uid\") != null}">
            <c:out value="${session.getAttribute(\"uid\")}"/>
        </c:if>
    </div>
    <div class="flex-container-column right-container">
        <%--Inline Login Component--%>
        <jsp:include page="components/inline-login.jsp" flush="true">
            <jsp:param name="error_type" value="${ error_type }"/>
        </jsp:include>
        <div class="flex-container-row message-container">
            <p id="error-msg" class="error-msg" style="display: none;"><c:out value="${error}"/></p>
            <p id="msg" class="msg" style="display: none;"><c:out value="${msg}"/></p>
            <c:if test="${error != null}">
                <p class="error-msg"><c:out value="${error}"/></p>
            </c:if>
            <c:if test="${msg != null}">
                <p class="msg"><c:out value="${msg}"/></p>
            </c:if>
        </div>
        <div class="flex-container-column register-container">
            <i class="fa fa-handshake-o fa-5x main-icon"></i>
            <h2 class="intro-text">分享你的所想</h2>
            <h3 class="intro-text">现在就加入SlothBlog</h3>
            <%--Register Component--%>
            <jsp:include page="components/register.jsp" flush="true"/>
        </div>
    </div>
</div>
<%@include file="include/basic-css.jsp" %>
<%@include file="include/basic-js.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>
</html>
