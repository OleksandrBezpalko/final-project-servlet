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
    <title>Login</title>

    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">

    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/login"/>
    </c:if>

    <jsp:include page="_menu.jsp" />

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <h3><fmt:message key="lPage" /></h3>

            <c:if test="${errorString!=null}">
                <h3 style="color: red"><fmt:message key="errorString"/></h3>
            </c:if>

            <c:if test="${errorString1!=null}">
                <h3 style="color: red"><fmt:message key="errorString1"/></h3>
            </c:if>

            <form style="margin-bottom: 30px" name="form" action="${pageContext.request.contextPath}/login" autocomplete="off"
                  method="post"novalidate >
                <div class="form-group">
                    <label for="userName"><fmt:message key="username"/></label>
                    <input type="text"
                           name="userName"
                           class="form-control"
                           id="userName"
                           required
                           ng-model="auth.email">

                </div>
                <div class="form-group">
                    <label for="password" ><fmt:message key="password"/></label>
                    <input type="password"
                           name="password"
                           class="form-control"
                           id="password"
                           required
                           ng-model="auth.password">


                </div>
                <button type="submit" value="Sign In" class="btn btn-dark" style="margin-top:30px"
                        ng-click="getUser()">
                    <fmt:message key="enter"/>

                </button>
            </form>
            <a class="btn btn-secondary" href="/registrate"><fmt:message key="register"/></a>
        </div>
    </div>
</div>

</body>
</html>