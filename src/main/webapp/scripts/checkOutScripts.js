$(document).on("click", "#make-order", function () {
    var name = $("#name").val();
    var surname = $("#surname").val();
    var addres = $("#address").val();
    var phone = $("#phone").val();
    $.ajax({
        type: "POST",
        url: window.location.href,
        data: {
            name: name,
            surname: surname,
            address: addres,
            phone: phone
        },
        success: function (responseText) {
            window.location.href = responseText;
        },
        error: function (request, status, error) {
            $(".error-message").text(request.responseText);
        }
    });
});