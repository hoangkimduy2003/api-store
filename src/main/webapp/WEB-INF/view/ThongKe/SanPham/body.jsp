<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    :root {
        --main-bg-color: #009d63;
        --main-text-color: #009d63;
        --second-text-color: #bbbec5;
        --second-bg-color: #c1efde;
    }

    .primary-text {
        color: var(--main-text-color);
    }

    .second-text {
        color: var(--second-text-color);
    }

    .primary-bg {
        background-color: var(--main-bg-color);
    }

    .secondary-bg {
        background-color: var(--second-bg-color);
    }

    .rounded-full {
        border-radius: 100%;
    }

    #wrapper {
        overflow-x: hidden;
        background-image: linear-gradient(
                to right,
                #baf3d7,
                #c2f5de,
                #cbf7e4,
                #d4f8ea,
                #ddfaef
        );
    }

    #sidebar-wrapper {
        min-height: 100vh;
        margin-left: -15rem;
        -webkit-transition: margin 0.25s ease-out;
        -moz-transition: margin 0.25s ease-out;
        -o-transition: margin 0.25s ease-out;
        transition: margin 0.25s ease-out;
    }

    #sidebar-wrapper .sidebar-heading {
        padding: 0.875rem 1.25rem;
        font-size: 1.2rem;
    }

    #sidebar-wrapper .list-group {
        width: 15rem;
    }

    #page-content-wrapper {
        min-width: 100vw;
    }

    #wrapper.toggled #sidebar-wrapper {
        margin-left: 0;
    }

    #menu-toggle {
        cursor: pointer;
    }

    .list-group-item {
        border: none;
        padding: 20px 30px;
    }

    .list-group-item.active {
        background-color: transparent;
        color: var(--main-text-color);
        font-weight: bold;
        border: none;
    }

    @media (min-width: 768px) {
        #sidebar-wrapper {
            margin-left: 0;
        }

        #page-content-wrapper1 {
            min-width: 0;
            width: 100%;
        }

        #page-content-wrapper {
            min-width: 0;
            width: 100%;
        }

        #wrapper.toggled #sidebar-wrapper {
            margin-left: -15rem;
        }
    }
</style>
<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <a href="/thong-ke" class="nav-link " id="nav-home-tab" aria-selected="false">Tổng</a>
        <a href="/thong-ke/doanh-thu" class="nav-link" id="nav-profile-tab" aria-selected="false">Doanh thu</a>
        <a href="/thong-ke/don-hang" class="nav-link" id="nav-contact-tab"  aria-selected="false">Hoá đơn</a>
        <a href="/thong-ke/san-pham" class="nav-link active" id="nav-prodcut-tab"  aria-selected="true">Sản phẩm</a>
    </div>
</nav>
<div>
    <div class="container-fluid px-4">
        <div class="row g-3 my-2">
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.sumProduct}" /></h3>
                        <p class="fs-6" style="margin: 0px">Tổng sản phẩm</p>
                    </div>
                    <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalProductActive}" /></h3>
                        <p class="fs-6" style="margin: 0px">Đang hoạt động</p>
                    </div>
                    <i
                            class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalProductSapHet}" /></h3>
                        <p class="fs-6" style="margin: 0px">Còn dưới 10 sản phẩm</p>
                    </div>
                    <i style="background-color: yellow" class="fas fa-truck fs-1 primary-text border rounded-full p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalProductHet}" /></h3>
                        <p class="fs-6" style="margin: 0px">Hết hàng</p>
                    </div>
                    <i style="background-color: red" class="fas fa-chart-line fs-1 primary-text border rounded-full p-3"></i>
                </div>
            </div>
        </div>
    </div>
    <div class="container m-2">
        <div class="row">
            <div class="col-6">
                <h6>Danh sách sản phẩm bán chạy</h6>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Đã bán</th>
                        <th scope="col">Còn lại</th>
                        <th scope="col">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rate.pageProductHot.content}" var="product">
                        <tr>
                            <td>${product.name}</td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.priceSale}" /></td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.totalQuantitySold}" /></td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.totalQuantity}" /></td>
                            <td>${product.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <ul class="pagination">
                    <c:forEach begin="1" end="${rate.pageProductHot.totalPages}" varStatus="loop">
                        <li class="page-item">
                            <a class="page-link"
                               href="/thong-ke/san-pham?page=${loop.begin + loop.count -2}&page2=${searchThongKeDTO.page2}">
                                    ${loop.begin + loop.count -1}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-6">
                <h6>Danh sách sản phẩm mới</h6>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Đã bán</th>
                        <th scope="col">Còn lại</th>
                        <th scope="col">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rate.pageProductNew.content}" var="product">
                        <tr>
                            <td>${product.name}</td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.priceSale}" /></td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.totalQuantitySold}" /></td>
                            <td><fmt:formatNumber pattern="#,###" value="${product.totalQuantity}" /></td>
                            <td>${product.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <ul class="pagination">
                    <c:forEach begin="1" end="${rate.pageProductNew.totalPages}" varStatus="loop">
                        <li class="page-item">
                            <a class="page-link"
                               href="/thong-ke/san-pham?page2=${loop.begin + loop.count -2}&page=${searchThongKeDTO.page}">
                                    ${loop.begin + loop.count -1}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
