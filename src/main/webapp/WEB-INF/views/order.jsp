<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<!doctype html >

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="${cookie['lang'].value}" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Order</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body class="container">
<jsp:include page="_menu.jsp" />
<c:if test="${not empty param.cookieLocale}">
    <c:redirect url="/order"/>
</c:if>

<c:if test = "${moneyBalance!=null}">
    <fmt:message key="uBalance"/> ${moneyBalance}
</c:if>

<c:if test="${notEnought!=null}">
    <h3 style="color: red"><fmt:message key="notEnought"/></h3>
</c:if>
<table class="table table-bordered">
    <tr>
        <th  ><fmt:message key="nameD"/></th>
        <th  ><fmt:message key="price"/></th>
        <th><fmt:message key="ammAdded"/></th>
        <th><fmt:message key="delete"/></th>

    </tr>
    <c:forEach var="entry" items="${mapD}">


        <tr >
            <c:if test = "${cookie['lang'].value == 'en'}">
                <td >${entry.key.name}</td>
            </c:if>
            <c:if test = "${cookie['lang'].value != 'en'}">
                <td >${entry.key.nameUkr}</td>
            </c:if>
            <td >${entry.key.price}</td>
            <td >${entry.value}</td>
            <td>

                <form action="${pageContext.request.contextPath}/removeD" method="post">
                    <button type="submit"  class="btn btn-danger"
                            name="dishId" value=${entry.key.id}><fmt:message key="delete"/> </button>
                </form>

            </td>
        </tr>
    </c:forEach>
</table>

<h2><fmt:message key="price"/></h2>
<h3 >${amount}</h3>


<form action="${pageContext.request.contextPath}/addedOrder" method="post">
    <button type="submit"  class="btn btn-danger"
            style="margin-top:30px"<%-- name="dishId" value=${entry.key.id}--%>><fmt:message key="confirm"/>   </button>
</form>
</body>
</html>
