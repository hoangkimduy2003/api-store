<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <button type="button" onclick="preAction(null,null,-1,-1,null,null,null,null)" class="btn btn-dark" data-bs-toggle="modal"
            data-bs-target="#exampleModal">
        Thêm sản phẩm
    </button>
    <div style="min-height: 540px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Ảnh</th>
                <th scope="col">Tên sản phẩm</th>
                <th scope="col">Loại sản phẩm</th>
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
                    <td>${x.price}</td>
                    <td>${x.importPrice}</td>
                    <td>${x.totalQuantity}</td>
                    <td>${x.totalQuantitySold}</td>
                    <td>${x.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                    <td>
                        <a href="/chi-tiet-sp/${x.id}" class="btn btn-info">
                            <i class="bi bi-eye"></i>
                        </a>
                        <button type="button" class="btn btn-warning"
                                onclick="preAction(${x.id},'${x.name}',${x.brand.id}, ${x.category.id}, ${x.price}, ${x.importPrice}, '${x.description}',null)"
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
                <a class="page-link" href="/san-pham?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>