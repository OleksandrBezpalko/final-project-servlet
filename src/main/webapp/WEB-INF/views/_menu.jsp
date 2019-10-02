<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-5">
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/addMoney"><fmt:message key="addMoney"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/menu"><fmt:message key="menu"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/order"><fmt:message key="toOrder"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user_confirm"><fmt:message key="payOrders"/></a>
            </li>
            <c:if test="${loginedUser.roles.contains('ADMIN')}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="forAdmin"/>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/adminOrder"><fmt:message key="forAdmin"/></a>
                        <a class="dropdown-item" href="/add">
                            <fmt:message key="dishContrl"/>
                        </a>
                        <a class="dropdown-item" href="/user">
                            <fmt:message key="usCtrl"/>
                        </a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <c:if test="${loginedUser == null}">
                <li class="nav-item">
                    <a class="nav-link" href="/login"><fmt:message key="lPage"/></a>
                </li>
            </c:if>
            <c:if test="${loginedUser != null}">
                <li class="nav-item">
                    <strong class="nav-link" style="color:#fff">${loginedUser.userName}</strong>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout"><fmt:message key="logout"/></a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="?cookieLocale=en" ><fmt:message key="en" /></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="?cookieLocale=ua" ><fmt:message key="uk" /></a>
            </li>
        </ul>
    </div>
</nav>