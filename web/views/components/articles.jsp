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
    <c:forEach items="${ requestScope.articles }" var="article">
        <div class="flex-container-column article-item blur">
            <a href="/user/${ article.user_id }/article/${ article.id }" class="article-wrapper">
                <h2 class="article-title">${ article.title }</h2>
            </a>
            <p class="article-content">${ article.content }</p>
            <c:if test="${ article.tags != null }">
                <div class="flex-container-row article-tags-container">
                    <c:forEach items="${ article.tags }" var="tag">
                        <span class="article-tag">${ tag.name }</span>
                    </c:forEach>
                </div>
            </c:if>
            <i class="article-time">${ article.created_at }</i>
        </div>
    </c:forEach>
</div>