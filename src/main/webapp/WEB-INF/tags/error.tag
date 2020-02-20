<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>
<%@ attribute name="message" required="true" %>

<c:if test="${not empty error}">
    <p class="error">
        ${message}
    </p>
</c:if>

