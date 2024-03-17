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
    <div id="slide_event" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <a href="">
                    <img src="https://bom.so/4DOZnf" class="d-block w-100" alt="...">
                </a>
            </div>
<%--            <div class="carousel-item">--%>
<%--                <a href="">--%>
<%--                    <img src="https://bom.so/Ia0OwM" class="d-block w-100" alt="...">--%>
<%--                </a>--%>
<%--            </div>--%>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#slide_event" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#slide_event" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div class="row" style="margin-top: 24px;">
        <div class="col-md-4 col-xs-12">
            <a href="">
                <img src="https://bom.so/KX9SWH" alt="" class="d-block w-100">
            </a>
        </div>
        <div class="col-md-4 col-xs-12">
            <a href="">
                <img src="https://bom.so/pTVrDK" alt="" class="d-block w-100">
            </a>
        </div>
        <div class="col-md-4 col-xs-12">
            <a href="">
                <img src="https://bom.so/Z60o3u" alt="" class="d-block w-100">
            </a>
        </div>
    </div>

    <div id="slide_slogan" class="carousel slide" style="margin-top: 24px;" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://bom.so/aNUXUN" class="d-block w-100" alt="...">
            </div>
    <%--            <div class="carousel-item">--%>
    <%--                <img src="https://bom.so/BZdUou" class="d-block w-100" alt="...">--%>
    <%--            </div>--%>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#slide_slogan" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#slide_slogan" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
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
                    <a href="/CTSP?productDetailId=${x.id}" class="col-md-3 col-xs-6 col-6 d-block">
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

    <div id="showAo2" class="carousel slide" style="margin-top: 42px;" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://bom.so/Sfmh2W" class="d-block w-100" alt="...">
            </div>
        </div>
    </div>

    <div class="row" style="margin: 12px 0px;">
        <div class="col-md-8 col-xs-6">
            <img src="https://bom.so/CU2s1M" class="d-block w-100" alt="...">
        </div>
        <div class="col-md-4 col-xs-6"
             style="background-color: #f1f2ef; display: flex; justify-items: center;align-items: center;">
            <div>
                <h5>ĐĂNG KÝ NHẬN BẢN TIN</h5>
                <p>Đừng bỏ lỡ hàng ngàn sản phẩm và chương trình <br> siêu hấp dẫn</p>
                <form>
                    <input type="email" placeholder="Nhập email của bạn"
                           style="width: 95%; height: 40px; background-color:#f1f2ef;border: none;border-bottom: 1px solid black; margin-bottom: 12px;">

                    <button style="color: white;background-color: black; width: 95%; height: 40px;">Đăng kí</button>
                </form>
            </div>
        </div>
    </div>
</div>