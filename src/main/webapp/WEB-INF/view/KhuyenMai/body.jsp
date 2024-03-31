<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <button type="button" onclick="preAction(null,null,1)" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Thêm
    </button>
    <div style="min-height: 320px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã giảm giá</th>
                <th scope="col">Giảm giá theo</th>
                <th scope="col">Mức giảm</th>
                <th scope="col">Đơn giá tối thiểu</th>
                <th scope="col">Giảm giá tối đa</th>
                <th scope="col">Ngày bắt đầu</th>
                <th scope="col">Ngày kết thúc</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.voucherCode}</td>
                    <td>${x.voucherType == 1 ? "Phần trăm" : "Giá"}</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.promotionalLevel}" /> ${x.voucherType == 1 ? "%" : "VND"}</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.minimumInvoice}" /></td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.maximumPromotion}" /></td>
                    <td>${x.dateStart}</td>
                    <td>${x.dateEnd}</td>
                    <td>${x.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
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