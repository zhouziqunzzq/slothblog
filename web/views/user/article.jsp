<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-12
  Time: 下午9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="util.AvatarHelper" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../include/title.jsp" flush="true">
        <jsp:param name="title" value="${ article.title }"/>
    </jsp:include>
    <link href="${pageContext.request.contextPath}/static/css/index-container.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index-text.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/index.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/static/css/article.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<%--Header--%>
<jsp:include page="../components/header.jsp" flush="true">
    <jsp:param name="selected" value="single-article"/>
</jsp:include>
<%--Content--%>
<div class="flex-container-column content-container single-article-container">
    <c:if test="${ article == null }">
        <p>该文章不存在</p>
    </c:if>
    <c:if test="${ article != null }">
        <%--Single article content--%>
        <div class="flex-container-column single-article-item blur">
            <h2 class="single-article-title">${ article.title }</h2>
            <a href="/user/${ article.user_id }/profile" class="single-article-author">
                作者：
                <c:if test="${ article.userInfo != null && !article.userInfo.nickname.isEmpty() }">
                    ${ article.userInfo.nickname }
                </c:if>
                <c:if test="${ (article.userInfo != null && article.userInfo.nickname.isEmpty()) ||
                 (article.userInfo == null)}">
                    ${ article.user.username }
                </c:if>
            </a>
            <p class="single-article-content">${ article.content }</p>
            <c:if test="${ article.tags != null }">
                <div class="flex-container-row article-tags-container">
                    <c:forEach items="${ article.tags }" var="tag">
                        <span class="article-tag">${ tag.name }</span>
                    </c:forEach>
                </div>
            </c:if>
            <i class="">${ article.created_at_precise }</i>
            <c:if test="${ sessionScope.uid != null && sessionScope.uid == sessionScope.targetUid }">
                <form method="post" id="delete-article-form" class="delete-article-form" action="">
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit" class="basic-button delete-article-button">删除</button>
                </form>
            </c:if>
        </div>
        <%--New comment area--%>
        <c:if test="${ sessionScope.uid != null }">
            <div class="flex-container-row new-comment-wrapper blur">
                <% String avatarPath = new AvatarHelper().
                        getAvatarByUserId(Integer.parseInt(session.getAttribute("uid").toString())); %>
                <div class="new-comment-avatar-wrapper">
                    <img class="new-comment-avatar-auto" src="<%=avatarPath%>"/>
                </div>
                <form method="post" class="flex-container-column new-comment-form"
                      action="" id="new-comment-form">
                    <textarea name="content" class="new-comment-content" placeholder="发表评论..."></textarea>
                    <button type="submit" class="basic-button new-comment-button">发表</button>
                </form>
            </div>
        </c:if>
        <%--Comments if exist--%>
        <c:if test="${ article.comments != null }">
            <c:forEach items="${ article.comments }" var="comment">
                <c:set var="commentUid" value="${ comment.user_id }"/>
                <div class="flex-container-row comment-container">
                    <div class="comment-avatar-wrapper">
                        <a href="/user/${ comment.user_id }/profile">
                            <img class="comment-avatar-auto" src="${ comment.avatar_path }"/>
                        </a>
                    </div>
                    <div class="flex-container-column comment-right-container">
                        <div class="comment-bubble">
                            <p>${ comment.content }</p>
                            <p>${ comment.created_at }</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </c:if>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/new-comment.js"></script>
</body>
</html>