<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>



<!DOCTYPE html>
<html>
<html lang="${cookie['lang'].value}"/>
<head>
    <meta charset="UTF-8">
    <title>Access Denied</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <jsp:include page="_menu.jsp" />
    <br/><br/>
    <h3>403</h3>
    <h3 style="color:red;"><fmt:message key="denied"/></h3>
</div>
</body>
</html>