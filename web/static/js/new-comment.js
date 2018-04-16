$(document).ready(function () {
    $("#new-comment-form").attr("action", location.pathname + "/comment");
    $("#delete-article-form").attr("action", location.pathname + "/comment")
});