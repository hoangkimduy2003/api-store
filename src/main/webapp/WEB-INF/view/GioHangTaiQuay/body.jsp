<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container m-2">
    <a id="donHangA" href="${billType == 1 ? '/tai-quay?idBill=' : '/don-hang/chi-tiet/' } + ${idBill}" style="display:none;"></a>
    <p style="font-size: 12px; color: #cfd3cb"><a style="text-decoration: none; color: #cfd3cb;" href="${billType == 1 ? '/tai-quay?idBill=' : '/don-hang/chi-tiet/' } + ${idBill}"  >Đơn hàng </a>/ Danh sách sản phẩm</p>
    <jsp:include page="modal.jsp"></jsp:include>
    <form id="searchForm" action="/danh-sach-san-pham/${idBill}/${billType}" method="get">
        <div class="row">
            <div class="col-1">
                <label for="productDetailCode" class="form-label">Mã SP</label>
                <input class="form-control" name="productDetailCode" id="productDetailCode" value="${searchProductDTO.productDetailCode}"
                       aria-describedby="emailHelp">
            </div>
            <div class="col-2">
                <label for="name" class="form-label">Tên SP</label>
                <input class="form-control" name="name" id="name" aria-describedby="emailHelp" value="${searchProductDTO.name}">
            </div>
            <div class="col-2">
                <label for="brand" class="form-label">Thương hiệu</label>
                <select class="form-select" name="brandId" id="brand"
                        aria-label="Default select example">
                    <option value="-1" ${searchProductDTO.brandId == (-1) ? "selected" : ""}>--Thương hiệu--</option>
                    <c:forEach items="${brands}" var="x">
                        <option value="${x.id}" ${searchProductDTO.brandId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <label for="category" class="form-label">Loại sản phẩm</label>
                <select class="form-select" name="categoryId" id="category">
                    <option value="-1" ${searchProductDTO.categoryId == (-1) ? "selected" : ""}>--Loại sản phẩm--</option>
                    <c:forEach items="${categories}" var="x">
                        <option value="${x.id}" ${searchProductDTO.categoryId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <label for="colorId" class="form-label">Màu sắc</label>
                <select class="form-select" name="colorId" id="colorId">
                    <option value="-1" ${searchProductDTO.colorId == (-1) ? "selected" : ""}>--Màu sắc--</option>
                    <c:forEach items="${colors}" var="x">
                        <option value="${x.id}" ${searchProductDTO.colorId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <label for="sizeId" class="form-label">Size</label>
                <select class="form-select" name="sizeId" id="sizeId">
                    <option value="-1" ${searchProductDTO.sizeId == (-1) ? "selected" : ""}>--Size--</option>
                    <c:forEach items="${sizes}" var="x">
                        <option value="${x.id}" ${searchProductDTO.sizeId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-1">
                <label for="search-input" class="form-label">Search</label>
                <input type="submit" value="Tìm" id="search-input" class="btn btn-info">
            </div>
        </div>
    </form>
    <table class="table">
        <thead>
        <tr>
            <th>Mã SP</th>
            <th>Tên sản phẩm</th>
            <th>Loại SP</th>
            <th>Thương hiệu</th>
            <th>Màu sắc</th>
            <th>Size</th>
            <th>Số lượng</th>
            <td>Giá</td>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.data}" var="x">
            <tr>
                <td>${x.productDetailCode}</td>
                <td>${x.product.name}</td>
                <td>${x.product.category.name}</td>
                <td>${x.product.brand.name}</td>
                <td>${x.color.name}</td>
                <td>${x.size.name}</td>
                <td><fmt:formatNumber pattern="#,###" value="${x.quantity}" /></td>
                <td><fmt:formatNumber pattern="#,###" value="${x.priceSale}" /></td>
                <td>
                    <button class="btn btn-success" onclick="preAction(${x.id},${x.quantity})" data-bs-toggle="modal" data-bs-target="#exampleModal"><i class="bi bi-cart-plus"></i></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination">
        <c:forEach begin="1" end="${list.totalPages}" varStatus="loop">
            <li class="page-item">
                <a class="page-link" href="#" onclick="changePage(${loop.begin + loop.count -1})">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<script>
    function handleOnHref(e){
        var a = document.getElementById("donHangA");
        a.click();
    }
    function changePage(pageNumber) {
        var form = document.getElementById("searchForm");
        var action = form.getAttribute("action");
        action = action.split("?")[0]; // Remove existing query parameters
        var inputs = form.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            action += (i === 0 ? "?" : "&") + inputs[i].name + "=" + inputs[i].value;
        }
        var selects = form.getElementsByTagName("select");
        for (var i = 0; i < selects.length; i++) {
            action += "&" + selects[i].name + "=" + selects[i].value;
        }
        action += "&page=" + (pageNumber - 1);
        window.location.href = action;
    }
</script>