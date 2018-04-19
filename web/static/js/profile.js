$(document).ready(function () {
    var avatarInput = $("#avatar-input");
    var image = document.getElementById('image');
    var saveButton = $("#avatar-save");
    var targetWidth = 256;
    var targetHeight = 256;
    saveButton.hide();
    var cropper;
    avatarInput.on('change', function (e) {
        saveButton.hide();
        if (cropper !== undefined && cropper !== null) {
            cropper.destroy();
            cropper = null;
        }
        var files = e.target.files;
        var done = function (url) {
            avatarInput.value = '';
            $(image).attr("src", url)
            cropper = new Cropper(image, {
                aspectRatio: 1,
                viewMode: 1
            });
            saveButton.show();
        };
        var reader;
        var file;
        var url;
        if (files && files.length > 0) {
            file = files[0];
            if (URL) {
                done(URL.createObjectURL(file));
            } else if (FileReader) {
                reader = new FileReader();
                reader.onload = function (e) {
                    done(reader.result);
                };
                reader.readAsDataURL(file);
            }
        }
    });
    saveButton.on('click', function (e) {
        var newAvatar = document.getElementById('image').cropper.getCroppedCanvas();
        var resized = document.createElement('canvas');
        resized.width = targetWidth;
        resized.height = targetHeight;
        var resizedContext = resized.getContext('2d');
        resizedContext.drawImage(newAvatar, 0, 0, targetWidth, targetHeight);
        $("#avatar-base64").val(resized.toDataURL('image/png').substring(22));
        $("#edit-avatar-form").submit();
    })
});