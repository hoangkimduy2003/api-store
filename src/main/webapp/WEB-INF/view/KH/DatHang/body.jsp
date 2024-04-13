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
                        <label for="fullName" class="form-label">Họ và tên<span style="color: red">(*)</span>:</label>
                        <input class="form-control" name="fullName" id="fullName">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại<span style="color: red">(*)</span>:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber"
                               aria-describedby="emailHelp">
                        <input class="form-control" name="status" value="1" hidden id="status"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="city" class="form-label">Thành phố<span style="color: red">(*)</span>:</label>
                                <select id="city" name="city" class="form-select" onchange="handleOnChangeCity(this)">
                                </select>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="district" class="form-label">Quận/huyện<span style="color: red">(*)</span>:</label>
                                <select id="district" name="district" class="form-select" onchange="handleOnChangeDistrict(this)">
                                </select>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="mb-3">
                                <label for="ward" class="form-label">Phường/xã<span style="color: red">(*)</span>:</label>
                                <select id="ward" name="ward" class="form-select">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="addressDetail" class="form-label">Địa chỉ chi tiết<span style="color: red">(*)</span>:</label>
                        <input class="form-control" name="addressDetail" id="addressDetail">
                    </div>
                    <div class="row">
                        <div class="col-9">
                            <div class="mb-3">
                                <label for="voucher" class="form-label">Mã giảm giá:</label>
                                <input class="form-control" name="voucher" id="voucher">
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="mb-3">
                                <label for="btnVoucher" class="form-label">Áp dụng</label>
                                <button type="button" id="btnVoucher" class="btn btn-dark" onclick="handleOnClickApVouCher('${cart.totalMoney}')">Áp dụng</button>
                            </div>
                        </div>
                    </div>
                    <button class="btn btn-dark" type="button" onclick="handleOnOrder()">ĐẶT HÀNG</button>
                    <a href="/gio-hang" id="aQuayLai" class="btn btn-dark">QUAY LẠI</a>
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
                                Tổng tiền: <span id="tongTien"><fmt:formatNumber pattern="#,###" value="${cart.totalMoney}"/></span><br>
                                Giảm giá: <span id="giaGiam">0</span><br/>
                                Tạm tính: <span id="tamTinh"><fmt:formatNumber pattern="#,###" value="${cart.totalMoney}"/></span> VND
                                <br/>
                                <span style="color: red; font-size: 12px">Đơn hàng chưa bao gồm phí ship</span>
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

    async function handleOnClickApVouCher(totalMoney){
        var voucher = document.getElementById("voucher").value;
        if(voucher == '' || voucher == null){
            toastr.error("Vui lòng nhập mã giảm giá ");
            return false;
        }
        await axios.get('/khuyen-mai/voucherApp/'+ voucher).then(res => {
            console.log(res);
            console.log(res.status);
            if(res.status == 200){
                var vc = res.data;
                if(vc.voucherType == 1 ){
                    console.log(1);
                    var giaGiam = (+totalMoney) * (vc.promotionalLevel/100);
                    if(giaGiam > (+vc.maximumPromotion)){
                        giaGiam = (+vc.maximumPromotion);
                    }
                    var giaConLai = (+totalMoney) - giaGiam;
                    var gia = Math.floor(giaGiam).toLocaleString('en-US');
                    var tamTinh = Math.floor(giaConLai).toLocaleString('en-US');
                    document.getElementById("giaGiam").innerHTML = gia;
                    document.getElementById("tamTinh").innerHTML = tamTinh;
                }else{

                }
            }else{
                toastr.error("Mã giảm giá không đúng hoặc đã hết hạn");
                document.getElementById("giaGiam").innerHTML = 0;
                document.getElementById("tamTinh").innerHTML = totalMoney;
                return false;
            }
        }).catch(e =>{
            toastr.error("Mã giảm giá không tồn tại hoặc đã kết thúc");
            return false;
        })

    }

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
        var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|32|31|30|34|35|36|37|38|39|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
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
            axios.get('/dat-hang/checkCart').then(
                (reponse) => {
                    if(reponse.data){
                        axios.get('/dat-hang/checkOrder').then(
                            (reponse) => {
                                if(reponse.data){
                                    document.getElementById("frmSubmitCreateBill").submit();
                                }else{
                                    toastr.error("Sản phẩm đã hết hoặc số lượng đã thay đổi quay lại giỏ hàng sau 3 giây");
                                    setTimeout(function() {
                                        // Lấy phần tử theA và kích hoạt sự kiện click
                                        document.getElementById("aQuayLai").click();
                                    }, 2000);

                                }
                            }
                        )
                    }else{
                        toastr.error("Đơn hàng đã được đặt");
                        setTimeout(function() {
                            // Lấy phần tử theA và kích hoạt sự kiện click
                            document.getElementById("aQuayLai").click();
                        }, 2000);

                    }
                }
            )
            // document.getElementById("frmSubmitCreateBill").submit();
        }
    }
</script>