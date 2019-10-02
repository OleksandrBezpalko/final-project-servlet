<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<html>
<html lang="${cookie['lang'].value}"/>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <jsp:include page="_menu.jsp" />
    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/user"/>
    </c:if>

    <c:forEach var="user" items="${users}">
        <div class="mt-4" style="border-left: 2px solid red; padding-left:10px;">
            <strong>${user.username}</strong> -

            <c:forEach var="role" items="${user.roles}">
                ${role}
            </c:forEach>

            <form action="${pageContext.request.contextPath}/user" method="post">
                <input type="hidden" name="id" value="${user.id}">
                <input type="checkbox" name="roles" id="adRole${user.id}" value="ADMIN">
                <label for="adRole${user.id}"> ADMIN</label>

                <br/>
                <input class="btn btn-dark" type="submit">
            </form>

        </div>

    </c:forEach>
</div>
</body>
</html>
