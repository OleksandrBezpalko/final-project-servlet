<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>


<!doctype html>
<html>
<html lang="${cookie['lang'].value}"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title  >Document</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <jsp:include page="_menu.jsp" />
    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/addMoney"/>
    </c:if>
    <h3  style="color: red" >${notEnoughtMoney}</h3>
    <%-- <text th:if="${moneyBalance}==null"></text>--%>
    <h1 <%--th:unless="${moneyBalance}==null"--%>><fmt:message key="uBalance"/> ${moneyBalance}</h1>


    <form action="${pageContext.request.contextPath}/addBalance"  method="post"
          style="max-width: 600px;margin-bottom: 30px;margin-top: 50px" ng-submit="form.$valid"
    >

        <div class="form-group">
            <label for="moneyToAdd" ><fmt:message key="sumToAdd"/></label>
            <input class="form-control" id="moneyToAdd"  name="toAdd" type="number">

        </div>


        <button type="submit" class="btn btn-dark"
                style="margin-top:30px;margin-left: 10px"
                ng-disabled="form.$invalid">
            <fmt:message key="addMoney"/>
        </button>
    </form>


</div>
<script type="text/javascript" src="/js/locale.js"></script>
</body>
</html>