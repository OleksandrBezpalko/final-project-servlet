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

<div class="container">

    <jsp:include page="_menu.jsp" />

    <c:if test="${notEnought!=null}">
        <h3 style="color: red"><fmt:message key="notEnought"/></h3>
    </c:if>



    <h1><fmt:message key="orderID"/> ${index}</h1>
    <table class="table table-bordered">
        <tr>
            <th > <fmt:message key="nameD"/></th>
            <th > <fmt:message key="price"/></th>
            <th> <fmt:message key="ammAdded"/></th>

        </tr>
        <c:forEach var="entry" items="${map}">
            <tr >
                <c:if test = "${cookie['lang'].value == 'en'}">
                    <td >${entry.key.name}</td>
                </c:if>
                <c:if test = "${cookie['lang'].value != 'en'}">
                    <td >${entry.key.nameUkr}</td>
                </c:if>
                <td>${entry.key.price}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>

    <div class="container" style="max-width: 90%">
        <h2><fmt:message key="price"/> ${price}</h2>


        <form action="${pageContext.request.contextPath}/checkOrderUser_confirm"
              method="post">
            <input type="hidden" name="price" value=${price}>
            <button type="submit"  class="btn btn-dark"
                    style="margin-top:30px" name="ind" value=${index}> <fmt:message key="confirm"/></button>
        </form>
    </div>
</div>

</body>
</html>
