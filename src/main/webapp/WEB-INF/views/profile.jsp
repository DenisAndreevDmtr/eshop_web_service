<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Profile</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../sources/css/mystyles.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="../../sources/css/mystyles.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/2a28c847cb.js" crossorigin="anonymous"></script>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h2>User info</h2>

<div class="container-buttons">
    <a href="${contextPath}/search">
        <i class="fa-solid fa-magnifying-glass fa-3x" style="color: black"></i>
    </a>
    <a href="${contextPath}/home">
        <i class="fa-solid fa-house fa-3x" style="color: black"></i>
    </a>
    <a href="${contextPath}/cart">
        <i class="fa-solid fa-cart-shopping fa-3x" style="color: black"></i>
    </a>
</div>

<div class="container-fluid">
    <div class="column">
        <div class="card w-25 m-1">
            <div class="card-body">
                <img class="card-img" style="width:20%;height:20%"
                     src="${contextPath}/images/userinfo.png" alt="Userimage">
                <div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><b>User login:</b> <a>${loggedInUser.getLogin()}</a></li>
                        <li class="list-group-item"><b>User password:</b> <a>${loggedInUser.getPassword()}</a></li>
                        <li class="list-group-item"><b>User name:</b> <a>${loggedInUser.getName()}</a></li>
                        <li class="list-group-item"><b>User surname:</b> <a>${loggedInUser.getSurname()}</a></li>
                        <li class="list-group-item"><b>User dateborn:</b> <a>${loggedInUser.getDateBorn()}</a></li>
                        <li class="list-group-item"><b>user E-mail:</b> <a>${loggedInUser.getEMail()}</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-orders">
    <div class="container-orders-title">
        <p>History</p>
    </div>

    <c:if test="${not empty userOrders}">
        <c:forEach items="${userOrders}" var="userOrder">
            <div class="order">
                <div class="order-info">
                    <p>ID number order:<b> ${userOrder.getId()}</b></p>
                    <p> Sum order:<b> ${userOrder.getPriceOrder()}</b></p>
                    <p> Date order:<b> ${userOrder.getDateCreation()}</b></p>
                </div>
                <div class="row order-list">
                    <c:forEach items="${userOrder.getProducts().entrySet()}" var="product">
                        <div class="card-body order-product">
                            <img class="card-img" style="width:50px;height:50px"
                                 src="${contextPath}/images/${product.getKey().getImagePath()}" alt="Product image"/>
                            <ul class="list-group order-product-info">
                                <li class="list-group-item"><b>Name:</b> <a
                                        href="${contextPath}/product/${product.getKey().getId()}">${product.getKey().getName()}</a></li>
                                <li class="list-group-item"><b>Description:</b> <a>${product.getKey().getDescription()}</a>
                                </li>
                                <li class="list-group-item"><b>Price per unit:</b> <a>${product.getKey().getPrice()}</a></li>
                                <li class="list-group-item"><b>Quantity in order:</b> <a>${product.getValue()}</a></li>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <div class="pages">
        <c:if test="${not empty number_of_pages}">
            <c:forEach items="${number_of_pages}" var="page">
                <a href="${contextPath}/signin/profile/${page}">
                        ${page} </a>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>
