<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <p style="font-size: 12px; color: #cfd3cb"><a style="text-decoration: none; color: #cfd3cb" href="/my-order?status=${bill.status}">Đơn
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
        </div>
        <div class="row">
            <div class="col-5">
                <form action="/don-hang/edit/${bill.id}/2" method="post" class="disabled" id="frmSubmitCreateBill"
                      onsubmit="handleOnOrder()">
                    <fieldset disabled>
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
                    </fieldset>
                </form>
                <br/>

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
<%--                        <th scope="col" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}">Thao--%>
<%--                            tác--%>
<%--                        </th>--%>
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

    function handleOnClickSua(quantityOld, quantitySP) {
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

    function handleOnClickXacNhan() {
        var a = document.getElementById("aXacNhan");
        var _href = a.href;
        var quantity = prompt("Nhập phí ship: ")
        console.log(quantity);
        if (quantity == null || quantity == "" || quantity <= 0) {
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

    async function handleUpdate(id) {
       await axios.get("/api/check/statusBill/" + id + "/" + 1).then(res => {
           if (res.data) {
               if (confirm("Bạn có muốn sửa thông tin nhận hàng không")) {
                   document.getElementById("frmSubmitCreateBill").submit();
               }
           } else {
               toastr.error("Đơn hàng đã được thay đổi trạng thái. Vui lòng tải lại trang");
           }
       })
    }

    async function handleOnClickCheck() {
        if (!confirm("Bạn có muốn xoá sản phẩm không?")) {
            return false;
        }
        await axios.get('/don-hang/checkSize/' + ${bill.id})
            .then((response) => {
                console.log(response);
                if (response.data) {
                    document.getElementById("deleteA").click();
                } else {
                    alert("Đơn hàng chỉ còn 1 sản phẩm không thể xoá");
                    return false;
                }
            })
    }
</script>