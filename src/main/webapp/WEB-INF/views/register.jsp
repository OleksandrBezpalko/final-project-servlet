<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>


<!DOCTYPE html>
<html  xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
       xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="${cookie['lang'].value}"/>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body ng-app="login_form">

<div class="container">

    <jsp:include page="_menu.jsp" />


    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/registrate"/>
    </c:if>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2 ><fmt:message key="welcome"/> </h2>

            <c:if test="${errorMessage!=null}">
                <h3 style="color: red"><fmt:message key="usExist"/></h3>
            </c:if>


            <form style="margin-bottom: 30px" name="form" action="${pageContext.request.contextPath}/registrate"
                  autocomplete="off" method="post" novalidate>

                <div class="form-group">
                    <label for="exampleInputEmail1" ><fmt:message key="username"/> </label>
                    <input type="text"
                           name="username"
                           class="form-control"
                           id="exampleInputEmail1"
                           required
                           ng-model="auth.email">
                </div>


                <div class="form-group">
                    <label for="exampleInputLastname" ><fmt:message key="lastname"/></label>
                    <input type="text"
                           class="form-control"
                           name="lastname"
                           id="exampleInputLastname"
                           required />

                </div>

                <div class="form-group">
                    <label for="exampleInputPassword1" ><fmt:message key="password"/></label>
                    <input type="password"
                           class="form-control"
                           name="password"
                           id="exampleInputPassword1"
                           required
                           ng-model="auth.password">
                </div>



                <button type="submit" value="Registrate" class="btn btn-dark" style="margin-top:30px"
                        ng-click="getUser()">
                    <fmt:message key="enter"/>
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
