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
<div class="flex-container-row content-container">
    <c:if test="${ article == null }">
        <p>该文章不存在</p>
    </c:if>
    <c:if test="${ article != null }">
        <div class="flex-container-column article-item blur">
            <c:if test="${ article.tags != null }">
                <c:forEach items="${ article.tags }" var="tag">
                    <span>${ tag.name }</span>
                </c:forEach>
            </c:if>
            <h2 class="article-title">${ article.title }</h2>
            <p class="article-content">${ article.content }</p>
            <i class="">${ article.created_at }</i>
        </div>
    </c:if>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
</body>
</html>