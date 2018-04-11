<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-10
  Time: 下午10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="new-article-container" id="new-article-popper" style="display: none;">
    <a href="#!" class="new-article-close"><i class="fa fa-close fa-2x"></i></a>
    <div class="flex-container-column">
        <form class="flex-container-column new-article-form" method="post" action="/user/${ sessionScope.uid }/article">
            <input type="text" name="title" placeholder="标题" class="new-article-input"/>
            <input type="text" name="tags" placeholder="Tags" class="new-article-input"/>
            <textarea name="content" placeholder="分享此刻的想法..."
                      class="new-article-input new-article-content"></textarea>
            <button type="submit" class="basic-button new-article-button">发送</button>
        </form>
    </div>
</div>
