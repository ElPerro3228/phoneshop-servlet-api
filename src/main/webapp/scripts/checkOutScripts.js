$(document).on("click", "#make-order", function () {
    var name = $("#name").val();
    var surname = $("#surname").val();
    var addres = $("#address").val();
    var phone = $("#phone").val();
    var payment = document.querySelector('input[name = payment]:checked').value;
    $.ajax({
        type: "POST",
        url: window.location.href,
        data: {
            name: name,
            surname: surname,
            address: addres,
            phone: phone,
            payment: payment
        },
        success: function (responseText) {
            window.location.href = responseText;
        },
        error: function (request, status, error) {
            $(".error-message").text(request.responseText);
        }
    });
});