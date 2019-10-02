<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>


<!doctype html>
<html >
<html lang="${cookie['lang'].value}"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <jsp:include page="_menu.jsp" />


    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/adminOrder"/>
    </c:if>


    <table class="table table-bordered">
        <tr>
            <th ><fmt:message key="product"/></th>
            <th ><fmt:message key="amHave"/></th>
            <th><fmt:message key="minAmount"/></th>

        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td> ${product.product}</td>
                <td >${product.amountHave}</td>
                <td >${product.minAmount}</td>

            </tr>
        </c:forEach>
    </table>
    <div>
        <form action="${pageContext.request.contextPath}/replenish_stock_of_products"  method="post">

            <button type="submit" value="Registrate" class="btn btn-dark mb-5">
                <fmt:message key="addAmHave"/>
            </button>

        </form>
    </div>



    <h1><fmt:message key="notConfOrders"/></h1>
    <div>
        <form action="${pageContext.request.contextPath}/checkOrder" method="post">
            <c:forEach var="indx" items="${ind}">
                <button type="submit"  class="btn btn-dark"
                        style="margin-top:30px;"
                        name="ind"  value=${indx}
                ><fmt:message key="orderID"/> ${indx}</button>
            </c:forEach>
        </form></div>


</div>

</body>
</html>
