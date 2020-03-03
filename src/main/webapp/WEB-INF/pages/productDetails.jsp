<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <c:out value="${cart}"/>
    <tags:error error="${error}" message="There is error"/>
    <c:if test="${not empty param.success}">
        <p style="color: green">
            <c:out value="Added to cart successfully"/>
        </p>
    </c:if>
    <table>
        <tr>
            <td>
                Description
            </td>
            <td>
                <c:out value="${product.description}"/>
            </td>
        </tr>
        <tr>
            <td>
                Price
            </td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}"><c:out value="${product.price}"/></a>
            </td>
        </tr>
        <tr>
            <td>
                Stock
            </td>
            <td>
                <c:out value="${product.stock}"/>
            </td>
        </tr>
        <tr>
            <td>
                Image
            </td>
            <td>
                <img src = "${product.imageUrl}">
            </td>
        </tr>
    </table>
    <form method="post" action="${product.id}">
        <p>
            <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="price"/>
            <button>Add to cart</button>
        </p>
    </form>
    <tags:error error="${error}" message="${error}"/>
</tags:master>