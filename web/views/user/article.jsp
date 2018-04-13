<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-12
  Time: 下午9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
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
            <p class="single-article-content">${ article.content }</p>
            <c:if test="${ article.tags != null }">
                <div class="flex-container-row article-tags-container">
                    <c:forEach items="${ article.tags }" var="tag">
                        <span class="article-tag blur">${ tag.name }</span>
                    </c:forEach>
                </div>
            </c:if>
            <i class="">${ article.created_at }</i>
        </div>
        <%--New comment area--%>
        <c:if test="${ sessionScope.uid != null }">
            <form method="post" class="flex-container-column new-comment-container"
                  action="" id="new-comment-form">
                <textarea name="content" placeholder="发表评论..."></textarea>
                <button type="submit" class="basic-button">发表</button>
            </form>
        </c:if>
        <%--Comments if exist--%>
        <c:if test="${ article.comments != null }">
            <c:forEach items="${ article.comments }" var="comment">
                <div class="flex-container-column comment-container blur">
                    <p>${ comment.content }</p>
                    <p>${ comment.created_at }</p>
                </div>
            </c:forEach>
        </c:if>
    </c:if>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/new-article.js"></script>
</body>
</html>