<%@ page import="util.AvatarHelper" %><%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-11
  Time: 下午7:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../include/title.jsp" flush="true">
        <jsp:param name="title" value="个人中心"/>
    </jsp:include>
    <link href="${pageContext.request.contextPath}/static/css/index-container.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index-text.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/profile.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<%--Header--%>
<jsp:include page="../components/header.jsp" flush="true">
    <jsp:param name="selected" value="profile"/>
</jsp:include>
<%--Content--%>
<div class="flex-container-row content-container">
    <div class="flex-container-row profile-container">
        <div class="flex-container-row profile-card blur">
            <c:choose>
                <c:when test="${ userInfo == null }">
                    <p>Invalid user ID!</p>
                </c:when>
                <c:otherwise>
                    <div class="profile-avatar-container">
                        <% String avatarPath = new AvatarHelper().
                                getAvatarByUserId(Integer.parseInt(
                                        session.getAttribute("targetUid").toString())); %>
                        <img class="avatar-auto" src="<%=avatarPath%>"/>
                    </div>
                    <div class="profile-content-container">
                        <p>${ userInfo.nickname }</p>
                        <p>${ userInfo.gender }</p>
                        <p>${ userInfo.email }</p>
                        <p>${ userInfo.intro }</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>
</html>