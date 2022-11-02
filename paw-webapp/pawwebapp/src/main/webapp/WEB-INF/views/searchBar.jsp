<div class="container">
    <div class="full-width search-style">
        <div class="search-container">
            <c:url value="/explore" var="searchUrl"/>
            <form action="${searchUrl}" method="get" id="search-form" class="landing-form-alignment">
                <div class="input-field col filterinputs filter-category">
                    <select name="category" style="display: none">
                        <option value="" selected><spring:message code="explore.any"/></option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}"><spring:message code="${category.name}"/></option>
                        </c:forEach>
                    </select>
                    <label><spring:message code="explore.filterform.category"/></label>
                </div>
                <div class="input-field col filterinputs">
                    <input name="name" id="name" type="text" value="${name}">
                    <label for="name"><spring:message code="explore.filterform.name"/></label>
                </div>
                <div class="filter-submit search-submit">
                    <button class="btn-large waves-effect waves-teal grow_on_hover btn-floating white"
                            type="submit">
                        <i class="material-icons black-text">search</i>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>