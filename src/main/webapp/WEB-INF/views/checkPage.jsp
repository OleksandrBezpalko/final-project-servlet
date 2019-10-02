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
    <table class="table table-bordered">
        <tr>
            <th ><fmt:message key="nameD"/></th>
            <th ><fmt:message key="price"/></th>
            <th><fmt:message key="ammAdded"/></th>

        </tr>
        <c:forEach var="entry" items="${map}">

            <tr >
                <c:if test = "${cookie['lang'].value == 'en'}">
                    <td >${entry.key.name}</td>
                </c:if>
                <c:if test = "${cookie['lang'].value != 'en'}">
                    <td >${entry.key.nameUkr}</td>
                </c:if>
                    <%--  <td >${entry.key.name}</td>--%>
                <td >${entry.key.price}</td>
                <td >${entry.value}</td>
            </tr>
        </c:forEach>


    </table>

    <table class="table table-bordered">
        <tr>
            <th ><fmt:message key="product"/></th>
            <th ><fmt:message key="neededAm"/></th>
        </tr>
        <c:forEach var="entry2" items="${map2}">
            <tr >
                <td>${entry2.key}</td>
                <td>${entry2.value}</td>
            </tr>
        </c:forEach>
    </table>


    <form action="${pageContext.request.contextPath}/checkOrder_confirm" method="post">
        <button type="submit"  class="btn btn-danger"
                style="margin-top:30px;" name="ind" value=${index}><fmt:message key="confirm"/></button>
    </form>
</div>

</body>
</html>