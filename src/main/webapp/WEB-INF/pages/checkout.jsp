<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout page">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <div class="container">
        <div class="row">
            <div class="col l2 m2 s2">
                <h5 class="start" style="color: #80cbc4;">Cart price</h5>
            </div>
            <div class="col l10 m10 s10">
                <h5 id="subtotal-price" class="start" style="color: #80cbc4;"><c:out value="${subtotalPrice}"/></h5>
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
                    <input id="name" name="name">
                </td>
            </tr>
            <tr>
                <td>Surname</td>
                <td>
                    <input id="surname" name="surname">
                </td>
            </tr>
            <tr>
                <td>Address</td>
                <td>
                    <input id="address" name="address">
                </td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>
                    <input id="phone" name="phone">
                </td>
            </tr>
        </table>
        <h5 class="error-message" style="color: red"></h5>
        <div class="row">
            <div class="col s12">
                <button id="make-order" class="btn waves-effect waves-light" type="submit">Send
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
    </div>
</tags:master>