<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Price History">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <div class="container">
    <p><c:out value="${product.description}"/></p>
    <table>
        <thead>
        <tr>
            <td>Date</td>
            <td class = "price">Price</td>
        </tr>
        </thead>
        <c:forEach var="entry" items="${product.priceHistory.entrySet()}">
            <tr>
                <td class = "price"><fmt:formatDate value="${entry.getKey()}"/></td>
                <td>
                    <fmt:formatNumber value="${entry.getValue()}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
</tags:master>