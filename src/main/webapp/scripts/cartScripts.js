$('button[name="update"]').on("click", function () {
    var $this = $(this);
    $.ajax({
        type: "PUT",
        url: window.location.href,
        contentType: "application/json",
        data: JSON.stringify({
            quantity: $this.siblings(".quantity").val(),
            id: $this.siblings(".product-id").val()
        }),
        success: function (responseText) {
            $this.siblings(".error-panel").text("");
            $("#total-price").text(responseText);
        },
        error: function (request, status, error) {
            $this.siblings(".error-panel").text(request.responseText);
        }
    });
});

$('button[name="delete"]').on("click", function () {
    var parent = $(this).closest(".item");
    var $this = $(this);
    $.ajax({
        type: "DELETE",
        url: window.location.href,
        contentType: "application/json",
        data: JSON.stringify({
            id: $this.siblings(".product-id").val()
        }),
        success: function (responseText) {
            $("#total-price").text(responseText);
            parent.remove();
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
});

