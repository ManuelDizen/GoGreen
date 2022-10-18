<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:url value="/explore" var="explore"/>
<form action="${explore}" method="get" id="filter_form" class="filter-form">
    <div class="row">
        <div class="input-field col s12 filterinputs">
            <input name="name" id="name" type="text" value="${name}">
            <label for="name"><spring:message code="explore.filterform.name"/></label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12 filterinputs filter-category">
            <select name="category" style="display: none">
                <c:if test="${chosenCategory != 0}">
                    <option value=""><spring:message code="explore.select"/></option>
                </c:if>
                <c:if test="${chosenCategory == 0}">
                    <option value="" selected><spring:message code="explore.select"/></option>
                </c:if>
                <c:forEach items="${categories}" var="category">
                    <c:if test="${chosenCategory != category.id}">
                        <option value="${category.id}"><spring:message code="${category.name}"/></option>
                    </c:if>
                    <c:if test="${chosenCategory == category.id}">
                        <option selected value="${category.id}"><spring:message code="${category.name}"/></option>
                    </c:if>
                </c:forEach>
            </select>
            <label><spring:message code="explore.filterform.category"/></label>
        </div>

    </div>
    <div class="row">
        <div class="input-field col s12 filterinputs">
            <input id="maxPrice" name="maxPrice" type="number" min="0" value="${maxPrice}">
            <label for="maxPrice"><spring:message code="explore.filterform.maxprice"/></label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12 filterinputs filter-margin">
            <select name="areaId" style="display: none">
                <c:if test="${chosenArea != 0}">
                    <option value=""><spring:message code="explore.select"/></option>
                </c:if>
                <c:if test="${chosenArea == 0}">
                    <option value="" selected><spring:message code="explore.select"/></option>
                </c:if>
                <c:forEach items="${areas}" var="area">
                    <c:if test="${chosenArea != area.id}">
                        <option value="${area.id}">${area.name}</option>
                    </c:if>
                    <c:if test="${chosenArea == area.id}">
                        <option selected value="${area.id}">${area.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <label><spring:message code="registerseller.area"/>:</label>
        </div>

    </div>
    <div class="row">
        <p class="filter-inputlabel">Ecotags:</p>
        <c:forEach items="${ecotagList}" var="ecotag">
            <c:if test="${boolTags[ecotag.id]}">
                <div>
                    <input name="strings" type="checkbox" value="${ecoStrings[ecotag.id]}" checked="checked" id="ecotag ${ecotag.id}">
                    <label for="ecotag ${ecotag.id}"><spring:message code="${ecotag.tag}"/></label>
                </div>
            </c:if>
            <c:if test="${!boolTags[ecotag.id]}">
                <div>
                    <input name="strings" type="checkbox" value="${ecoStrings[ecotag.id]}" id="ecotag ${ecotag.id}">
                    <label for="ecotag ${ecotag.id}"><spring:message code="${ecotag.tag}"/></label>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <div class="row" style="display:none;">
        <div class="input-field col s12">
            <input style="display: none" id="sort" name="sort" type="text" value="${sort}">
            <label style="display: none" for="sort"><spring:message code="explore.filterform.maxprice"/></label>
        </div>
    </div>
    <div class="row" style="display:none;">
        <div class="input-field col s12">
            <input style="display: none" id="direction" name="direction" type="text" value="${direction}">
            <label style="display: none" for="direction"><spring:message code="explore.filterform.maxprice"/></label>
        </div>
    </div>
    <div class="filter-submit">
        <button type="submit" class="decision-button waves-effect waves-light btn"><spring:message code="explore.filterform.submit"/></button>
    </div>
</form>
</body>
<script>
    $(document).ready(function(){
        $('select').formSelect();
    });

</script>
</html>

