<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <div id="cart-content"><c:out value="${cart}"/></div>
    <tags:error error="${error}" message="There is error"/>
    <div id = "success-message" style="color: green"></div>

    <div class="container">
        <div class="row">
            <div class="col l4 m4 s12">
                <div class="card">
                    <div class="card-image">
                        <img style="max-width: 256px;" src="${product.imageUrl}">
                        <span class="card-title-custom teal lighten-3"><strong><c:out value="${product.description}"/></strong></span>
                    </div>
                    <div class="card-content">
                        <p><a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}"><c:out value="${product.price}"/></a></p>
                        <p>Stock: <c:out value="${product.stock}"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col col l4 m4 s4">
                <input id="quantity" value="" class="price"/>
            </div>
            <div class="col l4 m4 s4">
                <button id="add-to-cart" class="btn waves-effect waves-light" type="submit" name="action">Add to cart
                    <i class="material-icons right">add</i>
                </button>
            </div>
        </div>
    </div>

    <tags:error error="${error}" message="${error}"/>
    <div class="container">
        <div class="row">
        <h5 class="start" style="color: #80cbc4;">Reviews</h5>
        <img style="max-width: 64px" src="${pageContext.servletContext.contextPath}/images/star.png"/>
        <h5 style="color: #80cbc4;">${product.averageRate}</h5>
        </div>
    </div>

    <div class="container">
        <div id="comments-container">
            <c:forEach var="comment" items="${product.comments}">
                <tags:comments name="${comment.authorName}" comment="${comment.commentText}" rate="${comment.rate.value}"/>
            </c:forEach>
        </div>
    </div>

    <div class="container">
        <h5 class="start" style="color: #80cbc4;">Send yours</h5>
    </div>

    <div class="container">
        <div class="row">
            <div class="col s12">
                <form>
                    <input id="name" value="Name">
                    <textarea id="comment" class="materialize-textarea"></textarea>
                    <tags:stars/>
                </form>
            </div>
            <div class="col s12">
                <button id="send-comment" class="btn waves-effect waves-light" type="submit" name="action">Send
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
    </div>
</tags:master>