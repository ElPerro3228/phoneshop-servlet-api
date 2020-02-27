<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}">
      <img style="max-width: 64px" src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
  </header>
  <main>
    <jsp:doBody/>
    <tags:recentWatched/>
  </main>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.10.2.js"
          type="text/javascript"></script>
  <script src="${pageContext.servletContext.contextPath}/scripts/addToCart.js">
  </script>

  <script>
    let url = "${pageContext.servletContext.contextPath}/comment";
    let id = ${product.id};
  </script>

  <script src = "${pageContext.servletContext.contextPath}/scripts/sendComment.js">
  </script>
</body>
</html>