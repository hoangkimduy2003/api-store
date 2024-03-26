<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Đặt hàng</h5>
    </div>
    <div>
        <div class="row">
            <div class="col-5">
                <form action="/dat-hang/order" method="post" id="frmSubmitCreateBill" onsubmit="handleOnOrder()">
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
                    <div class="row">
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="city" class="form-label">Thành phố</label>
                                <select id="city" name="city" class="form-select" onchange="handleOnChangeCity(this)">
                                </select>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="district" class="form-label">Quận/huyện</label>>
                                <select id="district" name="district" class="form-select" onchange="handleOnChangeDistrict(this)">
                                </select>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="ward" class="form-label">Phường/xã</label>
                                <select id="ward" name="ward" class="form-select">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="addressDetail" class="form-label">Địa chỉ chi tiết:</label>
                        <input class="form-control" name="addressDetail" id="addressDetail">
                    </div>
                    <button class="btn btn-dark" type="button" onclick="handleOnOrder()">ĐẶT HÀNG</button>
                    <a href="/gio-hang" class="btn btn-dark">QUAY LẠI</a>
                </form>
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
        </div>
    </div>
    <br/>
</div>
<script>
    axios.get('https://vapi.vnappmob.com/api/province').then(
        (reponse) => {
            var arrD = reponse.data.results;
            arrD.forEach(x => {
                var _option = document.createElement('option');
                _option.innerText = x.province_name;
                _option.value = x.province_id + "|" + x.province_name;
                document.getElementById("city").appendChild(_option);
            });
            var city = document.getElementById("city").value;
            axios.get('https://vapi.vnappmob.com/api/province/district/' + city.split("|")[0]).then(
                (reponse) => {
                    var arrD = reponse.data.results;
                    arrD.forEach(x => {
                        var _option = document.createElement('option');
                        _option.innerText = x.district_name;
                        _option.value = x.district_id + "|" + x.district_name;
                        document.getElementById("district").appendChild(_option);
                    });
                    var district = document.getElementById("district").value;
                    axios.get('https://vapi.vnappmob.com/api/province/ward/' + district.split("|")[0]).then(
                        (reponse) => {
                            var arrD = reponse.data.results;
                            arrD.forEach(x => {
                                var _option = document.createElement('option');
                                _option.innerText = x.ward_name;
                                _option.value = x.ward_id + "|" + x.ward_name;
                                document.getElementById("ward").appendChild(_option);
                            });
                        }
                    )
                }
            )
        }
    )

    function handleOnChangeCity(e) {
        document.getElementById("district").innerHTML = "";
        document.getElementById("ward").innerHTML = "";
        axios.get('https://vapi.vnappmob.com/api/province/district/' + e.value.split("|")[0]).then(
            (reponse) => {
                var arrD = reponse.data.results;
                arrD.forEach(x => {
                    var _option = document.createElement('option');
                    _option.innerText = x.district_name;
                    _option.value = x.district_id + "|" + x.district_name;
                    document.getElementById("district").appendChild(_option);
                });
                var district = document.getElementById("district").value;
                axios.get('https://vapi.vnappmob.com/api/province/ward/' + district.split("|")[0]).then(
                    (reponse) => {
                        var arrD = reponse.data.results;
                        arrD.forEach(x => {
                            var _option = document.createElement('option');
                            _option.innerText = x.ward_name;
                            _option.value = x.ward_id + "|" + x.ward_name;
                            document.getElementById("ward").appendChild(_option);
                        });
                    }
                )
            }
        )
    }

    function handleOnChangeDistrict(e) {
        document.getElementById("ward").innerHTML = "";
        axios.get('https://vapi.vnappmob.com/api/province/ward/' + e.value.split("|")[0]).then(
            (reponse) => {
                var arrD = reponse.data.results;
                console.log(arrD);
                arrD.forEach(x => {
                    var _option = document.createElement('option');
                    _option.innerText = x.ward_name;
                    _option.value = x.ward_id + "|" + x.ward_name;
                    document.getElementById("ward").appendChild(_option);
                });
            }
        )
    }

    function validate() {
        if (document.getElementById("fullName").value == null || document.getElementById("fullName").value == ""
            || document.getElementById("fullName").value == undefined) {
            toastr.error("Vui lòng nhập tên");
            return false;
        }
        if(document.getElementById("phoneNumber").value == null || document.getElementById("phoneNumber").value == ""
            || document.getElementById("phoneNumber").value == undefined){
            toastr.error("Vui lòng nhập số điện thoại")
            return false;
        }
        var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
        if (!phoneNumberRegex.test(document.getElementById("phoneNumber").value)) {
            toastr.error("Số điện thoại không hợp lệ");
            return false;
        }
        if(document.getElementById("addressDetail").value == null || document.getElementById("addressDetail").value == ""
            ||document.getElementById("addressDetail").value == undefined){
            toastr.error("Vui lòng nhập địa chỉ chi tiết");
            return false;
        }
        if(!confirm("Xác nhận thông tin nhận hàng đã chính xác")){
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