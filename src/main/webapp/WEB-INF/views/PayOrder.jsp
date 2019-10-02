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
    <title>Document</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<c:if test="${not empty param.cookieLocale}">
    <c:redirect url="/user_confirm"/>
</c:if>

<div class="container">
    <jsp:include page="_menu.jsp" />
    <h3 style="color: red">${erroMes}</h3>

    <h1><fmt:message key="notConfOrders"/></h1>
    <div>
        <form  action="${pageContext.request.contextPath}/checkOrderUser"  method="post">
            <c:forEach var="indx" items="${ind}">
                <button type="submit" class="btn btn-dark"
                        style="margin-top:30px"
                        name="ind"  value=${indx}><fmt:message key="orderID"/> ${indx}</button>
            </c:forEach>
        </form></div>


</div>

</body>
</html>