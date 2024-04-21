<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">

    <div class="row" style="margin-bottom: 24px;">
        <span style="font-size: 11px;"><a href="/trang-chu" style="text-decoration: none; color: black;">HOME</a> | ĐƠN HÀNG
            CỦA
            TÔI</span>
    </div>
    <div class="row">
        <div class="col-md-2 col-xs-4">
            <a class="btn btn-outline-secondary d-block w-100" href="/my-order">Tất cả đơn hàng
            </a>
        </div>
        <div class="col-md-2 col-xs-4">
            <a class="btn btn-outline-secondary d-block w-100" href="/my-order?status=1">Đang chờ
            </a>
        </div>
        <div class="col-md-2 col-xs-4">
            <a  class="btn btn-outline-secondary d-block w-100" href="/my-order?status=3">Đã xác nhận
            </a>
        </div>
        <div class="col-md-2 col-xs-4">
            <a class="btn btn-outline-secondary d-block w-100" href="/my-order?status=4">Đang giao hàng
            </a>
        </div>
        <div class="col-md-2 col-xs-4">
            <a class="btn btn-outline-secondary d-block w-100" href="/my-order?status=5">Đã hoàn thành
            </a>
        </div>
        <div class="col-md-2 col-xs-4">
            <a class="btn btn-outline-secondary d-block w-100" href="/my-order?status=0">Đã huỷ
            </a>
        </div>
    </div>
    <div class="container">

        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Tên người nhận</th>
                    <th scope="col">Số điện thoại</th>
                    <th scope="col">Tổng sản phẩm</th>
                    <th scope="col">Tổng tiền</th>
                    <th scope="col">Ngày tạo</th>
                    <th scope="col">Trạng thái</th>
                    <th scope="col">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bills.data}" var="bill" varStatus="loopStatus">
                    <tr>
                        <td>${loopStatus.index + 1}</td>
                        <td>${bill.fullName}</td>
                        <td>${bill.phoneNumber}</td>
                        <td><fmt:formatNumber pattern="#,###" value="${bill.tatolProduct}"/></td>
                        <td><fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/></td>
                        <td>${bill.orderDate}</td>
                        <td>
                                ${bill.status == 1 ? "Chờ xác nhận" : (bill.status == 2 ? "Đang xử lý" :
                                        bill.status == 3 ? "Chờ lấy hàng" : ( bill.status ==  4 ? "Đang giao" :
                                                (bill.status == 5 ? (bill.billType==1?"Đã hoàn thành" :"Đã giao") : (bill.status == 6 ? "Trả hàng" : "Đã huỷ"))))}
                        </td>
                        <td>
                            <a style="color: aliceblue;" class="btn btn-info" href="/CTDH/${bill.id}" ><i class="bi bi-eye"></i>
                            </a>
                            <button class="btn btn-dark" style="${(bill.status != 4) ? "display: none" : ""}" onclick="handOnDaNhan('${bill.id}')">Đã nhận hàng</button>
                            <a class="btn btn-dark"
                               id="btnDaNhanOnline${bill.id}"
                               style="display: none"
                               href="/don-hang/updateStatus/${bill.id}/5/2"
                            >Đã nhận hàng</a>
                            <button class="btn btn-dark" style="${(bill.status != 1) ? "display: none" : ""}" onclick="handleOnHuy('${bill.id}',1)">Huỷ</button>
                            <a class="btn btn-dark"
                               id="btnHuyOnline${bill.id}"
                               style="display: none"
                               href="/don-hang/updateStatus/${bill.id}/0/2"
                            >Huỷ</a>
                            <button class="btn btn-dark" style="${(bill.status != 4) ? "display: none" : ""}" onclick="handleOnHuy('${bill.id}',2)">Thất bại</button>
                            <a class="btn btn-dark"
                               id="khongnhanhang${bill.id}"
                               style="display:none;"
                               href="/don-hang/updateStatus/${bill.id}/0/2"
                            >Hoàn trả</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul class="pagination">
                <c:forEach begin="1" end="${bills.totalPages}" varStatus="loop">
                    <li class="page-item">
                        <a class="page-link" href="/my-order?page=${loop.begin + loop.count -2}&status=${status}">
                                ${loop.begin + loop.count -1}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<script>
    async function handleOnHuy(id,typeAction){
        if(typeAction == 1){
            var res = await axios.get("/api/check/statusBill/"+id+"/" + 1)
            console.log(res);
            if(res.data){
                var reason = prompt("Vui lòng nhập lý do huỷ");
                if(reason != null && reason.trim() != "" && reason != undefined){
                    if(confirm("Bạn có muốn huỷ đơn hàng không")){
                        var a = document.getElementById("btnHuyOnline"+id);
                        var _href = a.href + "?reason=" + reason;
                        toastr.success("Huỷ thành công");
                        document.getElementById("btnHuyOnline"+id).setAttribute("href", _href);
                        document.getElementById("btnHuyOnline"+id).click();
                    }
                }else{
                    toastr.error("Lý do huỷ không hợp lệ");
                }
            }else {
                toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
            }
        }else{
            var res = await axios.get("/api/check/statusBill/"+id+"/" + 4);
            console.log(res);
            if(res.data){
                var reason = prompt("Vui lòng nhập lý do huỷ");
                if(reason != null && reason.trim() != "" && reason != undefined){
                    if(confirm("Bạn có muốn huỷ đơn hàng không")){
                        var a = document.getElementById("btnHuyOnline"+id);
                        var _href = a.href + "?reason=" + reason;
                        toastr.success("Huỷ thành công");
                        document.getElementById("btnHuyOnline"+id).setAttribute("href", _href);
                        document.getElementById("btnHuyOnline"+id).click();
                    }
                }else{
                    toastr.error("Lý do huỷ không hợp lệ");
                }
            }else {
                toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
            }
        }


    }
    async function handOnDaNhan(id){
        var res = await axios.get("/api/check/statusBill/"+id+"/" + 4)
        console.log(res);
        if(res.data){
            if(confirm("Đã nhận hàng thành công")){
                toastr.success("Nhận hàng thành công");
                document.getElementById("btnDaNhanOnline"+id).click();
            }
        }else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }

    }
    async function handleOnKhongNhanHang(id){
        var res = await axios.get("/api/check/statusBill/"+id+"/" + 4)
        console.log(res);
        if(res.data){
            if(confirm("Bạn có muốn huỷ đơn hàng không")){
                toastr.success("Huỷ thành công");
                document.getElementById("khongnhanhang"+id).click();
            }
        }else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }

    }
</script>