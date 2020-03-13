<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <div class="container">
    <h5 class="start" style="color: #80cbc4;">Products in cart: <c:out value="${miniCart}"/> </h5>
  </div>
  <div class="container">
      <form>
        <div class="row">
          <div class="col l10 m10 s12">
            <input name="query" value="<c:out value="${param.query}"/>"/>
          </div>
          <div class="col l2 m2 s1">
            <button class="btn waves-effect waves-light pulse"><i class="small material-icons">search</i></button>
          </div>
        </div>
      </form>

    <div class="row">
      <tags:sortLink field="description" order="asc"/>
      <tags:sortLink field="description" order="desc"/>
      <tags:sortLink field="price" order="asc"/>
      <tags:sortLink field="price" order="desc"/>
    </div>

    <div class="row">
      <c:forEach var="product" items="${products}">
        <div class="col l4 m6 s12">
          <div class="card">
            <div class="card-image">
              <img style="max-width: 256px;" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
              <span class="card-title-custom teal lighten-3" style="color: white"><strong><c:out value="${product.description}"/></strong></span>
            </div>
            <div class="card-content">
              <p>
                <a href="products/priceHistory/${product.id}"><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></a>
              </p>
            </div>
            <div class="card-action">
              <a href = "products/${product.id}">More</a>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</tags:master>