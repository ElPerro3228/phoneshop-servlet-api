<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <p>
        Welcome to Expert-Soft training!
    </p>
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
</tags:master>