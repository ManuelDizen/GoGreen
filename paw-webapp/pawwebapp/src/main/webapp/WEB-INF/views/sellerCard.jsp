<html>
  <body>
    <div class="card product-card">
      <a style="height:50%" href="<c:url value="/sellerPage/${seller.id}"/>">
        <div class="seller-card-image-container">
          <c:if test="${seller.user.image != null}">
            <img class="image-restrain" src="<c:url value="/image/${seller.user.image.id}"/>">
          </c:if>
          <c:if test="${seller.user.image == null}">
            <img class="image-restrain" src="<c:url value="/resources/images/logo.png"/>">
          </c:if>
        </div>
      </a>
      <div class="card-content">
        <a href="<c:url value="/sellerPage/${seller.id}"/>"
           class="card-title product-card-title">
          <c:out value="${seller.user.firstName}"/>
        </a>
        

      </div>

    </div>
  </body>
</html>
