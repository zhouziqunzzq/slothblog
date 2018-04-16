var newTags = [];

var initNewArticlePopper = function () {
    var popper = $('#new-article-popper');
    popper.appendTo('body');
    // Disable submit on Enter pressed
    $("#new-article-popper>form").on("keypress", function (e) {
        if (e.keyCode === 13) {
            e.preventDefault();
        }
    });
    // Add tag to newTags on Enter pressed
    $("#new-article-tags-input").on("keypress", function (e) {
        if (e.keyCode === 13) {
            var tagsInput = $("#new-article-tags-input");
            // Check if tag is null or already exist
            if (tagsInput.val() === "" || newTags.indexOf(tagsInput.val()) !== -1)
                return;
            else {
                // Add to array
                newTags.push(tagsInput.val());
                // Add dom
                $("#new-article-tags-container").append(
                    '<span class="article-tag article-tag-deep article-tag-deletable">' +
                    tagsInput.val() +
                    '</span>');
                // Clear tag input
                tagsInput.val("");
                // Bind click
                $(".article-tag-deletable").on('click', function() {
                    console.log($(this).html());
                    var i = newTags.indexOf($(this).html());
                    if (i > -1) {
                        newTags.splice(i, 1);
                    }
                    $(this).remove();
                });
            }
        }
    });
    // Bind submit to button
    $("#new-article-button").on('click', function() {
        $("#new-article-tags-input-real").val(newTags.join(' '));
        $("#new-article-form").submit();
    });
};

var showNewArticlePopper = function () {
    $('#new-article-popper').fadeTo(400, 1, function () {
        $('#new-article-popper').attr("style", "display: block");
    });

};

var hideNewArticlePopper = function () {
    $('#new-article-popper').fadeTo(400, 0, function () {
        $('#new-article-popper').attr("style", "display: none !important");
    });
};