<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<div class="recently-viewed-sidebar">
    <h6>recently watched</h6>
    <c:forEach var="product" items="${resentWatched}">
        <div class="sidebar-item">
            <div class="col l1 m1 s1">
                <div class="card">
                    <div class="card-image">
                        <img style="max-width: 128px;" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                        <span class="card-title-custom teal lighten-3" style="color: white"><strong><c:out value="${product.description}"/></strong></span>
                    </div>
                    <div class="card-action">
                        <a href = "${pageContext.servletContext.contextPath}/products/${product.id}">More</a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
