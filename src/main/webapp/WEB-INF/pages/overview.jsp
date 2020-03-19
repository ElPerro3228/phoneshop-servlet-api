<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order overview">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <div class="container">
        <div class="row">
            <div class="col l2 m2 s2">
                <h5 class="start" style="color: #80cbc4;">Cart price</h5>
            </div>
            <div class="col l10 m10 s10">
                <h5 id="subtotal-price" class="start" style="color: #80cbc4;"><fmt:formatNumber value="${cartPrice}" type="currency" currencySymbol="${currency}"/></h5>
            </div>
        </div>
        <div class="row">
            <div class="col l2 m2 s2">
                <h5 class="start" style="color: #80cbc4;">Delivery price</h5>
            </div>
            <div class="col l10 m10 s10">
                <h5 id="delivery-price" class="start" style="color: #80cbc4;"><fmt:formatNumber value="${deliveryPrice}" type="currency" currencySymbol="${currency}"/></h5>
            </div>
        </div>
        <div class="row">
            <div class="col l2 m2 s2">
                <h5 class="start" style="color: #80cbc4;">Total price</h5>
            </div>
            <div class="col l10 m10 s10">
                <h5 id="total-price" class="start" style="color: #80cbc4;"><fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="${currency}"/></h5>
            </div>
        </div>
    </div>

    <div class="container">
        <h5 id="meassge" class="start" style="color: green;"></h5>
    </div>

    <div class="container" style="border: 1px;">
        <table>
            <tr>
                <td>Name</td>
                <td>
                    ${name}
                </td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>
                    ${surname}
                </td>
            </tr>
            <tr>
                <td>Address</td>
                <td>
                    ${address}
                </td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>
                    ${phone}
                </td>
            </tr>
            <tr>
                <td>Payment</td>
                <td>${payment}</td>
            </tr>
        </table>
        <div class="row" style="border: 1px;">
        <c:forEach var="item" items="${cart}">
            <c:set var="product" value="${item.product}"/>
            <div class="col l2 m2 s2">
                <div class="card">
                    <div class="card-image">
                        <img style="max-width: 128px;" src="${product.imageUrl}">
                        <span class="card-title-custom teal lighten-3"><strong>${product.description}</strong></span>
                    </div>
                    <div class="card-content">
                        <p>Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${currency}"/></p>
                        <p>Quantity: <c:out value="${item.quantity}"/> </p>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
        </div>
    </div>
</tags:master>