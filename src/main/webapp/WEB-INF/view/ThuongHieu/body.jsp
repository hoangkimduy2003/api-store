<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <button type="button" onclick="preAction(null,null,1)" class="btn btn-primary" data-bs-toggle="modal"
            data-bs-target="#exampleModal">
        Thêm
    </button>
    <div style="min-height: 320px">
        <table class="table w-50">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Tên thương hiệu</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.name}</td>
                    <td>${x.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                    <td>
                        <button type="button" class="btn btn-warning"
                                onclick="preAction(${x.id},'${x.name}',${x.status})"
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
                <a class="page-link" href="/thuong-hieu?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>