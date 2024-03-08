<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <title>Owen</title>
</head>

<body >
<div class="container-fluid" style="padding: 0;">
    <hr style="margin: 0;">
    <nav class="navbar navbar-expand-lg navbar-light bg-light nav-menu">
        <div class="container">
            <a class="navbar-brand" href="#"><img src="https://bom.so/1YAtAj" alt="#!/home"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" style="padding: 8px 24px;" href="#!/home">TRANG CHỦ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link " style="padding: 8px 24px;" href="#!/bestchoice">BÁN CHẠY</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" style="padding: 8px 24px;" href="#!/newProducts">HÀNG MỚI</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" style="padding: 8px 24px;" href="#!/allProduct">SẢN PHẨM</a>
                    </li>


                </ul>
                <ul class="d-flex navbar-nav mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a style="padding: 8px 24px;" class="nav-link" ng-if="userToken.id === ''"
                           href="/account/dang-nhap">ĐĂNG NHẬP</a>
                    </li>
                    <li class="nav-item dropdown me-2" ng-if="userToken.id != ''">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-start" aria-labelledby="navbarDropdownMenuLink">
                            <li><a class="dropdown-item" href="#!/user">
                                Thông tin tài khoản
                            </a>
                            </li>
                            <li><a class="dropdown-item" href="#!/order">Đơn hàng của
                                tôi</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li ng-click="logout()"><a class="dropdown-item" href="#!/login">Đăng xuất</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!/cart"><i class="bi bi-bag" style="position: relative;">
                                    <span ng-if="quantityGioHang !== 0" style="font-size: 10px;
                                        color: white;
                                         position: absolute;
                                         border-radius: 10px;
                                         background-color: #c9ae63  ;
                                         padding: 0px 5px;">
                                        {{quantityGioHang}}
                                    </span>
                        </i>
                        </a>
                    </li>
                </ul>

            </div>
        </div>
    </nav>
    <div class="row" style=" margin: 0px 0px 24px 0px;">
        <p style="text-align: center; margin: 0px; background-color: #cfd3cb;"><b>BẠN CÓ ĐỒ HÈ - OWEN CÓ <a href="#"
                                                                                                            style="color: gray;text-decoration: none;">Ở
            ĐÂY</a></b></p>
    </div>
    <jsp:include page="body.jsp"></jsp:include>
    <hr style="margin: 0;">
    <div class="container-fluid container-position" style="background-color: #fafafa; margin-bottom: 36px;">
        <div class="container">
            <footer>
                <div class="row">
                    <div class="col-md-4 col-xs-12 col-sm-6" style="margin-top: 24px;">
                        <div class="row">
                            <img src="https://bom.so/1YAtAj" style="width: 40%;">
                        </div>
                        <div class="row">
                            <b>CÔNG TY CỔ PHẦN THỜI TRANG KOWIL VIỆT NAM
                                Hotline: 1900 8079
                                8:30 - 19:00 tất cả các ngày trong tuần.</b>
                        </div>
                        <div class="row">
                                <span>VP Phía Bắc: Tầng 17 tòa nhà Viwaseen, 48 Phố Tố Hữu, Trung Văn, Nam Từ Liêm, Hà
                                    Nội.
                                    VP Phía Nam: 186A Nam Kỳ Khởi Nghĩa, Phường Võ Thị Sáu, Quận 3, TP.HCM</span>
                        </div>
                    </div>
                    <div class="col-md-2 col-xs-12 col-sm-6" style="margin-top: 24px;">
                        <div class="row">
                            <b>GIỚI THIỆU OWEN</b>
                        </div>
                        <div class="row"><a href="">Giới thiệu</a></div>
                        <div class="row"><a href="">Blog</a></div>
                        <div class="row"><a href="">Hệ thống cửa hàng</a></div>
                        <div class="row"><a href="">Liên hệ với Owen</a></div>
                        <div class="row"><a href="">Chính sách bảo mật</a></div>
                    </div>
                    <div class="col-md-2 col-xs-12 col-sm-6" style="margin-top: 24px;">
                        <div class="row">
                            <b>HỖ TRỢ KHÁCH HÀNG
                            </b>
                        </div>
                        <div class="row"><a href="">Hỏi đáp</a></div>
                        <div class="row"><a href="">Chính sách vận chuyển</a></div>
                        <div class="row"><a href="">Hướng dẫn chọn kích cỡ</a></div>
                        <div class="row"><a href="">Hướng dẫn thanh toán</a></div>
                        <div class="row"><a href="">Quy định đổi hàng</a></div>
                        <div class="row"><a href="">Hướng dẫn mua hàng</a></div>
                    </div>
                    <div class="col-md-4 col-xs-12 col-sm-6" style="margin-top: 24px;">
                        <div class="row"><b>KẾT NỐI</b></div>
                        <div class="row">
                            <div class="col-1"><a href=""><i class="bi bi-facebook"></i></a></div>
                            <div class="col-1"><a href=""><i class="bi bi-instagram"></i></a></div>
                            <div class="col-1"><a href=""><i class="bi bi-youtube"></i></a></div>
                        </div>
                        <br>
                        <div class="row">
                            <b>PHƯƠNG THỨC THANH TOÁN</b>
                        </div>
                        <div class="row">
                            <img src="https://bom.so/o6MnKl" style="width: 30%;" alt="">
                        </div>
                        <div class="row">
                            <a href="">
                                <img src="https://bom.so/NoUbmF" alt="" style="width: 20%;">
                            </a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>