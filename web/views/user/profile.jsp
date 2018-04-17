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
            <c:if test="${ user == null }">
                <p>Invalid user ID!</p>
            </c:if>
            <c:if test="${ user!=null }">
                <div class="flex-container-column widget widget-tab">
                    <input type="radio" name="widget-tab" id="modify-password"/>
                    <input type="radio" name="widget-tab" id="user-info" checked="checked"/>
                    <input type="radio" name="widget-tab" id="edit-userinfo"/>
                    <div class="widget-title inline-ul">
                        <ul>
                            <li class="user-info">
                                <label for="user-info">个人信息</label>
                            </li>
                            <c:if test="${ sessionScope.uid == sessionScope.targetUid }">
                                <li class="edit-userinfo">
                                    <label for="edit-userinfo">编辑个人信息</label>
                                </li>
                                <li class="modify-password">
                                    <label for="modify-password">修改密码</label>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                    <div class="widget-box">
                        <c:if test="${ sessionScope.uid == sessionScope.targetUid }">
                            <ul class="modify-password-form">
                                <form class="flex-container-row" method="post"
                                      action="/auth/edit-password" id="edit-password-form">
                                    <div class="flex-container-column">
                                        <label for="old-password">旧密码</label>
                                        <input class="text-input profile-text-input" type="password" name="oldpassword"
                                               id="old-password"/>
                                        <label for="password">新密码</label>
                                        <input class="text-input profile-text-input" type="password" name="password"
                                               id="password"/>
                                        <label for="re-password">确认密码</label>
                                        <input class="text-input profile-text-input" type="password" name="repassword"
                                               id="re-password"/>

                                        <br>
                                        <button class="basic-button" type="submit">确认修改</button>
                                    </div>
                                </form>
                            </ul>
                            <c:if test="${ userInfo == null && sessionScope.uid == sessionScope.targetUid}">
                                <ul class="user-info-form">
                                    <p style="color: #ff8c8c;">个人信息为空，请编辑！</p>
                                </ul>
                                <ul class="edit-userinfo-form">
                                    <form class="flex-container-row" method="post"
                                          action="/user/${ sessionScope.uid }/profile" id="profile-form">
                                        <div class="flex-container-column">
                                            <label for="nickname">昵称</label>
                                            <input class="text-input profile-text-input" type="text" name="nickname"
                                                   id="nickname"/>

                                            <label>性别</label>
                                            <div class="inline-radio">
                                                <input type="radio" name="gender" id="genderM" value="0"/>
                                                <label for="genderM">男</label>
                                                <input type="radio" name="gender" id="genderF" value="1"/>
                                                <label for="genderF">女</label>
                                            </div>

                                            <br/><label for="email">email</label>
                                            <input class="text-input profile-text-input" type="text" name="email"
                                                   id="email"/>

                                            <label for="intro">个人简介</label>
                                            <textarea class="text-input profile-text-input" name="intro"
                                                      id="intro"></textarea>

                                            <button class="basic-button" type="submit">保存</button>
                                        </div>
                                    </form>
                                </ul>
                            </c:if>
                            <c:if test="${ userInfo != null && sessionScope.uid == sessionScope.targetUid}">
                                <ul class="user-info-form">
                                    <div class="flex-container-row">
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
                                    </div>
                                </ul>

                                <ul class="edit-userinfo-form">
                                    <form class="flex-container-row" method="post"
                                          action="/user/${ sessionScope.uid }/profile">
                                        <div class="flex-container-column">
                                            <label for="nickname">昵称</label>
                                            <input class="text-input profile-text-input" type="text" name="nickname"
                                                   id="nickname"
                                                   value="${ userInfo.nickname }"/>

                                            <label>性别</label>
                                            <div class="inline-radio">
                                                <input type="radio" name="gender" id="genderM" value="0"
                                                        <c:if test="${ userInfo.gender.toString() == \"MALE\" }">
                                                            checked="checked"
                                                        </c:if>/>
                                                <label for="genderM">男</label>
                                                <input type="radio" name="gender" id="genderF" value="1"
                                                        <c:if test="${ userInfo.gender.toString() == \"FEMALE\" }">
                                                            checked="checked"
                                                        </c:if>/>
                                                <label for="genderF">女</label>
                                            </div>

                                            <br/><label for="email">email</label>
                                            <input class="text-input profile-text-input" type="text" name="email"
                                                   id="email"
                                                   value="${ userInfo.email }"/>

                                            <label for="intro">个人简介</label>
                                            <textarea class="text-input profile-text-input" name="intro"
                                                      id="intro">${ userInfo.intro }</textarea>

                                            <br>
                                            <button class="basic-button" type="submit">修改个人信息</button>
                                        </div>
                                    </form>
                                </ul>
                            </c:if>
                        </c:if>
                        <c:if test="${ sessionScope.uid != sessionScope.targetUid && userInfo == null }">
                            <ul class="user-info-form">
                                <p style="color: #ff8c8c;">个人信息为空！</p>
                                <a href="/user/${ sessionScope.targetUid }" class="user-blog">访问ta的博客主页</a>
                            </ul>
                        </c:if>
                        <c:if test="${ sessionScope.uid != sessionScope.targetUid && userInfo != null}">
                            <ul class="user-info-form">
                                <div class="flex-container-row">
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
                                        <a href="/user/${ sessionScope.targetUid }" class="user-blog">访问ta的博客主页</a>
                                    </div>
                                </div>
                            </ul>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>
</html>