var hideAllMsg = function (isFade) {
    if (isFade === true) {
        $(".error-msg").fadeOut();
        $(".msg").fadeOut();
    } else {
        $(".error-msg").hide();
        $(".msg").hide();
    }
};

var showErrorMsg = function (msg) {
    em = $("#error-msg");
    if (em.is(':visible') && msg === em.html()) return;
    hideAllMsg(false);
    em.html(msg);
    em.fadeIn();
};

var showMsg = function (msg) {
    m = $("#msg");
    if (m.is(':visible') && msg === m.html()) return;
    hideAllMsg(false);
    m.html(msg);
    m.fadeIn();
};

var cleanInputStatus = function (input) {
    input.removeClass("text-input-error");
    input.removeClass("text-input-ok");
    input.parent().removeClass("error-x");
    input.parent().removeClass("ok-tick");
};

var setInputStatus = function (input, status) {
    cleanInputStatus(input);
    switch (status) {
        case "error":
            input.addClass("text-input-error");
            input.parent().addClass("error-x");
            break;
        case "ok":
            input.addClass("text-input-ok");
            input.parent().addClass("ok-tick");
            break;
    }
};

var checkPassword = function () {
    inputPwd = $("#register-password");
    inputRepwd = $("#register-repassword");
    pwd = inputPwd.val();
    repwd = inputRepwd.val();
    if (repwd === "") return;
    if (pwd === "" || pwd !== repwd) {
        setInputStatus(inputPwd, "error");
        setInputStatus(inputRepwd, "error");
        showErrorMsg("两次输入的密码不一致");
    } else {
        setInputStatus(inputPwd, "ok");
        setInputStatus(inputRepwd, "ok");
        showMsg("密码输入正确");
    }
};

var checkUsername = function () {
    fetch("/auth/register/check-username", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: $.param({
            username: $("#register-username").val()
        })
    }).then(function (rst) {
        if (rst.ok) {
            rst.json()
                .then(function (rst) {
                    if (rst.result === true) {
                        setInputStatus($("#register-username"), "ok");
                        showMsg(rst.msg);

                    } else {
                        setInputStatus($("#register-username"), "error");
                        showErrorMsg(rst.msg);
                    }
                })
        }
    });
};

$(document).ready(function () {
    $("input").bind('click', function () {
        this.classList.remove("text-input-ok");
        this.classList.remove("text-input-error");
    });
    $(".input-wrapper").bind('click', function () {
        this.classList.remove("error-x");
        this.classList.remove("ok-tick");
    });

    $("#register-username").blur(function () {
        checkUsername();
    });
    $("#register-password").bind('input propertychange', function () {
        checkPassword();
    });
    $("#register-repassword").bind('input propertychange', function () {
        checkPassword();
    });

    initHeader();
    initNewArticlePopper();
});