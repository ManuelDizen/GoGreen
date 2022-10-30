<div class="big-news-card">
    <div class="row">
        <c:if test="${displayName == 1}">
            <div class="col s6" style="text-align:left; padding-left:20px;
            font-size: 25px; font-weight:bolder;">
                <a href="<c:url value="/sellerPage/${article.seller.id}"/>" class="white-text underline">
                    <spring:message code="newssellername" arguments="${article.seller.user.firstName},
                ${article.seller.user.surname}"/>
                </a>
            </div>
            <div class="col s6"  style="text-align: right; padding-right:20px; font-size: 20px;">
                <c:out value="${article.parsedDateTime}"/>
            </div>
        </c:if>
        <c:if test="${displayName == 0}">
            <div class="col s12"  style="text-align: right; padding-right:20px; font-size: 20px;">
                <c:out value="${article.parsedDateTime}"/>
            </div>
        </c:if>
    </div>
    <c:if test="${article.image.id != 0}">
        <div class="row">
            <div class="col s6" style="width:50%; text-align: justify;
                        min-height:200px; margin-left:0; padding-left:20px; word-break:break-word;">
                <c:out value="${article.message}"/>
            </div>
            <div class="col s6" style="width:50%; text-align: center;">
                <div class="news-image">
                    <img src="<c:url value="/image/${article.image.id}"/>" class="materialboxed image-restrain">
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${article.image.id == 0}">
        <div class="row">
            <div class="col s12" style="text-align: justify;
                        margin-left:0; padding-left:20px; word-break:break-word;">
                <c:out value="${article.message}"/>
            </div>
        </div>
    </c:if>
</div>