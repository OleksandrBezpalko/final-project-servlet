
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!doctype html>
<html lang="${cookie['lang'].value}"/>
<head>
    <meta charset="UTF-16">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
    <title>Add Dish</title>
</head>
<body>

<c:if test="${not empty param.cookieLocale}">
    <c:redirect url="/add"/>
</c:if>

<div class="container">
    <jsp:include page="_menu.jsp" />


    <c:if test="${error!=null}">
        <h3 style="color: red"><fmt:message key="${error}"/></h3>
    </c:if>


    <h2 ><fmt:message key="addDish"/></h2>
    <form action="${pageContext.request.contextPath}/add" method="post"
          style="max-width: 600px;margin-bottom: 30px;margin-top: 50px">

        <div class="form-group">
        </div>

        <div class="form-group">
            <label for="fileName" > <fmt:message key="fileName"/> </label>
            <input class="form-control" type="text" name="fileName" id="fileName">
        </div>

        <div class="form-group">
        </div>

        <div class="form-group">
            <label for="nameUkr" ><fmt:message key="nameDukr"/></label>
            <input class="form-control" type="text" name="nameUkr" id="nameUkr">
        </div>

        <div class="form-group">
            <label for="name" ><fmt:message key="nameD"/></label>
            <input class="form-control" type="text" name="name" id="name">
        </div>

        <div class="form-group">
            <label for="price" ><fmt:message key="price"/></label>
            <input class="form-control" type="number" name="price" id="price">
        </div>
        <div class="form-group">

            <div class="check-box" >


                <c:forEach var="product" items="${products}">
                    <input type="checkbox" name="prod" value="${product.product}" id="${product.product}">
                    <label for="${product.product}"> ${product.product}</label>
                </c:forEach>

            </div>

        </div>

        <button type="submit" value="Sign In" class="btn btn-success"
                style="margin-top:30px;margin-left: 10px">
            <fmt:message key="addDish"/>
        </button>
    </form>
    <h2  style="margin-left: 15px"><fmt:message key="delete"/></h2>

    <form action="${pageContext.request.contextPath}/remove" method="post">
        <c:forEach var="dish" items="${dishes}">
            <div class="check-box">
                <input type="checkbox" name="ds" value=${dish.id} id=${dish.id}>
                <label for=${dish.id} >${dish.name}</label>
            </div>
        </c:forEach>
        <button type="submit" value="Sign In" class="btn btn-danger"
                style="margin-top:30px;margin-left: 15px"
        >
            <fmt:message key="delete"/>
        </button>
    </form>
</div>
<script type="text/javascript" src="/js/locale.js"></script>
</body>
</html>