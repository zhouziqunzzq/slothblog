<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-8
  Time: 下午3:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <form class="flex-container-row search-container">
                <input type="text" class="search-input" placeholder="搜索文章或用户..."/>
                <button type="submit" class="search-button"><i class="fa fa-yelp"></i></button>
            </form>
        </div>
        <%--User--%>
        <c:if test="${ sessionScope.uid == null }">
            <h4 class="header-logo">未登录</h4>
        </c:if>
        <c:if test="${ sessionScope.uid != null }">
            <div class="flex-container-row user-tooltip-container">
                <a class="flex-container-row new-article-tooltip" href="#">
                    <i class="fa fa-plus flex-container-row new-article-tooltip"
                       title="发布博文"></i>
                </a>
                <a id="user-dropdown-trigger" href="#" data-jq-dropdown="#user-dropdown">
                    <img class="avatar-sm avatar-header" src="/static/img/default.png"/>
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
        <a href="/auth/logout" class="flex-container-row dropdown-item">
            <div>
                <i class="fa fa-sign-out fa-fw"></i>注销
            </div>
        </a>
    </div>
</div>
