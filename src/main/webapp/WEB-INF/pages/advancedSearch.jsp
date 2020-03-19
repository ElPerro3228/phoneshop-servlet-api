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
        <table>
          <tr>
            <td>Description</td>
            <td><input name="query" value="<c:out value="${param.query}"/>"/></td>
            <td>
              <input type="radio" id="cart" name="searchType" value="all">
              <label for="cart">All words</label>
              <input type="radio" id="cash" name="searchType" value="any">
              <label for="cash">Any word</label>
            </td>
          </tr>
          <tr>
            <td>Max price</td>
            <td><input name="maxPrice" value="<c:out value="${param.maxPrice}"/>"></td>
          </tr>
          <tr>
            <td>Min Price</td>
            <td><input name="minPrice" value="<c:out value="${param.minPrice}"/>"></td>
          </tr>
        </table>
        <h5 style="color: red">${error}</h5>
        <button class="btn waves-effect waves-light pulse"><i class="small material-icons">search</i></button>
      </form>

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