$(document).on("click", "#send-comment", function () {
    var rate = document.querySelector('input[name = rating]:checked').value;
    var name = $("#name").val();
    var comment = $("#comment").val();
    $.ajax({
        type: "POST",
        url: url,
        data: {
            rate: rate,
            name: name,
            comment: comment,
            productId: id
        },
        success: function (responseText) {
            $("#comments-container").append(responseText);
        },
        error: function (request, status, error) {
            console.log("error" + " " + request.responseText);
            alert(request.responseText);
        }
    });
});