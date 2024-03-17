<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <a class="btn btn-warning" href="/san-pham"><i class="bi bi-sign-turn-left-fill"></i></a>
    <button type="button" onclick="preAction(null,0,-1,-1)" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Thêm
    </button>
    <div style="min-height: 320px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã chi tiết</th>
                <th scope="col">Màu sắc</th>
                <th scope="col">Kích cỡ</th>
                <th scope="col">Đơn giá</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.productDetailCode}</td>
                    <td>${x.color.name}</td>
                    <td>${x.size.name}</td>
                    <td>${x.priceSale}</td>
                    <td>${x.quantity}</td>
                    <td>
                        <button type="button" class="btn btn-warning" onclick="preAction(${x.id},'${x.name}',${x.status})"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Sửa
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
                <a class="page-link" href="/kich-co?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>