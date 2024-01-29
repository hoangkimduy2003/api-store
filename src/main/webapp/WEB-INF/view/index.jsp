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

                #ok:hover {
                    background-color: red;
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
                            <span class="bg-white text-dark rounded shadow px-2 me-2 alige">
                                O
                            </span>
                            <span class="text-white">man</span>
                        </h1>
                        <button class="d-md-none d-block btn close-btn px-1 py-0 text-white">
                            <i class="bi bi-list"></i>
                        </button>
                        <ul class="list-unstyled px-2">
                            <li class="active" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Thống kê</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Bán hàng tại quầy</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Tất cả đơn hàng</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Sản phẩm</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Thuộc tính sản phẩm</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Khuyến mãi</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Nhân viên</a>
                            </li>
                            <li class="" onclick="handleOnClickMenu(this)">
                                <a href="#" class="text-decoration-none px-3 py-2 my-2 d-block">Khách hàng</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="content">

                </div>
            </div>
            <script type="text/javascript">
                var handleOnClickMenu = function(e) {
                    console.log(document.querySelector("li.active").setAttribute("class",""));
                    e.classList.add("active");
                }
            </script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
        </body>

        </html>