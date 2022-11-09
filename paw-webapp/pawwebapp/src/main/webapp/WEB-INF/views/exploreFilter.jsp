<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:url value="/explore" var="explore"/>
<form action="${explore}" method="get" id="filter_form" class="filter-form">
    <sec:authorize access="hasRole('USER')">
        <div class="row" style="margin: 0; display:flex; justify-content:space-between;">
            <c:if test="${!favorite}">
                <div class="eco_input flex-column-center-align-vertical" style="padding:0; text-align:left;
                width:58%; font-size:20px;">
                    <label style="margin: 0 10px; font-size: 15px;" for="favorite">
                        <spring:message code="explore.filterform.favorite"/>
                    </label>
                </div>
                <div style="text-align: center;">
                    <input class="star star-table-cell" name="favorite" type="checkbox" id="favorite">
                </div>
            </c:if>
            <c:if test="${favorite}">
                <div class="eco_input" style="padding:0; text-align:left; width:58%;">
                    <label style="margin: 0 10px;" for="favorite2">
                        <spring:message code="explore.filterform.favorite"/>
                    </label>
                </div>
                <div style="text-align: right; width: 40%;">
                    <input class="star star-table-cell" name="favorite" type="checkbox" checked="checked" id="favorite2">
                </div>
            </c:if>
        </div>
    </sec:authorize>
    <div class="row margin-bottom-filterbox" style="margin-top: 0">
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
    <div class="row margin-top-25">
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
                        <option value="${area.id}">
                                <c:out value="${area.name}"/>
                        </option>
                    </c:if>
                    <c:if test="${chosenArea == area.id}">
                        <option selected value="${area.id}">
                            <c:out value="${area.name}"/>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <label><spring:message code="registerseller.area"/>:</label>
        </div>

    </div>
    <div class="row" id="#ecocontainer">
        <p class="filter-inputlabel eco_label" style="margin-left:0.75rem;">Ecotags:</p>
        <c:forEach items="${ecotagList}" var="ecotag">
            <c:if test="${ecotag.id != 0}">
                <c:if test="${boolTags[ecotag.id-1]}">
                    <div class="row eco_div">
                        <div class="col s2">
                            <div class="${ecotag.color} white-text chip chip_filter eco_chip_small">
                                <i class="left-align tiny tiny_filter material-icons">${ecotag.icon}</i>
                            </div>
                        </div>
                        <div class="eco_input" style="padding:0;">
                            <input style="display:table-cell; vertical-align:middle; margin: 0 6px;" class="ecotag-checkbox" name="strings" type="checkbox" value="${ecoStrings[ecotag.id-1]}" checked="checked" id="ecotag ${ecotag.id}">
                            <label style="margin: 0 3px;" for="ecotag ${ecotag.id}">
                                <spring:message code="${ecotag.tag}"/></label>
                        </div>
                    </div>
                </c:if>
                <c:if test="${!boolTags[ecotag.id-1]}">
                    <div class="row eco_div">
                        <div class="col s2">
                            <div class="${ecotag.color} white-text chip chip_filter eco_chip_small">
                                <i class="left-align tiny tiny_filter material-icons">
                                        <c:out value="${ecotag.icon}"/>
                                </i>
                            </div>
                        </div>
                        <div class="eco_input" style="padding:0;">
                            <input style="display:table-cell; vertical-align:middle; margin: 0 6px;" class="ecotag-checkbox" name="strings" type="checkbox" value="${ecoStrings[ecotag.id-1]}" id="ecotag ${ecotag.id}">
                            <label style="margin: 0 3px;" for="ecotag ${ecotag.id}">
                                <spring:message code="${ecotag.tag}"/>
                            </label>
                        </div>
                    </div>
                </c:if>
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
        <button type="submit" class="filter-button waves-effect waves-light btn">
            <spring:message code="explore.filterform.submit"/></button>
    </div>
</form>
</body>
<script>
    $(document).ready(function(){
        $('select').formSelect();
    });

</script>
</html>

