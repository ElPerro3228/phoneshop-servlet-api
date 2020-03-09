<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<div class="col s3 m3 l3">
    <a class="waves-effect waves-light btn" href="products?query=${param.query}&field=${field}&order=${order}">
        ${field eq "price" ? "Price" : "Description"}<i class="material-icons right">${order eq "asc" ? "arrow_downward" : "arrow_upward"}</i>
    </a>
</div>

