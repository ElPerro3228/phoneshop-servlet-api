<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
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
          <a href="products?query=${param.query}&field=description&order=asc">${order eq "asc" ? "&#8657;" : "&#8659;"}</a>
          <a href="products?query=${param.query}&field=description&order=desc">${order eq "asc" ? "&#8659;" : "&#8657;"}</a>
        </td>
        <td class="price">
          Price
          <a href="products?query=${param.query}&field=price&order=asc">${order eq "asc" ? "&#8657;" : "&#8659;"}</a>
          <a href="products?query=${param.query}&field=price&order=desc">${order eq "asc" ? "&#8659;" : "&#8657;"}</a>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td><a href = "products/${product.id}">${product.description}</a></td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>