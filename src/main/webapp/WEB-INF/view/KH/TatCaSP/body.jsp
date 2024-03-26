<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    .btn {
        width: 100%;
    }

    .btn button {
        width: 100%;
        border-radius: 5px;
        border: none;
        letter-spacing: 2px;
        font-weight: bold;
        margin-top: 6px;
        cursor: pointer;
    }

    .btn i img {
        margin-bottom: 3px;
    }

    .card {
        box-shadow: 0px 2px 5px black;
        transition: transform 0.5s;
        cursor: pointer;
    }

    .card:hover {
        transform: translateY(-15px);
    }

    .start {
        font-size: 20px;
    }

    .checked {
        color: rgba(255, 196, 0);
    }

    #menu {
        position: -webkit-sticky; /* For Safari */
        position: sticky;
        top: 56px;
        left: 0;
        z-index: 1000;
    }
</style>
<div class="container py-5">
    <div class="row">
        <div class="col-1" id="filter">
            <div id="menu">
                <button class="btn btn-dark w-50" style="width: 100% !important;" id="btnOpen" onclick="handleOnClickShow(true, this)">
                    <i class="bi bi-funnel" ></i>
                </button>
                <button class="btn btn-dark w-50" id="btnClose" style="display: none;width: 100% !important;" onclick="handleOnClickShow(false, this)">
                    <i class="bi bi-funnel" ></i>
                </button>
                <a class="btn btn-dark w-50" style="width: 100% !important; margin-top: 6px !important;" href="/tat-ca-sp">
                    <i class="bi bi-arrow-clockwise"></i>
                </a>
                <form id="searchForm" style="display: none" action="/tat-ca-sp" method="get">
                    <div class="row"  style="margin: 6px 0px" >
                        <input class="form-control" name="name" id="name" placeholder="Tên sản phẩm"
                               value="${searchProductDTO.name}">
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <select class="form-select" name="brandId" id="brand"
                                aria-label="Default select example">
                            <option value="-1" ${searchProductDTO.brandId == (-1) ? "selected" : ""}>--Thương hiệu--
                            </option>
                            <c:forEach items="${brands}" var="x">
                                <option value="${x.id}" ${searchProductDTO.brandId == x.id ? "selected" : ""}>${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <select class="form-select" name="categoryId" id="category">
                            <option value="-1" ${searchProductDTO.categoryId == (-1) ? "selected" : ""}>--Loại sản
                                phẩm--
                            </option>
                            <c:forEach items="${categories}" var="x">
                                <option value="${x.id}" ${searchProductDTO.categoryId == x.id ? "selected" : ""}>${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <select class="form-select" name="colorId" id="colorId">
                            <option value="-1" ${searchProductDTO.colorId == (-1) ? "selected" : ""}>--Màu sắc--
                            </option>
                            <c:forEach items="${colors}" var="x">
                                <option value="${x.id}" ${searchProductDTO.colorId == x.id ? "selected" : ""}>${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <select class="form-select" name="sizeId" id="sizeId">
                            <option value="-1" ${searchProductDTO.sizeId == (-1) ? "selected" : ""}>--Size--</option>
                            <c:forEach items="${sizes}" var="x">
                                <option value="${x.id}" ${searchProductDTO.sizeId == x.id ? "selected" : ""}>${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <select class="form-select" name="order" id="order">
                            <option ${searchProductDTO.order == 'no' ? "selected" : ""} value="no">--Sắp xếp--</option>
<%--                            <option ${searchProductDTO.order == 'p.totalQuantitySold desc' ? "selected" : ""} value="p.totalQuantitySold desc">Bán chạy</option>--%>
                            <option ${searchProductDTO.order == 'p.createAt desc' ? "selected" : ""} value="p.createAt desc">Sản phẩm mới</option>
                            <option ${searchProductDTO.order == 'p.priceSale' ? "selected" : ""} value="p.priceSale">Giá tăng dần</option>
                            <option ${searchProductDTO.order == 'p.priceSale desc' ? "selected" : ""} value="p.priceSale desc">Giá giảm dần</option>
                        </select>
                    </div>
                    <div class="row"  style="margin: 6px 0px" >
                        <input type="submit" value="Tìm" id="search-input" class="btn btn-dark">
                    </div>
                </form>
            </div>
        </div>
        <div class="col-11" id="showProduct">
            <div class="row">
                <c:forEach items="${products.data}" var="x">
                    <div class="col-md-3">
                        <a style="text-decoration: none; color: black" href="/CTSP?productDetailId=${x.id}">
                            <div class="card">
                                <img class="card-image-top anhShow" src="/san-pham/img?fileName=${x.images[0]}" style="height: 360px" alt=""/>
                                <div class="card-body">
                                    <h6 class="card-title text-center">${x.name}</h6>
                                    <strong style="${x.price == x.priceSale ? "display: none" : ""}" class="card-text text-center">
                                        <fmt:formatNumber pattern="#,###" value="${x.price}"/> VND
                                    </strong>
                                    <p class="card-text text-center">
                                        <fmt:formatNumber pattern="#,###" value="${x.priceSale}"/> VND
                                    </p>
                                    <p class="card-text text-center" style="font-size: 10px">
                                        Đã bán: <fmt:formatNumber pattern="#,###" value="${x.totalQuantitySold}"/>
                                    </p>
                                    <div class="btn">
                                        <button class="button"><i class="bi bi-cart-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
    <ul class="pagination">
        <c:forEach begin="1" end="${products.totalPages}" varStatus="loop">
            <li class="page-item">
                <a class="page-link" style="color: black"
                   href="/tat-ca-sp?page=${loop.begin + loop.count -2}&name=${searchProductDTO.name}&brandId=${searchProductDTO.brandId}&categoryId=${searchProductDTO.categoryId}&sizeId=${searchProductDTO.sizeId}&colorId=${searchProductDTO.colorId}&order=${searchProductDTO.order}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<script>
    function handleOnClickShow(isShow, e){
        var searchForm = document.getElementById("searchForm");
        var close = document.getElementById("btnClose");
        var open = document.getElementById("btnOpen");
        var filter = document.getElementById("filter");
        var showProduct = document.getElementById("showProduct");
        var anhShows = document.getElementsByClassName("anhShow");
        if (isShow) {
            searchForm.style.display = "block";
            e.style.display = "none";
            close.style.display = "block";
            filter.classList.remove("col-1");
            filter.classList.add("col-2");
            showProduct.classList.remove("col-11");
            showProduct.classList.add("col-10");
            for (var i = 0; i < anhShows.length; i++) {
                anhShows[i].style.height = "310px";
            }
        } else {
            searchForm.style.display = "none";
            e.style.display = "none";
            open.style.display = "block";
            filter.classList.remove("col-2");
            filter.classList.add("col-1");
            showProduct.classList.remove("col-10");
            showProduct.classList.add("col-11");
            for (var i = 0; i < anhShows.length; i++) {
                anhShows[i].style.height = "360px";
            }
        }
    }
</script>