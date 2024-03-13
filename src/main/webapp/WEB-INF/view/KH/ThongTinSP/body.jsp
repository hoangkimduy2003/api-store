<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" ng-controller="detailController" style="margin-bottom: 36px;">
    <div class="row" style="margin-bottom: 24px;">
        <span style="font-size: 11px;"><a href="#!/" style="text-decoration: none; color: black;">TRANG CHỦ</a> | ${product.name}</span>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-xs-12 ">
                <div id="slide_detail" class="carousel slide w-75" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="/san-pham/img?fileName=${product.images[0]}" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item" ng-repeat="img in product.images">
                            <img src="/san-pham/img?fileName=${product.images[1]}" class="d-block w-100" alt="...">
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#slide_detail"
                            data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#slide_detail"
                            data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div class="col-md-6 col-xs-12">
                <div class="row">
                    <h6>${product.name}</h6>
                </div>
                <div class="row">
                    <p style="color: gray;">
                        ${product.price} đ
                    </p>
                </div>
                <div class="row">
                    <h6>Màu sắc: </h6>
                    <div class="row">
                        <div class="col-1" ng-repeat="detail in uniqueColors">
                            <input type="radio" name="color"  ng-value="detail.id">

                        </div>
                    </div>
                </div>
                <div class="row" style="margin: 12px 0px;">
                    <h6 style="padding: 0;">Kích cỡ: </h6>
                    <div class="row" style="padding: 0;">
                        <div class="col-2" ng-repeat="detail in uniqueSizes ">
                            <input type="radio" name="size"  ng-value="detail.id">
                            <br>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-top: 24px;">
                    <button type="button" class="btn btn-dark" ng-click="addCartDetails()" style="width: 60%">Thêm vào
                        giỏ hàng</button>
                </div>
                <div class="row" style="margin-top: 36px;">
                    <h6>Mô tả: </h6>
                    <hr style="width: 60%">
                    <div class="row" style="padding: 0;">
                        <div class="col-7">
                            ${product.description}
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>