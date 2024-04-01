<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <title>Hello, world!</title>
    <style type="text/css">
        body {
            background: #eee;
        }

        #side_nav {
            background: #000;
            min-width: 250px;
            max-width: 250px;
        }

        .content {
            min-height: 100vh;
            width: 100%;
        }

        .sidebar li.active {
            background: #eee;
            border-radius: 8px;
        }

        .sidebar li.active a:hover {
            color: #000;
        }

        .sidebar li.active a {
            color: #000;
        }
        .sidebar li a {
            color: #fff;
        }
    </style>
</head>

<body>
<div class="main-container d-flex">
    <div class="sidebar" id="side_nav">
        <div class="header-box px-2 pt-3 pb-4">
            <h1 class="fs-4">
                <a href="/trang-chu" style="text-decoration: none; color: black">
                    <span class="bg-white text-dark rounded shadow px-2 ">
                    O
                </span>
                    <span class="text-white">man</span>
                </a>
            </h1>
            <button class="d-md-none d-block btn close-btn px-1 py-0 text-white">
                <i class="bi bi-list"></i>
            </button>
            <ul class="list-unstyled px-2">
                <li class="active">
                    <a href="/thong-ke" class="text-decoration-none px-3 py-2 my-2 d-block">Thống kê</a>
                </li>
                <li class="">
                    <a href="/tai-quay" class="text-decoration-none px-3 py-2 my-2 d-block">Bán hàng tại quầy</a>
                </li>
                <li class="">
                    <a href="/don-hang" class="text-decoration-none px-3 py-2 my-2 d-block">Tất cả đơn hàng</a>
                </li>
                <li class="">
                    <a href="/san-pham" class="text-decoration-none px-3 py-2 my-2 d-block">Sản phẩm</a>
                </li>
                <li class="dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Thuộc tính sản phẩm
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item text-dark" href="/mau-sac">Màu sắc</a></li>
                        <li><a class="dropdown-item text-dark" href="/kich-co">Kích cỡ</a></li>
                        <li><a class="dropdown-item text-dark" href="/loai-san-pham">Loại sản phẩm</a></li>
                        <li><a class="dropdown-item text-dark" href="/thuong-hieu">Thương hiệu</a></li>
                    </ul>
                </li>
                <li class="">
                    <a href="/khuyen-mai" class="text-decoration-none px-3 py-2 my-2 d-block">Khuyến mãi</a>
                </li>
                <li class="">
                    <a href="/nhan-vien" class="text-decoration-none px-3 py-2 my-2 d-block">Nhân viên</a>
                </li>
                <li class="">
                    <a href="/khach-hang" class="text-decoration-none px-3 py-2 my-2 d-block">Khách hàng</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="content" id="content">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Thống kê</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
        </nav>
        <jsp:include page="body.jsp"></jsp:include>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>

</html>