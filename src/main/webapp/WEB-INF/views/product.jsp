<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../sources/css/mystyles.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/2a28c847cb.js" crossorigin="anonymous"></script>


    <script>
        function productAddedToShoppingCartMsg() {
            window.confirm("Продукт будет добавлен в корзину!");
        }
    </script>

</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h2>${product.getName()}</h2>

<div class="container-buttons">
    <a href="${contextPath}/search">
        <i class="fa-solid fa-magnifying-glass fa-3x" style="color: black"></i>
    </a>
    <a href="${contextPath}/home">
        <i class="fa-solid fa-house fa-3x" style="color: black"></i>
    </a>
    <a href="${contextPath}/signin/profile/1">
        <i class="fa-solid fa-user fa-3x" style="color: black"></i>
    </a>
    <a href="${contextPath}/cart">
        <i class="fa-solid fa-cart-shopping fa-3x" style="color: black"></i>
    </a>
</div>

<div class="container-fluid mb-4">
    <div class="card w-25 m-1" type="product">
        <div class="card-body">
            <img class="card-img" style="width:45%;height:100%"
                 src="${contextPath}/images/${product.getImagePath()}" alt="Product image">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><b>Description:</b> <a>${product.getDescription()}</a>
                </li>
                <li class="list-group-item"><b>Price:</b> <a>${product.getPrice()}</a></li>
            </ul>
        </div>
        <div class="card-button">
            <a href="${contextPath}/cart/add?product_id=${product.getId()}">
                <i class="fa-solid fa-cart-arrow-down fa-2x " style="color: black"
                   onclick="productAddedToShoppingCartMsg()"></i>
            </a>
        </div>
    </div>
</div>


</body>
</html>
