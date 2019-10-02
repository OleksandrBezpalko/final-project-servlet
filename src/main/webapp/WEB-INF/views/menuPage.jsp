<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<html lang="${cookie['lang'].value}"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title  >Menu </title>
    <link rel="stylesheet" href="/lib/bootstrap-4.3.1/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/lib/bootstrap-4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <jsp:include page="_menu.jsp" />

    <c:if test="${not empty param.cookieLocale}">
        <c:redirect url="/menu"/>
    </c:if>

    <c:if test = "${moneyBalance!=null}">
        <text><fmt:message key="uBalance"/> ${moneyBalance}</text>
    </c:if>

    <text style="color: red">${message}</text>

    <h1 ><fmt:message key="menu"/></h1>

    <table class="table table-bordered">
        <tr>
            <th  ><fmt:message key="foto"/></th>
            <th  ><fmt:message key="nameD"/></th>
            <th  ><fmt:message key="price"/></th>
            <th  ><fmt:message key="products"/></th>
            <th ><fmt:message key="getNow"/></th>
        </tr>
        <c:forEach var="dish" items="${elements}">
            <tr >
                <td  ><img src="${dish.fileName}" alt="noImage" width="100" height="100"></td>

                <c:if test = "${cookie['lang'].value == 'en'}">
                    <td >${dish.name}</td>
                </c:if>
                <c:if test = "${cookie['lang'].value != 'en'}">
                    <td >${dish.nameUkr}</td>
                </c:if>

                <td >${dish.price}</td>
                <td >
                    <c:forEach var="product" items="${dish.productsForDish}">
                        <text>${product.product} </text>
                    </c:forEach>

                </td>

                <td>
                    <form action="${pageContext.request.contextPath}/addToCard" method="post">
                        <button type="submit"  class="btn btn-dark"
                                style="margin-top:-10px" name="dish" value=${dish.id}><fmt:message key="addDish"/>  </button>
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
    <div class="row">
        <div class="col-sm-12 col-md-5">
            <div class="dataTables_info">
                <fmt:message key="pagination.showing"/>
                ${(page) * size - size + 1 }
                <fmt:message key="pagination.to"/>
                ${((page) * size) > elementsCount ? elementsCount : ((page) * size)}
                <fmt:message key="pagination.of"/>
                ${elementsCount}
                <fmt:message key="pagination.entries"/>
            </div>
        </div>
        <div class="col-sm-12 col-md-7">
            <c:if test="${elementsCount > size}">
                <div class="dataTables_paginate paging_simple_numbers text-right">
                    <ul class="pagination" style="display:inline-flex">
                        <li class="paginate_button page-item previous ${page < 2 ? 'disabled' : ''}">
                            <a href="/menu?page=${page-1}" class="page-link">
                                <fmt:message key="pagination.previous"/>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${pagesCount}" var="i">
                            <li class="paginate_button page-item ${page == i ? 'active' : ''}">
                                <a href="/menu?page=${i}" class="page-link">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="paginate_button page-item next ${page == pagesCount ? 'disabled' : ''}">
                            <a href="/menu?page=${page+1}" class="page-link">
                                <fmt:message key="pagination.next"/>
                            </a>
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>


</div>

</body>
</html>
