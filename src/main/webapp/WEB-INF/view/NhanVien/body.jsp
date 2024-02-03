<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <button type="button" onclick="preAction(null,null,null,null,null)" class="btn btn-primary" data-bs-toggle="modal"
            data-bs-target="#exampleModal">
        Thêm
    </button>
    <div style="min-height: 320px">
        <table class="table w-50">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã</th>
                <th scope="col">Họ và tên</th>
                <th scope="col">SĐT</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.userCode}</td>
                    <td>${x.fullName}</td>
                    <td>${x.phoneNumber}</td>
                    <td>
                        <button type="button" class="btn btn-warning"
                                onclick="preAction(${x.id},'${x.userCode}','${x.fullName}'
                                        ,'${x.phoneNumber}','${x.email}')"
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
                <a class="page-link" href="/nhan-vien?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>