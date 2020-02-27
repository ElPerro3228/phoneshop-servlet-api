<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="comment" required="true" %>
<%@ attribute name="rate" required="true" %>

<div class="row">
    <div class="col s12 m6">
        <div class="col s3 m4 l3">
            <img width="65" height="65" src="https://www.android-kiosk.com/wp-content/themes/androidkioskcom/images/material_man1.png" class="circle">
        </div>
        <div class="col s9 m8 l9">
            <blockquote>
                <p class="grey-text text-darken-3">
                        ${comment}
                </p>
                <p style="font-weight:300;">${name}, Rate: <img src="${pageContext.servletContext.contextPath}/images/starsbig_${rate}.gif"></p>
                <div class="divider"></div>
            </blockquote>
        </div>
    </div>
</div>


