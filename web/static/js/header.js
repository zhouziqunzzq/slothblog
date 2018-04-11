var initHeader = function () {
    $("#new-article-tooltip").bind('click', function () {
        showNewArticlePopper();
    });
    $(".new-article-close").bind('click', function () {
        hideNewArticlePopper();
    });
};