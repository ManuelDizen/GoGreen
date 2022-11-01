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
    <c:if test="${loggedEmail != null}">
        <c:if test="${user.email == loggedEmail}">
            <div class="row">
                <div class="center">
                    <a id="delete" class="waves-effect waves-light btn red accent-4 modal-trigger" href="#modal${article.id}">
                        <i class="material-icons">delete_forever</i>
                        <spring:message code="sellerprofile.delete.confirmbutton"/>
                    </a>
                </div>
            </div>
            <div id="modal${article.id}" class="modal green-modal">
                <div class="modal-content">
                    <h3 class="underline"><spring:message code="newspage.delete.confirm"/></h3>
                </div>
                <div class="row s12" style="display:flex; justify-content: center;">
                    <a class="modal-close waves-effect waves-green btn-flat white-margin">
                        <spring:message code="sellerprofile.delete.cancel"/>
                    </a>
                    <a class="waves-effect waves-light btn  red accent-4 left-margin" href=<c:url value="/deleteArticle/${article.id}"/>>
                        <i class="material-icons left">delete</i><spring:message code="sellerprofile.delete.confirmbutton"/>
                    </a>
                </div>
            </div>
        </c:if>
    </c:if>
</div>