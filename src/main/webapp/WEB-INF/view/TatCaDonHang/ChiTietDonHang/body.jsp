<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <p style="font-size: 12px; color: #cfd3cb"><a style="text-decoration: none; color: #cfd3cb" href="/don-hang">Đơn
        hàng </a>/ ${bill.billCode}
    <div>
        <div class="row">
            <div class="col-4">
                <div class="mb-3">
                    <label for="addressDetail" class="form-label">Ngày đặt: </label>
                    ${bill.orderDate}
                </div>
            </div>
            <div class="col-3">
                <div class="mb-3">
                    <label for="addressDetail" class="form-label">Hình thức: </label>
                    ${bill.billType == 1 ? "Tại quầy" : "Online"}
                </div>
            </div>
            <div class="col-2">
                <div class="mb-3">
                    <label for="addressDetail" class="form-label">Trạng thái: </label>
                    ${bill.status == 1 ? "Chờ xác nhận" : (bill.status == 2 ? "Đang xử lý" :
                            bill.status == 3 ? "Chờ lấy hàng" : ( bill.status ==  4 ? "Đang giao" :
                                    (bill.status == 5 ? (bill.billType==1?"Đã hoàn thành" :"Đã giao") : (bill.status == 6 ? "Trả hàng" : "Đã huỷ"))))}
                </div>
            </div>
            <div class="col-3">
                <div>
                    <button class="btn btn-dark" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}" onclick="handleOnClickThemSP('${bill.id}')"
                    ><i class="bi bi-plus-circle"></i> Sản phẩm</button>
                    <a class="btn btn-dark"
                       style="display: none" id="themSPA"
                       href="/danh-sach-san-pham/${bill.id}/2"> <i class="bi bi-plus-circle"></i> Sản phẩm</a>
                    <button class="btn btn-dark" style="${(bill.status != 1) ? "display: none" : ""}"
                            onclick="handleOnClickXacNhan('${bill.id}')">Xác nhận
                    </button>
                    <button class="btn btn-dark" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}"
                            onclick="handleOnClickXacNhanHuy('${bill.id}')">Huỷ
                    </button>
                    <a style="display: none" href="/don-hang/updateStatus/${bill.id}/0/1" id="aHuyHoaHonOK"></a>
                    <a style="display: none" class="btn btn-dark" href="/don-hang/updateStatus/${bill.id}/3/1"
                       id="aXacNhan">
                        Xác nhận</a>
                    <a style="${(bill.status != 3) ? "display: none" : ""}" class="btn btn-dark"
                       href="/don-hang/updateStatus/${bill.id}/4/1">
                        Giao hàng</a>

                    <button class="btn btn-dark" style="${(bill.status != 4) ? "display: none" : ""}" onclick="handleHoanThanhOK('${bill.id}')">Hoàn thành</button>
                    <a class="btn btn-dark"
                       id="handleHoanThanhOK"
                       style="display: none"
                       href="/don-hang/updateStatus/${bill.id}/5/1"
                    >Giao hàng thành công</a>
                    <button class="btn btn-danger" style="${(bill.status != 4) ? "display: none" : ""}" onclick="handleOnClickThatBaiOK('${bill.id}')">Giao thất bại</button>
                    <a class="btn btn-danger"
                       id="handleOnClickThatBaiOK"
                       style="display:none;"
                       href="/don-hang/updateStatus/${bill.id}/0/1"
                    >Giao hàng thất bại</a>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-5">
                <form action="/don-hang/edit/${bill.id}/1" method="post" id="frmSubmitCreateBill"
                      onsubmit="handleOnOrder()">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và tên:</label>
                        <input class="form-control" name="fullName" id="fullName"
                               value="${bill.fullName}" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber"
                               value="${bill.phoneNumber}" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}
                               aria-describedby="emailHelp">
                        <input class="form-control" name="status" value="1" hidden id="status"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="addressDetail" class="form-label">Địa chỉ:</label>
                        <input class="form-control" name="addressDetail"
                               id="addressDetail" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}
                               value="${bill.addressDetail}">
                    </div>
                    <button class="btn btn-dark" onclick="handleUpdate('${bill.id}')" type="button"
                            style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}">Sửa
                    </button>
                </form>
                <div style="${bill.status == 0 ? 'display: block' : 'display: none'}">
                    <p>Lý do huỷ: <span style="color: red">${bill.reasonCancel}</span></p>
                </div>
            </div>
            <div class="col-7">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">
                            Sản phẩm
                        </th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Giá (VND)</th>
                        <th scope="col">Tổng tiền (VND)</th>
                        <th scope="col" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}">Thao
                            tác
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${billDetails}" var="cartDetail">
                        <tr>
                            <td scope="row">
                                <a href="" style="text-decoration: none; color: black">
                                    <div class="row">
                                        <img src="/san-pham/img?fileName=${cartDetail.productDetail.product.images[0]}"
                                             style="width: 80px; height: 80px" class="d-block" alt="">
                                        <div class="col-6" style="font-size: 9px">
                                            <p>${cartDetail.productDetail.name}</p>
                                            <p>Màu sắc: ${cartDetail.productDetail.color.name}</p>
                                            <p>Kích cỡ: ${cartDetail.productDetail.size.name}</p>
                                        </div>
                                    </div>
                                </a>
                            </td>
                            <td><span style="font-size: 12px"><fmt:formatNumber pattern="#,###"
                                                                                value="${cartDetail.quantity}"/></span>
                            </td>
                            <td>
                                <p style="font-size: 12px"><fmt:formatNumber pattern="#,###"
                                                                             value="${cartDetail.productDetail.priceSale}"/></p>
                            </td>
                            <td><span style="font-size: 12px"><fmt:formatNumber pattern="#,###"
                                                                                value="${cartDetail.productDetail.priceSale * cartDetail.quantity}"/></span>
                            </td>
                            <td>
                                <button class="btn btn-danger"
                                        style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}"
                                        onclick="handleOnClickCheck('${bill.id}')">Xoá
                                </button>
                                <a class="btn btn-danger" id="deleteA" style="display: none"
                                   href="/don-hang/delete/${cartDetail.id}/${bill.id}">Xoá</a>
                                <a class="btn btn-danger" id="updateA" style="display: none"
                                   href="/don-hang/update/${cartDetail.id}/${bill.id}">Xoá</a>
                                <button class="btn btn-warning"
                                        style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}"
                                        onclick="handleOnClickSua('${cartDetail.quantity}',${cartDetail.productDetail.quantity},'${bill.id}')">
                                    Sửa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div id="divTien" class="me-3">
                    <div class="mb-1">
                        <label for="addressDetail" class="form-label fw-bold">Tổng tiền: </label>
                        <fmt:formatNumber pattern="#,###" value="${bill.moneyRoot}"/> VND
                    </div>
                    <div class="mb-1">
                        <label for="addressDetail" class="form-label fw-bold">Giảm giá: </label>
                        <fmt:formatNumber pattern="#,###" value="${bill.giaGiam}"/> VND
                    </div>
                    <div class="mb-1" style="${bill.billType == 1 ? 'display: none' : ''}">
                        <label for="addressDetail" class="form-label fw-bold">Phí ship: </label>
                        <p style="${bill.status == 1 ? 'display: none' : ''}"><fmt:formatNumber pattern="#,###" value="${bill.shippingFee}"/> VND</p>
                        <span style="${bill.status != 1 ? 'display: none' : ''}">Đơn hàng sẽ được cộng phí ship sau khi xác nhận</span>
                    </div>
                    <div class="mb-1">
                        <label for="addressDetail" class="form-label fw-bold">Thành tiền: </label>
                        <fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/> VND
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    async function handleHoanThanhOK(id){
        var res = await axios.get("/api/check/statusBill/"+id+"/" + 4)
        if(res.data){
            if(confirm("Xác nhận giao hàng thành công")){
                toastr.success("Xác nhận thành công");
                document.getElementById("handleHoanThanhOK").click();
            }
        }else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }
    }

    async function handleOnClickThatBaiOK(id){
        var res = await axios.get("/api/check/statusBill/"+id+"/" + 4)
        if(res.data){
            if(confirm("Xác nhận giao hàng thất bại")){
                toastr.success("Xác nhận thành công");
                document.getElementById("handleOnClickThatBaiOK").click();
            }
        }else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }
    }

    async function handleOnClickThemSP(id){
        var res = await axios.get("/api/check/statusBill/" + id + "/" + 1);
        var resData = await axios.get("/api/check/statusBill/" + id + "/" + 3);
        if (!(res.data || resData.data)){
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
            return false;
        }else{
            document.getElementById("themSPA").click();
        }
    }

    async function handleOnClickSua(quantityOld, quantitySP, id) {
        var res = await axios.get("/api/check/statusBill/" + id + "/" + 1);
        var resData = await axios.get("/api/check/statusBill/" + id + "/" + 3);
        if (!(res.data || resData.data)){
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
            return false;
        }
        var quantityNew = prompt("Vui lòng nhập số lượng, số lượng cũ là " + quantityOld + ":");
        if (quantityNew == null || quantityNew == "" || quantityNew <= 0) {
            toastr.error("Vui lòng nhập số lượng!");
            return false;
        }
        if (isNaN(quantityNew)) {
            toastr.error("Số lượng phải là số");
            return false;
        }
        if (+quantityNew > (+quantityOld + (+quantitySP))) {
            toastr.error("Sản phẩm chỉ còn: " + (+quantityOld + (+quantitySP)));
            return false;
        }
        var a = document.getElementById("updateA");
        var _href = a.href;
        _href = _href + "/" + quantityNew;
        a.setAttribute("href", _href);
        a.click();
    }

    async function handleOnClickXacNhan(id) {
        var resData = await axios.get("/api/check/statusBill/" + id + "/" + 1);
        if (!(resData.data)){
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
            return false;
        }
        var a = document.getElementById("aXacNhan");
        var _href = a.href;
        var quantity = prompt("Nhập phí ship: ")
        console.log(quantity);
        if (quantity == null || quantity == "" || quantity < 0) {
            toastr.error("Vui lòng nhập phí ship!");
            return false;
        }
        if (isNaN(quantity)) {
            toastr.error("Phí ship phải là số");
            return false;
        }
        _href = _href + "?ship=" + quantity;
        a.setAttribute("href", _href);
        a.click();
    }

    async function handleOnClickXacNhanHuy(id) {
        var res = await axios.get("/api/check/statusBill/"+id+"/" + 1)
        var resData = await axios.get("/api/check/statusBill/"+id+"/" + 3)
        if(res.data || resData.data){
            var reason = prompt("Vui lòng nhập lý do huỷ");
            if(reason != null && reason.trim() != "" && reason != undefined){
                if(confirm("Bạn có muốn huỷ đơn hàng không")){
                    var a = document.getElementById("aHuyHoaHonOK");
                    var _href = a.href + "?reason=" + reason;
                    toastr.success("Huỷ thành công");
                    document.getElementById("aHuyHoaHonOK").setAttribute("href", _href);
                    document.getElementById("aHuyHoaHonOK").click();
                }
            }else{
                toastr.error("Lý do huỷ không hợp lệ");
            }
        }else{
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }
    }

    async function handleUpdate(id) {
        var fullName = document.getElementById("fullName").value;
        var phoneNumber = document.getElementById("phoneNumber").value;
        var addressDetail = document.getElementById("addressDetail").value;
        if(fullName == "" || fullName == null || fullName == undefined){
            toastr.error("Vui lòng nhập tên");
            return false;
        }
        if(phoneNumber == "" || phoneNumber == null || phoneNumber == undefined){
            toastr.error("Vui lòng nhập số điện thoại");
            return false;
        } else {
            var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|34|33|32|35|36|37|38|39|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
            if (!phoneNumberRegex.test(phoneNumber)) {
                toastr.error("Số điện thoại không hợp lệ");
                return false;
            }
        }
        if(addressDetail == "" || addressDetail == null || addressDetail == undefined){
            toastr.error("Vui lòng nhập địa chỉ");
            return false;
        }
        var res = await axios.get("/api/check/statusBill/" + id + "/" + 1)
        var resDa = await axios.get("/api/check/statusBill/" + id + "/" + 3)
        if (res.data || resDa.data) {
            if (confirm("Bạn có muốn sửa thông tin nhận hàng không")) {
                document.getElementById("frmSubmitCreateBill").submit();
            }
        } else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }
    }

    async function handleOnClickCheck(id) {
        var res = await axios.get("/api/check/statusBill/" + id + "/" + 1);
        var resData = await axios.get("/api/check/statusBill/"+id+"/" + 3)
        if(res.data || resData.data){
            await axios.get('/don-hang/checkSize/' + ${bill.id})
                .then((response) => {
                    console.log(response);
                    if (response.data) {
                        if (confirm("Bạn có muốn xoá sản phẩm không?")) {
                            document.getElementById("deleteA").click();
                        }
                    } else {
                        toastr.error("Đơn hàng chỉ còn 1 sản phẩm không thể xoá");
                        return false;
                    }
                })

        } else {
            toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
        }
    }
</script>