<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <button type="button" onclick="preAction(null,null,null,null,null,null,1,null,null,1,1)" style="width: 150px" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Thêm khuyến mại
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
                <th scope="col">Số lượng</th>
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
                    <td>${x.quantity}</td>
                    <td>${x.status == 0 ? "Không hoạt động" : "Hoạt động"}</td>
                    <td>
                        <button type="button" class="btn btn-warning" onclick="preAction(${x.id},'${x.voucherCode}','${x.minimumInvoice}','${x.dateStart}','${x.dateEnd}','${x.quantity}','${x.voucherType}','${x.promotionalLevel}','${x.maximumPromotion}','${x.status}',2)"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Sửa
                        </button>
                        <button onclick="handleOnClickSendKhuyenMai('${x.id}',this)" style="${x.sendType == 1 ? "" : "display: none"}" class="btn btn-info"><i class="bi bi-envelope-arrow-up-fill"></i></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <ul class="pagination">
        <c:forEach begin="1" end="${list.totalPages}" varStatus="loop">
            <li class="page-item">
                <a class="page-link" href="/khuyen-mai?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<script>
    async function handleOnClickSendKhuyenMai(id, e){
        e.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';
        var res = await axios.get("/khuyen-mai/sendEmail/"+id);
        if(res.status == 200){
            toastr.success("Gửi khuyến mãi đến khách hàng thành công");
            e.style.display = "none";
        }else{
            toastr.error("Gửi thông tin đến khách hàng thất bại");
        }
    }
</script>