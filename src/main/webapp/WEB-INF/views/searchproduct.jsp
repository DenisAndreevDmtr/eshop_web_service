<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Search product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../sources/css/mystyles.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/2a28c847cb.js" crossorigin="anonymous"></script>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h2>Search product</h2>

<div class="container-buttons">
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
<form method="post" action="${contextPath}/search">
    <div class="search-bar">
        <input type="text" class="form-control" id="searchParametr" placeholder="What do u want to search?"
               name="searchParametr"
               required>
        <button type="submit">
            <i class="fa-solid fa-magnifying-glass fa-3x" type="submit" style="color: black"></i>
        </button>
    </div>
    <div class="form-group">
        <div class="invalid-feedback">Type something</div>
        <div class="filter">
            <p>Filter</p>
            <select class="select-category" name="allcategories">
                <option> All categories</option>
                <c:if test="${not empty categories}">
                    <c:forEach items="${categories}" var="category">
                        <option>${category.getName()}</option>
                    </c:forEach>
                </c:if>
            </select>
            <div class="pricerange">
                <input type="text" class="form-control w-25" id="pricefrom" placeholder="Price from"
                       name="pricefrom">
                <input type="text" class="form-control w-25" id="priceupto" placeholder="Price up to"
                       name="priceupto">
            </div>
        </div>
    </div>
</form>

<div class="container-searchresult">
    <c:forEach items="${search_result}" var="searchitem">
        <div class="card searchitems" type="searchitem">
            <div>
                <p>
                    <a class="product-name"
                       href="${contextPath}/product/${searchitem.getId()}">${searchitem.getName()}</a>
                </p>
                <div class="card-body">
                    <img class="card-img" style="width:45%;height:100%"
                         src="images/${searchitem.getImagePath()}" alt="Product image">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><b>Name:</b> <a>${searchitem.getName()}</a></li>
                        <li class="list-group-item"><b>Description:</b> <a>${searchitem.getDescription()}</a></li>
                        <li class="list-group-item"><b>Price:</b> <a>${searchitem.getPrice()}</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
