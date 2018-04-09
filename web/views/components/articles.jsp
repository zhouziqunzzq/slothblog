<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-8
  Time: 下午10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="flex-container-column index-article-container">
    <c:if test="${sessionScope.uid != null}">
        <c:out value="${sessionScope.uid}"/>
    </c:if>
    <c:forEach items="${ requestScope.articles }" var="article">
        <div>
            <h3>${ article.title }</h3>
            <i>${ article.created_at }</i>
            <p>${ article.content }</p>
        </div>
    </c:forEach>
</div>
