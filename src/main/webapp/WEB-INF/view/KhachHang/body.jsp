<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <form action="/khach-hang" method="get">
        <div class="row">

            <div class="col-1">
                <label for="phoneNumber" class="form-label"></label>
                <button type="button" onclick="preAction(null,null,1,null,null)" class="btn btn-primary"
                        style="width: 80px"
                        data-bs-toggle="modal"
                        data-bs-target="#exampleModal">
                    Thêm
                </button>
            </div>
            <div class="col-1">
                <label for="phoneNumber" class="form-label"></label>
                <button class="btn btn-dark" type="submit" style="width: 80px">Tìm kiếm</button>
            </div>
            <div class="col-3">
                <input class="form-control" name="phoneNumber" value="${searchUserDTO.phoneNumber}" placeholder="Số điện thoại" id="phoneNumber">
            </div>
        </div>

    </form>


    <div style="min-height: 320px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Tên nhân viên</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Email</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.fullName}</td>
                    <td>${x.phoneNumber}</td>
                    <td>${x.email}</td>
                    <td>${x.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                    <td>
                        <button type="button" class="btn btn-warning"
                                onclick="preAction(${x.id},'${x.fullName}',${x.status},'${x.phoneNumber}','${x.email}')"
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
                <a class="page-link"
                   href="/nhan-vien?page=${loop.begin + loop.count -2}&phoneNumber=${searchUserDTO.phoneNumber}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>