<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 22/9/2022
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%--<div class="explore-filter-title"><spring:message code="explore.filterby"/></div>--%>
<c:url value="/explore" var="explore"/>
<form action="${explore}" method="get" id="filter_form" style="margin: 0 20px 20px 20px">
    <div class="row">
        <div class="input-field col s12 filterinputs">
            <input name="name" id="name" type="text" value="${name}">
            <label for="name"><spring:message code="explore.filterform.name"/></label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12 filterinputs" style="margin-top:2vh;">
            <select name="category" style="display: none">
                <c:if test="${chosenCategory != 0}">
                    <option value="" disabled><spring:message code="explore.select"/></option>
                </c:if>
                <c:if test="${chosenCategory == 0}">
                    <option value="" disabled selected><spring:message code="explore.select"/></option>
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
            <input id="maxPrice" name="maxPrice" type="text" value="${maxPrice}">
            <label for="maxPrice"><spring:message code="explore.filterform.maxprice"/></label>
        </div>
    </div>
    <div class="row">
        <p class="filter-inputlabel">Ecotags:</p>
        <c:forEach items="${ecotagList}" var="ecotag">
            <c:if test="${boolTags[ecotag.id-1]}">
                <div>
                    <input name="strings" type="checkbox" value="${ecoStrings[ecotag.id-1]}" checked="checked" id="ecotag ${ecotag.id}">
                    <label for="ecotag ${ecotag.id}"><spring:message code="${ecotag.tag}"/></label>
                </div>
            </c:if>
            <c:if test="${!boolTags[ecotag.id-1]}">
                <div>
                    <input name="strings" type="checkbox" value="${ecoStrings[ecotag.id-1]}" id="ecotag ${ecotag.id}">
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
    <div style="display:flex;justify-content: space-around;margin-top:5vh;">
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

