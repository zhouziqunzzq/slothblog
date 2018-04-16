<%--
  Created by IntelliJ IDEA.
  User: harry
  Date: 18-4-10
  Time: 下午10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="flex-container-column new-article-container" id="new-article-popper"
     style="display: none !important;">
    <a href="#!" class="new-article-close"><i class="fa fa-close fa-2x"></i></a>
    <form class="flex-container-column new-article-form" method="post"
          action="/user/${ sessionScope.uid }/article" id="new-article-form">
        <input type="text" name="title" placeholder="标题" class="new-article-input"/>
        <div class="flex-container-row article-tags-container new-tags-container"
             id="new-article-tags-container">
        </div>
        <input type="text" placeholder="按下Enter添加Tag" class="new-article-input"
               id="new-article-tags-input"/>
        <input type="hidden" name="tags" id="new-article-tags-input-real"/>
        <input type="hidden" name="action" value="new"/>
        <textarea name="content" placeholder="分享此刻的想法..."
                  class="new-article-input new-article-content"></textarea>
        <button class="basic-button new-article-button" id="new-article-button">发送</button>
    </form>
</div>
