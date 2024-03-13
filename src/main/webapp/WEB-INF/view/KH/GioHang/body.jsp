<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    a{
        text-decoration: none;
    }

    .services{
        width: 100%;
    }
    .single-img{
        border-radius: 5px;
        display: flex;
        flex-direction: column-reverse;
        flex-wrap: nowrap;
        width: 110%;
        height: 450px;
        overflow: hidden;
        box-shadow: 5px 5px 20px rgba(0,0,0,0.3);
        transform: translate(0, 0);
        transition: .3s;
    }
    .single-img:hover{
        transform: translate(0, -9px);
    }
    .img-all{
        background-size: cover;
        background-position: center center;
    }
    .img-text{
        background: linear-gradient(to top,rgba(0, 0, 0, 0.9),rgba(216, 216, 216, 0));
        padding: 0 10px 5px 10px;
        width: 100%;
        height: auto;
        position: relative;
        transform: translate(0, 110px);
        line-height: 25px;
        transition: 0.5s ease;
        display: inline-block;
        text-align: center;
        color: #fff;
    }

    .services button{
        background: #252525;
        color: #fff;
        padding: 10px 35px;
        border: none;
        border-radius: 50px;
        line-height: 14px;
        display: inline-block;
        margin-bottom: 10px;
    }
    .single-img:hover .img-text{
        transform: translate(0, 0);
    }

</style>
<div class="container">


    <!-- Hàng chạy -->
    <div class="row">
        <div class="row">
            <h4 style="text-align: center; margin: 36px 0px;">HÀNG BÁN CHẠY</h4>
        </div>
        <div class="wrapper">
            <div class="services row">
                <c:forEach items="${listBestSell.data}" var="x">
                    <a href="#" class="col-md-3 col-xs-6 col-6 d-block">
                        <span class="single-img img-all" style="background-image: url('/san-pham/img?fileName=${x.images[0]}')">
                            <span class="img-text">
                                <h6>
                                  ${x.name} <i class="bi bi-heart"></i>
                                </h6>
                                <div>Giá: <fmt:formatNumber pattern="#,###" value="${x.price}" /> VND</div>
                                <button><i class="bi bi-cart-plus"></i></button>&nbsp;
                                <button>Mua ngay</button>
                            </span>
                       </span>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <!-- Hàng Mới -->
    <div class="row">
        <div class="row">
            <h4 style="text-align: center; margin: 36px 0px;">HÀNG MỚI</h4>
        </div>
        <div class="wrapper">
            <div class="services row">
                <c:forEach items="${listNew.data}" var="x">
                    <a href="#" class="col-md-3 col-xs-6 col-6 d-block">
                        <span class="single-img img-all" style="background-image: url('/san-pham/img?fileName=${x.images[0]}')">
                            <span class="img-text">
                                <h6>
                                  ${x.name} <i class="bi bi-heart"></i>
                                </h6>
                                <div>Giá: <fmt:formatNumber pattern="#,###" value="${x.price}" /> VND</div>
                                <button><i class="bi bi-cart-plus"></i></button>&nbsp;
                                <button>Mua ngay</button>
                            </span>
                       </span>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <br/>
</div>

