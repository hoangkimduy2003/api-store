<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal fade" id="thanhtoan" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog  modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Đặt hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
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
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${cart.cartDetails}" var="cartDetail">
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
                        <div class="row">
                            <div class="col-12">
                                <div style="width: 30%; float: right;">
                                    <p>
                                        Tổng sản phẩm: <fmt:formatNumber pattern="#,###"
                                                                         value="${cart.totalProduct}"/><br>
                                        Tạm tính: <fmt:formatNumber pattern="#,###" value="${cart.totalMoney}"/> VND
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-5">
                        <h5>Thông tin đặt hàng</h5>
                        <form action="/gio-hang/order" method="post" id="frmSubmitCreateBill" onsubmit="handleOnOrder()">
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Họ và tên:</label>
                                <input class="form-control" name="fullName" id="fullName">
                            </div>
                            <div class="mb-3">
                                <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                                <input class="form-control" name="user.phoneNumber" id="phoneNumber"
                                       aria-describedby="emailHelp">
                                <input class="form-control" name="status" value="1" hidden id="status"
                                       aria-describedby="emailHelp">
                            </div>
                            <div class="mb-3">
                                <label for="addressDetail" class="form-label">Địa chỉ:</label>
                                <input class="form-control" name="addressDetail" id="addressDetail">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-dark" onclick="handleOnOrder()">Đặt hàng</button>
            </div>
        </div>
    </div>
</div>
<script>

    function validate() {
        if (document.getElementById("fullName").value == null || document.getElementById("fullName").value == ""
            || document.getElementById("fullName").value == undefined) {
            alert("Vui lòng nhập tên");
            return false;
        }
        if(document.getElementById("phoneNumber").value == null || document.getElementById("phoneNumber").value == ""
        || document.getElementById("phoneNumber").value == undefined){
            alert("Vui lòng nhập số điện thoại")
            return false;
        }
        var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
        if (!phoneNumberRegex.test(document.getElementById("phoneNumber").value)) {
            alert("Số điện thoại không hợp lệ");
            return false;
        }
        if(document.getElementById("addressDetail").value == null || document.getElementById("addressDetail").value == ""
        ||document.getElementById("addressDetail").value == undefined){
            alert("Vui lòng nhập địa chỉ");
            return false;
        }
        return true;
    }

    function handleOnOrder() {
        if (validate()) {
            document.getElementById("frmSubmitCreateBill").submit();
        }
    }
</script>