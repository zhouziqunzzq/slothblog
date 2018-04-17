<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-6
  Time: 下午6:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../include/title.jsp" flush="true">
        <jsp:param name="title" value="用户中心"/>
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
    <jsp:param name="selected" value="userhome"/>
</jsp:include>
<%--Content--%>
<div class="flex-container-row content-container">
    <div class="flex-container-column left-container
        ${ sessionScope.uid != null ? "expanded left-container-no-shadow" : "" }">
        <c:if test="${ articles.isEmpty() }">
            <h3 style="color: #ffffff; align-self: center;">
                个人博客空空如也...点击右上角的<i class="fa fa-plus"></i>写点什么吧！
            </h3>
        </c:if>
        <c:set var="articles" value="${ articles }" scope="request"/>
        <jsp:include page="../components/articles.jsp"/>
    </div>
</div>
<%@include file="../include/basic-css.jsp" %>
<%@include file="../include/basic-js.jsp" %>
</body>
</html>
