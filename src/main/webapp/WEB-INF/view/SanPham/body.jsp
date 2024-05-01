<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    .switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
    }

    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
    }

    input:checked + .slider {
        background-color: #2196F3;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px #2196F3;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(26px);
        -ms-transform: translateX(26px);
        transform: translateX(26px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 34px;
    }

    .slider.round:before {
        border-radius: 50%;
    }
</style>
<div class="container m-2">
    <form id="searchForm" action="/san-pham" method="get">
        <div class="row">
            <div class="col-2">
                <input class="form-control" name="name" id="name" aria-describedby="emailHelp" placeholder="Tên sản phẩm" value="${searchProductDTO.name}">
            </div>
            <div class="col-2">
                <select class="form-select" name="brandId" id="brand"
                        aria-label="Default select example">
                    <option value="-1" ${searchProductDTO.brandId == (-1) ? "selected" : ""}>--Thương hiệu--</option>
                    <c:forEach items="${brands}" var="x">
                        <option value="${x.id}" ${searchProductDTO.brandId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <select class="form-select" name="categoryId" id="category">
                    <option value="-1" ${searchProductDTO.categoryId == (-1) ? "selected" : ""}>--Loại sản phẩm--</option>
                    <c:forEach items="${categories}" var="x">
                        <option value="${x.id}" ${searchProductDTO.categoryId == x.id ? "selected" : ""}>${x.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2">
                <select class="form-select" name="status" id="status">
                    <option value="-1" ${searchProductDTO.status == (-1) ? "selected" : ""}>--Trạng thái--</option>
                    <option value="1" ${searchProductDTO.status == (1) ? "selected" : ""}>Hoạt động</option>
                    <option value="0" ${searchProductDTO.status == (0) ? "selected" : ""}>Không hoạt động</option>
                </select>
            </div>
            <div class="col-1">
                <input type="submit" value="Tìm kiếm" id="search-input" class="btn btn-info">
            </div>
            <div class="col-2">
                <button type="button" onclick="preAction(null,null,-1,-1,null,null,null,null,null)" class="btn btn-dark" data-bs-toggle="modal"
                        data-bs-target="#exampleModal">
                    Thêm sản phẩm
                </button>
            </div>
        </div>
    </form>
    <jsp:include page="modal.jsp"></jsp:include>

    <div style="min-height: 540px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Ảnh</th>
                <th scope="col">Tên sản phẩm</th>
                <th scope="col">Loại sản phẩm</th>
                <th scope="col">Thương hiệu</th>
                <th scope="col">Giá bán</th>
                <th scope="col">Giá nhập</th>
                <th scope="col">Số lượng trong kho</th>
                <th scope="col">Số lượng đã bán</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td><img src="/san-pham/img?fileName=${x.images[0]}" style="width: 60px; height: 80px"></td>
                    <td>${x.name}</td>
                    <td>${x.category.name}</td>
                    <td>${x.brand.name}</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.price}"/> VND</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.importPrice}"/> VND</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.totalQuantity}"/></td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.totalQuantitySold}"/></td>
                    <td>
                        <label class="switch">
                            <input id="active${x.id}" type="checkbox" ${x.status == 1 ? "checked" : ""} onclick="handleOnChangeToggleActiveProduct('${x.id}')">
                            <span class="slider round"></span>
                        </label>
                    </td>
                    <td>
                        <a href="/chi-tiet-sp/${x.id}" class="btn btn-info">
                            <i class="bi bi-eye"></i>
                        </a>
                        <button type="button" class="btn btn-warning"
                                onclick="preAction(${x.id},'${x.name}',${x.brand.id}, ${x.category.id}, ${x.price}, ${x.importPrice}, '${x.description}',null, '${x.status}')"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            <i class="bi bi-pencil"></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <ul class="pagination">
        <c:forEach begin="1" end="${list.totalPages}" varStatus="loop">
            <li class="page-item">
                <a class="page-link" href="/san-pham?page=${loop.begin + loop.count -2}&name=${searchProductDTO.name}&brandId=${searchProductDTO.brandId}&categoryId=${searchProductDTO.categoryId}&status=${searchProductDTO.status}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>

<script>
    async function handleOnChangeToggleActiveProduct(id){
        var isCheck = true;
        var active = document.getElementById("active"+id).checked;
            await axios.get("/san-pham/changeStatus/"+ id + "/" + (active ? 1 : 0)).then(res => {
                if(res.status == 200){
                    toastr.success("Thay đổi trạng thái thành công");
                }
            }).catch(e => {
                console.log(e);
                toastr.error("Vui lòng thêm sản phẩm chi tiết trước!");
                isCheck = false;
            })
        if(!isCheck) {
            document.getElementById("active"+id).checked = false;
        }
    }
</script>