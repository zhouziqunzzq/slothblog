<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-8
  Time: 下午3:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.AvatarHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="flex-container-row header-container blur">
    <%--Nav section--%>
    <div class="flex-container-row nav-container">
        <h2 class="header-logo">SlothBlog</h2>
        <a href="/">
            <div class="nav-item
                ${ param.selected == "index" ? "nav-item-selected" : "" }">首页
            </div>
        </a>
        <a href="#">
            <div class="nav-item
                ${ param.selected == "find" ? "nav-item-selected" : "" }">发现
            </div>
        </a>
    </div>
    <div class="flex-container-row header-right-container">
        <%--Search bar--%>
        <div class="flex-container-row search-container">
            <form class="flex-container-row search-container" method="get"
                  action="/">
                <input type="text" class="search-input" name="keyword" placeholder="搜索文章..."
                       value="${ sessionScope.keyword }"/>
                <button type="submit" class="search-button"><i class="fa fa-yelp"></i></button>
            </form>
        </div>
        <%--User--%>
        <c:if test="${ sessionScope.uid == null }">
            <a href="/" style="text-decoration: none;"><h4 class="header-logo">未登录</h4></a>
        </c:if>
        <c:if test="${ sessionScope.uid != null }">
            <% String avatarPath = new AvatarHelper().
                    getAvatarByUserId(Integer.parseInt(session.getAttribute("uid").toString())); %>
            <div class="flex-container-row user-tooltip-container">
                <a class="flex-container-row new-article-tooltip" href="#" id="new-article-tooltip">
                    <i class="fa fa-plus flex-container-row new-article-tooltip"
                       title="发布博文"></i>
                </a>
                <a id="user-dropdown-trigger" href="#" data-jq-dropdown="#user-dropdown">
                    <img class="avatar-sm avatar-header" src="<%=avatarPath%>"/>
                </a>
            </div>
        </c:if>
    </div>
</div>
<%--Dropdown menu--%>
<div id="user-dropdown" class="jq-dropdown jq-dropdown-anchor-right">
    <div class="jq-dropdown-panel flex-container-column dropdown-container blur">
        <a href="/user/${ sessionScope.uid }" class="flex-container-row dropdown-item">
            <div>
                <i class="fa fa-rss fa-fw"></i>我的博客
            </div>
        </a>
        <a href="#" class="flex-container-row dropdown-item">
            <div>
                <i class="fa fa-list fa-fw"></i>我的关注
            </div>
        </a>
        <a href="/user/${ sessionScope.uid }/profile" class="flex-container-row dropdown-item">
            <div>
                <i class="fa fa-user-circle-o fa-fw"></i>个人中心
            </div>
        </a>
        <a href="/auth/logout" class="flex-container-row dropdown-item">
            <div>
                <i class="fa fa-sign-out fa-fw"></i>注销
            </div>
        </a>
    </div>
</div>
<%--New article popup--%>
<jsp:include page="new_article.jsp" flush="true"/>