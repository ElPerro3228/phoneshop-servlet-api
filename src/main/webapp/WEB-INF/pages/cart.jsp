<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
    <p>
        Welcome to Expert-Soft training!
    </p>

    <div class="container">
        <div class="row">
            <div class="col l2 m2 s2">
                <h5 class="start" style="color: #80cbc4;">Total price</h5>
            </div>
            <div class="col l10 m10 s10">
                <h5 id="total-price" class="start" style="color: #80cbc4;"><c:out value="${price}"/></h5>
            </div>
        </div>
    </div>

    <div class="container" style="border: 1px;">
        <c:forEach var="item" items="${cart.cartItems}">
            <c:set var="product" value="${item.product}"/>
            <div class="row" style="border: 1px;">
            <div class="item">
                <div class="col l2 m2 s2">
                    <div class="card">
                        <div class="card-image">
                            <img style="max-width: 128px;" src="${item.product.imageUrl}">
                            <span class="card-title-custom teal lighten-3"><strong>${item.product.description}</strong></span>
                        </div>
                        <div class="card-content">
                            <p>Price: <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/></p>
                        </div>
                    </div>
                </div>
                <div class="col l6 m6 s6">
                    <div class="row">
                        <div class="col s2">
                            <p>Quantity: </p>
                        </div>
                        <div class="col s4">
                            <input class="quantity" value="${item.quantity}"/>
                            <button class="btn waves-effect waves-light" type="submit" name="update">Update
                                <i class="material-icons right">edit</i>
                            </button>
                            <input class="product-id" value="${item.product.id}" hidden>
                            <div class="error-panel" style="color: red">

                            </div>
                        </div>
                        <div class="col l6 m6 s6">
                            <button class="btn waves-effect waves-light" type="submit" name="delete">Delete
                                <i class="material-icons right">delete</i>
                            </button>
                            <input class="product-id" value="${item.product.id}" hidden>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </c:forEach>
    </div>
</tags:master>