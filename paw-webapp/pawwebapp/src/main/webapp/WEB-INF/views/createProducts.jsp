<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 11/9/2022
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="createproduct.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>

</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container createproducts create-header">
        <c:url value="/createProduct" var="postUrl"/>
        <form:form modelAttribute="productForm" method="post" action="${postUrl}" id="product_form" enctype="multipart/form-data">
            <div class="center create-center">
                <h5><spring:message code="createproduct.title"/></h5>
            </div>
            <div class="createbody">
                <div class="row">
                    <div class="col s4 input-field">
                        <form:label path="name"><spring:message code="createproduct.form.name"/>
                            <spring:message code="forms.obligatorysign"/></form:label>
                        <form:input path="name" type="text"/>
                        <form:errors path="name" element="p" cssClass="error"/>
                    </div>
                    <div class="col s4 input-field" >
                        <form:select path="category">
                            <form:option value="0" disabled="true"><spring:message code="explore.select"/></form:option>
                            <c:forEach items="${categories}" var="category">
                                <form:option value="${category.id}"><spring:message code="${category.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:label for="category" path="category"><spring:message code="createproduct.form.category"/></form:label>
                        <form:errors path="category" element="p" cssClass="error"/>
                    </div>
                    <div class="col s4 input-field">
                        <form:label path="stock"><spring:message code="createproduct.form.stock"/>
                            <spring:message code="forms.obligatorysign"/></form:label>
                        <form:input path="stock" type="number"/>
                        <form:errors path="stock" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col s4 input-field">
                        <form:label path="description"><spring:message code="createproduct.form.description"/></form:label>
                        <form:input path="description" type="text"/>
                        <form:errors path="description" element="p" cssClass="error"/>
                    </div>
                    <div class="col s4 input-field">
                        <form:label path="price"><spring:message code="createproduct.form.price"/>
                            <spring:message code="forms.obligatorysign"/></form:label>
                        <form:input path="price" type="number"/>
                        <form:errors path="price" element="p" cssClass="error"/>
                    </div>
                    <div class="col s4 input-field create-ecotag">
                        <spring:message code="createproduct.form.taglist" var="placeholder"/>
                        <form:select path="ecotag" multiple="true" data-placeholder="${placeholder}">
                            <c:forEach items="${tagList}" var="ecotag">
                                <form:option value="${ecotag.id}"><spring:message code="${ecotag.tag}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:label for="ecotag" path="ecotag"><spring:message code="createproduct.form.taglist"/></form:label>
                        <form:errors path="ecotag" element= "p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div class="createproduct_row create-button">
                <div class="file-field input-field">
                    <div class="decision-button waves-effect waves-light btn image_button">
                        <span><spring:message code="createproduct.form.image"/></span>
                        <form:input path="image" type="file"/>
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text">
                    </div>
                </div>
                <form:errors path="image" element="p" cssClass="error"/>
            </div>
            <div class="center create-submit">
                <button type="submit" class="decision-button waves-effect waves-light btn publish-button">
                    <spring:message code="createproduct.form.submit"/>
                </button>
            </div>
        </form:form>
    </div>

</body>
<script>


    $(document).ready(function () {

        var select = $('select[multiple]');
        var options = select.find('option');

        var div = $('<div />').addClass('selectMultiple');
        var active = $('<div />');
        var list = $('<ul />');
        var placeholder = select.data('placeholder');

        var span = $('<span />').text(placeholder).appendTo(active);

        options.each(function () {
            var text = $(this).text();
            if ($(this).is(':selected')) {
                active.append($('<a />').html('<em>' + text + '</em><i></i>'));
                span.addClass('hide');
            } else {
                list.append($('<li />').html(text));
            }
        });

        active.append($('<div />').addClass('arrow'));
        div.append(active).append(list);

        select.wrap(div);

        $(document).on('click', '.selectMultiple ul li', function (e) {
            var select = $(this).parent().parent();
            var li = $(this);
            if (!select.hasClass('clicked')) {
                select.addClass('clicked');
                li.prev().addClass('be|foreRemove');
                li.next().addClass('afterRemove');
                li.addClass('remove');
                var a = $('<a />').addClass('notShown').html('<em>' + li.text() + '</em><i></i>').hide().appendTo(select.children('div'));
                a.slideDown(400, function () {
                    setTimeout(function () {
                        a.addClass('shown');
                        select.children('div').children('span').addClass('hide');
                        select.find('option:contains(' + li.text() + ')').prop('selected', true);
                    }, 500);
                });
                setTimeout(function () {
                    if (li.prev().is(':last-child')) {
                        li.prev().removeClass('beforeRemove');
                    }
                    if (li.next().is(':first-child')) {
                        li.next().removeClass('afterRemove');
                    }
                    setTimeout(function () {
                        li.prev().removeClass('beforeRemove');
                        li.next().removeClass('afterRemove');
                    }, 200);

                    li.slideUp(400, function () {
                        li.remove();
                        select.removeClass('clicked');
                    });
                }, 600);
            }
        });

        $(document).on('click', '.selectMultiple > div a', function (e) {
            var select = $(this).parent().parent();
            var self = $(this);
            self.removeClass().addClass('remove');
            select.addClass('open');
            setTimeout(function () {
                self.addClass('disappear');
                setTimeout(function () {
                    self.animate({
                        width: 0,
                        height: 0,
                        padding: 0,
                        margin: 0
                    }, 300, function () {
                        var li = $('<li />').text(self.children('em').text()).addClass('notShown').appendTo(select.find('ul'));
                        li.slideDown(400, function () {
                            li.addClass('show');
                            setTimeout(function () {
                                select.find('option:contains(' + self.children('em').text() + ')').prop('selected', false);
                                if (!select.find('option:selected').length) {
                                    select.children('div').children('span').removeClass('hide');
                                }
                                li.removeClass();
                            }, 400);
                        });
                        self.remove();
                    })
                }, 300);
            }, 400);
        });

        $(document).on('click', '.selectMultiple > div .arrow, .selectMultiple > div span', function (e) {
            $(this).parent().parent().toggleClass('open');
        });

    });
</script>
</html>
