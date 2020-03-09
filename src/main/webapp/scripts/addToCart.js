$(document).on("click", "#add-to-cart", function () {
    $.ajax({
        type: "POST",
        url: window.location.href,
        data: {
            quantity: $("#quantity").val()
        },
        success: function (responseText) {
            $("#success-message").text("Added to cart successfully");
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
});