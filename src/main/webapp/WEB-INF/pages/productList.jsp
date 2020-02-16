<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
          Description
          <tags:sortLink field="description" order="asc"/>
          <tags:sortLink field="description" order="desc"/>
        </td>
        <td class="price">
          Price
          <tags:sortLink field="price" order="asc"/>
          <tags:sortLink field="price" order="desc"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td><a href = "products/${product.id}"><c:out value="${product.description}"/></a></td>
        <td class="price">
          <a href="products/priceHistory/${product.id}"><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>