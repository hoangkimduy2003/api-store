<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    a {
        text-decoration: none;
    }

    .services {
        width: 100%;
    }

    .single-img {
        border-radius: 5px;
        display: flex;
        flex-direction: column-reverse;
        flex-wrap: nowrap;
        width: 110%;
        height: 450px;
        overflow: hidden;
        box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.3);
        transform: translate(0, 0);
        transition: .3s;
    }

    .single-img:hover {
        transform: translate(0, -9px);
    }

    .img-all {
        background-size: cover;
        background-position: center center;
    }

    .img-text {
        background: linear-gradient(to top, rgba(0, 0, 0, 0.9), rgba(216, 216, 216, 0));
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

    .services button {
        background: #252525;
        color: #fff;
        padding: 10px 35px;
        border: none;
        border-radius: 50px;
        line-height: 14px;
        display: inline-block;
        margin-bottom: 10px;
    }

    .single-img:hover .img-text {
        transform: translate(0, 0);
    }

</style>
<div class="container">
    <div class="row" style="margin-bottom: 24px;">
        <span style="font-size: 11px;"><a href="/trang-chu"
                                          style="text-decoration: none; color: black; font-size: 10px;">TRANG
                CHỦ</a> | GIỎ HÀNG</span>
    </div>
    <div class="row">
        <h4>GIỎ HÀNG</h4>
    </div>
    <div class="row">
        <div class="col-md-9 col-xs-12">

            <table class="table">
                <thead>
                <tr>
                    <th scope="col">
                        Sản phẩm
                    </th>
                    <th></th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Giá (VND)</th>
                    <th scope="col">Tổng tiền (VND)</th>
                    <th scope="col">Sửa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cart.cartDetails}" var="cartDetail">
                    <tr>
                        <td scope="row">
                            <a href="" style="text-decoration: none; color: black">
                                <div class="row">
                                    <img src="/san-pham/img?fileName=${cartDetail.productDetail.product.images[0]}"
                                         style="width: 130px;" class="d-block" alt="">
                                    <div class="col-6" style="font-size: 10px">
                                        <p>${cartDetail.productDetail.name}</p>
                                        <p>Màu sắc: ${cartDetail.productDetail.color.name}</p>
                                        <p>Kích cỡ: ${cartDetail.productDetail.size.name}</p>
                                    </div>
                                </div>
                            </a>
                        </td>
                        <td>
                            <a class="btn btn-danger" href="/gio-hang/delete/${cartDetail.id}" onclick="handleOnClickAction('D')"><i class="bi bi-trash3"></i></a>
                        </td>
                        <td><fmt:formatNumber pattern="#,###" value="${cartDetail.quantity}"/></td>
                        <td>
                            <p><fmt:formatNumber pattern="#,###" value="${cartDetail.productDetail.priceSale}"/></p>
                        </td>
                        <td><fmt:formatNumber pattern="#,###" value="${cartDetail.productDetail.priceSale * cartDetail.quantity}"/></td>
                        <td>
                            <button class="btn btn-warning">
                                <i class="bi bi-pencil"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <div class="row">
                <div class="col-12">
                    <div style="width: 30%; float: right;">
                        <p>
                            Tổng sản phẩm: <fmt:formatNumber pattern="#,###" value="${cart.totalProduct}"/><br>
                            Tạm tính: <fmt:formatNumber pattern="#,###" value="${cart.totalMoney}"/> VND
                        </p>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="col-6"><a href="/trang-chu" class="btn btn-light w-75">TIẾP TỤC MUA HÀNG</a></div>
                <div class="col-6" ng-if="cart.totalProduct != 0"><a style="float: right;" href="#!/pay"
                                                                     class="btn btn-dark w-75">THANH TOÁN</a></div>
            </div>

        </div>

        <div class="col-md-3">
            <img src="https://bom.so/o0KZdk" alt="" class="w-100 d-block">
        </div>
    </div>

    <!-- Hàng chạy -->
    <div class="row">
        <div class="row">
            <h4 style="text-align: center; margin: 36px 0px;">HÀNG BÁN CHẠY</h4>
        </div>
        <div class="wrapper">
            <div class="services row">
                <c:forEach items="${listBestSell.data}" var="x">
                    <a href="/CTSP?productDetailId=${x.id}" class="col-md-3 col-xs-6 col-6 d-block">
                        <span class="single-img img-all"
                              style="background-image: url('/san-pham/img?fileName=${x.images[0]}')">
                            <span class="img-text">
                                <h6>
                                  ${x.name} <i class="bi bi-heart"></i>
                                </h6>
                                <div>Giá: <fmt:formatNumber pattern="#,###" value="${x.price}"/> VND</div>
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
                    <a href="/CTSP?productDetailId=${x.id}" class="col-md-3 col-xs-6 col-6 d-block">
                        <span class="single-img img-all"
                              style="background-image: url('/san-pham/img?fileName=${x.images[0]}')">
                            <span class="img-text">
                                <h6>
                                  ${x.name} <i class="bi bi-heart"></i>
                                </h6>
                                <div>Giá: <fmt:formatNumber pattern="#,###" value="${x.price}"/> VND</div>
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
<script>
    var handleOnClickAction = function (action){
        if(action == "D"){
            if(!confirm("Bạn có muốn xoá sản phẩm khỏi giỏ hàng không?")){
                return false;
            }
            return true;
        }
    }
</script>

