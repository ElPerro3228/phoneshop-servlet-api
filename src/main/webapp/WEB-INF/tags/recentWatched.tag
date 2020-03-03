<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="recently-viewed-sidebar">
    <h2>Recent watched</h2>
    <c:forEach var="product" items="${resentWatched}">
        <div class="sidebar-item">
            <div><img src="${product.imageUrl}" /></div>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
            <div><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></div>
        </div>
    </c:forEach>
</div>
