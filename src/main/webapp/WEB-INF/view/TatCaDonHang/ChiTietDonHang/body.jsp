<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <p style="font-size: 12px; color: #cfd3cb"><a style="text-decoration: none; color: #cfd3cb" href="/don-hang">Đơn hàng </a>/ ${bill.billCode}
    <div>
        <div class="row">
            <div class="col-5">
                <form action="/don-hang/edit/${bill.id}" method="post" id="frmSubmitCreateBill" onsubmit="handleOnOrder()">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và tên:</label>
                        <input class="form-control" name="fullName" id="fullName" value="${bill.fullName}" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber" value="${bill.phoneNumber}" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}
                               aria-describedby="emailHelp">
                        <input class="form-control" name="status" value="1" hidden id="status"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="addressDetail" class="form-label">Địa chỉ:</label>
                        <input class="form-control" name="addressDetail" id="addressDetail" ${(bill.status != 1 && bill.status != 3) ? "disabled" : ""}
                               value="${bill.addressDetail}">
                    </div>
                    <button class="btn btn-dark" onclick="handleUpdate()" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}">Sửa</button>
                </form>
                <br/>
                <h6>Chi tiết thanh toán</h6>
                <p>Số lượng sản phẩm: <fmt:formatNumber pattern="#,###" value="${bill.tatolProduct}"/></p>
                <p>Tổng tiền: <fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/> VND</p>
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
                            <td><button class="btn btn-danger"
                                        style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}"
                                        onclick="handleOnClickCheck()">Xoá</button>
                                <a class="btn btn-danger" id="deleteA" style="display: none"
                                   href="/don-hang/delete/${cartDetail.id}/${bill.id}" >Xoá</a>
                                <a class="btn btn-danger" id="updateA" style="display: none"
                                   href="/don-hang/update/${cartDetail.id}/${bill.id}" >Xoá</a>
                                <button class="btn btn-warning" style="${(bill.status != 1 && bill.status != 3) ? "display: none" : ""}" onclick="handleOnClickSua('${cartDetail.quantity}',${cartDetail.productDetail.quantity})">Sửa</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-12">
                        <div style="width: 30%; float: right;">
                            <button class="btn btn-dark" style="${(bill.status != 1 || bill.status != 3) ? "display: none" : ""}" onclick="handleOnClickXacNhan()">Thêm sản phẩm</button>
                            <button class="btn btn-dark" style="${(bill.status != 1) ? "display: none" : ""}" onclick="handleOnClickXacNhan()">Xác nhận đơn hàng</button>
                            <a style="display: none" class="btn btn-dark" href="/don-hang/updateStatus/${bill.id}/3/1" id="aXacNhan">
                                Xác nhận đơn hàng</a>
                            <a style="${(bill.status != 3) ? "display: none" : ""}" class="btn btn-dark" href="/don-hang/updateStatus/${bill.id}/4/1">
                                Giao hàng</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    function handleOnClickSua(quantityOld, quantitySP) {
        var quantityNew = prompt("Vui lòng nhập số lượng, số lượng cũ là " + quantityOld + ":");
        if(quantityNew == null || quantityNew == "" || quantityNew <= 0){
            toastr.error("Vui lòng nhập số lượng!");
            return false;
        }
        if(isNaN(quantityNew)){
            toastr.error("Số lượng phải là số");
            return false;
        }
        if(+quantityNew > (+quantityOld + (+quantitySP))){
            toastr.error("Sản phẩm chỉ còn: " +  (+quantityOld + (+quantitySP)));
            return false;
        }
        var a = document.getElementById("updateA");
        var _href = a.href;
        _href = _href + "/" + quantityNew;
        a.setAttribute("href",_href);
        a.click();
    }

    function handleOnClickXacNhan(){
        var a = document.getElementById("aXacNhan");
        var _href = a.href;
        var quantity = prompt("Nhập phí ship: ")
        console.log(quantity);
        if(quantity == null || quantity == "" || quantity <= 0){
            toastr.error("Vui lòng nhập phí ship!");
            return false;
        }
        if(isNaN(quantity)){
            toastr.error("Phí ship phải là số");
            return false;
        }
        _href = _href + "?ship=" + quantity;
        a.setAttribute("href", _href);
        a.click();
    }

    function handleUpdate(){
        document.getElementById("frmSubmitCreateBill").submit();
    }
    async function handleOnClickCheck(){
        if (!confirm("Bạn có muốn xoá sản phẩm không?")){
            return false;
        }
        await axios.get('/don-hang/checkSize/' + ${bill.id})
            .then((response) => {
                console.log(response);
                if(response.data){
                    document.getElementById("deleteA").click();
                }else {
                    alert("Đơn hàng chỉ còn 1 sản phẩm không thể xoá");
                    return false;
                }
            })
    }
</script>