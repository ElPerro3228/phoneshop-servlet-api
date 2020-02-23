<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<a href="products?query=<c:out value="${param.query}"/>&field=${field}&order=${order}">${order eq "asc" ? "&#8659;" : "&#8657;"}</a>


