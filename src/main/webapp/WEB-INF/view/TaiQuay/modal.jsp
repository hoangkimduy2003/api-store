<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal fade" id="thanhtoan" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/tai-quay/atStore/${bill.id}" method="post" id="frmAction" onsubmit="handleOnAction('${bill.totalMoney}')">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <p>Tên KH: <span id="customerName"></span></p>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại<span style="color: red">(*)</span>:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber"
                               aria-describedby="emailHelp" onchange="handleOnChangeSdt(this)">
                        <input class="form-control" name="status" value="5" hidden id="status"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <p>Tổng tiền: <span id="totalMoney"><fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/></span> VND</p>
                        <p>Giá giảm: <span id="giaGiam">0</span> VND</p>
                        <p>Thành tiền: <span id="tamTinhValue"><fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/></span> VND</p>
                    </div>
                    <div class="mb-3">
                        <p id="moneyTransfers">Còn thiếu: <fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/> VND</p>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Tiền khách đưa:</label>
                        <input class="form-control" type="number" id="moneyCustomer"
                               onchange="handleOnChangeInputMoney(this)" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3" style="display: none">
                        <label for="htnh" class="form-label">Hình thức nhận hàng</label>
                        <select id="htnh" class="form-select" onchange="handleOnChangeHtnn(this)">
                            <option value="1" selected>Tại quầy</option>
                            <option value="2">Giao hàng</option>
                        </select>
                    </div>
                    <div class="mb-3" id="inputFullName">
                    </div>
                    <div class="mb-3" id="inputAddress">
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
                                <button type="button" id="btnVoucher" class="btn btn-dark" onclick="handleOnClickCheckVOucher('${bill.totalMoney}')">Áp dụng</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" onclick="handleOnAction('${bill.totalMoney}')" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="sua-gio-hang" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/danh-sach-san-pham/update" method="post" id="frmActionUpdate"
                  onsubmit="handleOnActionUpdate()">
                <input class="form-control" name="id" id="idBillDetail" style="display: none"
                       aria-describedby="emailHelp">
                <input class="form-control" name="bill.id" id="idBill" value="${bill.id}" style="display: none"
                       aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số lượng:</label>
                        <input class="form-control" type="number" name="quantity" id="quantity"
                               aria-describedby="emailHelp">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" onclick="handleOnActionUpdate()" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
            <input type="number" hidden id="quantityProduct">
            <input type="number" hidden id="quantityOld">
        </div>
    </div>
</div>
<script>

    async function handleOnClickCheckVOucher(totalMoney){
        var voucher = document.getElementById("voucher").value;
        await axios.get('/khuyen-mai/voucherApp/'+ voucher).then(res => {
            if(res.status == 200){
                var vc = res.data;
                if(+totalMoney >= +(vc.minimumInvoice)){
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
                        document.getElementById("tamTinhValue").innerHTML = tamTinh;
                        var tienKD = document.getElementById("moneyCustomer").value;
                        if(tienKD != "" && tienKD != null && tienKD != undefined){
                            var traLai = tienKD - giaConLai;
                            if(traLai >= 0){
                                var valueNew = new Intl.NumberFormat('en-US').format(traLai);
                                document.getElementById("moneyTransfers").innerHTML = "Trả lại: " + valueNew + " VND";
                            }else{
                                var valueNew = new Intl.NumberFormat('en-US').format(-traLai);
                                document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                            }
                        }else{
                            var valueNew = new Intl.NumberFormat('en-US').format(giaConLai);
                            document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                        }
                    }else{
                        var giaGiam = +vc.promotionalLevel;
                        var giaConLai = (+totalMoney) - giaGiam;
                        var gia = Math.floor(giaGiam).toLocaleString('en-US');
                        var tamTinh = Math.floor(giaConLai).toLocaleString('en-US');
                        document.getElementById("giaGiam").innerHTML = gia;
                        document.getElementById("tamTinhValue").innerHTML = tamTinh;
                        var tienKD = document.getElementById("moneyCustomer").value;
                        if(tienKD != "" && tienKD != null && tienKD != undefined){
                            var traLai = tienKD - giaConLai;
                            if(traLai >= 0){
                                var valueNew = new Intl.NumberFormat('en-US').format(traLai);
                                document.getElementById("moneyTransfers").innerHTML = "Trả lại: " + valueNew + " VND";
                            }else{
                                var valueNew = new Intl.NumberFormat('en-US').format(-traLai);
                                document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                            }
                        }else{
                            var valueNew = new Intl.NumberFormat('en-US').format(giaConLai);
                            document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                        }
                    }
                }else{
                    toastr.error("Hoá đơn chưa đạt giá trị tối tiểu của mã giảm giá");
                    return false;
                }

            }else{
                toastr.error("Mã giảm giá không đúng hoặc đã hết hạn");
                return false;
            }
        }).catch(e =>{
            toastr.error("Mã giảm giá không tồn tại hoặc đã kết thúc");
            return false;
        })
    }

    async function handleOnChangeSdt(e){
        var value = e.value;
        await axios.get('/tai-quay/getFullName/' + value)
            .then(response => {
                document.getElementById("customerName").innerHTML = response.data;
            })
    }

    function handleOnChangeHtnn(e) {
        var value = e.value;
        var divAddress = document.getElementById("inputAddress");
        var divFullName = document.getElementById("inputFullName");
        var total = (+${bill.totalMoney});
        var ship = 35000;
        var totalAll = total + ship;
        var frmMoney = new Intl.NumberFormat('en-US').format(total);
        var frmShip = new Intl.NumberFormat('en-US').format(ship);
        var frmTotalAll = new Intl.NumberFormat('en-US').format(totalAll);
        if (value == 2) {
            document.getElementById("status").value = 3;

            var addressDiv = document.createElement("div");
            addressDiv.classList.add("mb-3");
            var addressLabel = document.createElement("label");
            addressLabel.setAttribute("for", "addressDetail");
            addressLabel.classList.add("form-label");
            addressLabel.textContent = "Địa chỉ:";
            var addressInput = document.createElement("input");
            addressInput.setAttribute("type", "text");
            addressInput.setAttribute("name", "addressDetail");
            addressInput.setAttribute("id", "addressDetail");
            addressInput.setAttribute("class", "form-control");
            // Thêm phần tử input và label vào div
            addressDiv.appendChild(addressLabel);
            addressDiv.appendChild(addressInput);

            var fullNameDiv = document.createElement("div");
            fullNameDiv.classList.add("mb-3");
            var fullNameLable = document.createElement("label");
            fullNameLable.setAttribute("for", "fullName");
            fullNameLable.classList.add("form-label");
            fullNameLable.textContent = "Họ tên:";
            var fullNameInput = document.createElement("input");
            fullNameInput.setAttribute("type", "text");
            fullNameInput.setAttribute("name", "fullName");
            fullNameInput.setAttribute("id", "fullName");
            fullNameInput.setAttribute("class", "form-control");
            // Thêm phần tử input và label vào div
            fullNameDiv.appendChild(fullNameLable);
            fullNameDiv.appendChild(fullNameInput);

            // Thêm div mới vào div inputAddress

            divAddress.appendChild(addressDiv);
            divFullName.appendChild(fullNameDiv);
            document.getElementById("totalMoney").innerHTML = frmMoney + " + " + frmShip + " = " + frmTotalAll;
        } else {
            divAddress.innerHTML = "";
            divFullName.innerHTML = "";
            document.getElementById("status").value = 5;
            document.getElementById("totalMoney").innerHTML = frmMoney;
        }
    }

    function preActionThanhToan() {
        var divAddress = document.getElementById("inputAddress");
        var divFullName = document.getElementById("inputFullName");
        divAddress.innerHTML = "";
        divFullName.innerHTML = "";
        document.getElementById("htnh").value = 1;
        document.getElementById("status").value = 5;
    }

    function handleOnChangeInputMoney(e) {
        var value = e.value;
        var tamTinh = +(document.getElementById("tamTinhValue").innerText.replaceAll(",","").replaceAll(".",""));
        console.log(tamTinh);
        if (value != "" && value != null) {
            if (document.getElementById("htnh").value == 1) {
                value = (+value);
                // moneyTransfersư
                var frmMoney = (value - +tamTinh);
                if(frmMoney >= 0){
                    var valueNew = new Intl.NumberFormat('en-US').format(frmMoney);
                    document.getElementById("moneyTransfers").innerHTML = "Trả lại: " + valueNew + " VND";
                }else{
                    var valueNew = new Intl.NumberFormat('en-US').format(-frmMoney);
                    document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                }
            } else {
                var frmMoney = (+tamTinh -value + 35000);
                var valueNew = new Intl.NumberFormat('en-US').format(frmMoney);
                document.getElementById("moneyTransfers").innerHTML = valueNew;
            }
        }
    }


    function preActionUpdate(quantity, id, quantityProduct) {
        document.getElementById("idBillDetail").value = id;
        document.getElementById("quantity").value = quantity;
        document.getElementById("quantityOld").value = quantity;
        document.getElementById("quantityProduct").value = quantityProduct;
    }

    async function handleOnAction(totalMoney) {
        var voucher = document.getElementById("voucher").value;
        if(!(document.getElementById("voucher").value == "" ||
            document.getElementById("voucher").value == null ||
            document.getElementById("voucher").value == undefined)){
            if(+document.getElementById("tamTinhValue").innerText.replaceAll(",","").replaceAll(".","") == +document.getElementById("totalMoney").innerText.replaceAll(",","").replaceAll(".","")){
                await axios.get('/khuyen-mai/voucherApp/'+ voucher).then(res => {
                    if(res.status == 200){
                        var vc = res.data;
                        if(+totalMoney >= +(vc.minimumInvoice)){
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
                                document.getElementById("tamTinhValue").innerHTML = tamTinh;
                                var tienKD = document.getElementById("moneyCustomer").value;
                                if(tienKD != "" && tienKD != null && tienKD != undefined){
                                    var traLai = tienKD - giaConLai;
                                    if(traLai >= 0){
                                        var valueNew = new Intl.NumberFormat('en-US').format(traLai);
                                        document.getElementById("moneyTransfers").innerHTML = "Trả lại: " + valueNew + " VND";
                                    }else{
                                        var valueNew = new Intl.NumberFormat('en-US').format(-traLai);
                                        document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                                    }
                                }else{
                                    var valueNew = new Intl.NumberFormat('en-US').format(giaConLai);
                                    document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                                }
                            }else{
                                var giaGiam = +vc.promotionalLevel;
                                var giaConLai = (+totalMoney) - giaGiam;
                                var gia = Math.floor(giaGiam).toLocaleString('en-US');
                                var tamTinh = Math.floor(giaConLai).toLocaleString('en-US');
                                document.getElementById("giaGiam").innerHTML = gia;
                                document.getElementById("tamTinhValue").innerHTML = tamTinh;
                                var tienKD = document.getElementById("moneyCustomer").value;
                                if(tienKD != "" && tienKD != null && tienKD != undefined){
                                    var traLai = tienKD - giaConLai;
                                    if(traLai >= 0){
                                        var valueNew = new Intl.NumberFormat('en-US').format(traLai);
                                        document.getElementById("moneyTransfers").innerHTML = "Trả lại: " + valueNew + " VND";
                                    }else{
                                        var valueNew = new Intl.NumberFormat('en-US').format(-traLai);
                                        document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                                    }
                                }else{
                                    var valueNew = new Intl.NumberFormat('en-US').format(giaConLai);
                                    document.getElementById("moneyTransfers").innerHTML = "Còn thiếu: " + valueNew + " VND";
                                }
                            }
                        }else{
                            toastr.error("Đơn hàng chưa đạt giá trị tối thiểu của mã giảm giá");
                            return false;
                        }
                    }else{
                        toastr.error("Mã giảm giá không đúng hoặc đã hết hạn");
                        return false;
                    }
                }).catch(e =>{
                    toastr.error("Mã giảm giá không tồn tại hoặc đã kết thúc");
                    return false;
                });
                return false;
            }
        }

        if (${bill.totalMoney == 0}) {
            toastr.error("Đơn hàng chưa có sản phẩm nào");
            return false;
        }
        if (+document.getElementById("moneyCustomer").value <= 0 || document.getElementById("moneyCustomer").value == "" || document.getElementById("moneyCustomer").value == null
            || document.getElementById("moneyCustomer").value == undefined) {
            toastr.error("Vui lòng nhập tiền khách trả");
            return false;
        }
        var tamTinh = document.getElementById("tamTinhValue").innerText;
        if (+document.getElementById("moneyCustomer").value < (+(tamTinh.replaceAll(",","").replaceAll(".",""))) && document.getElementById("htnh").value == 1) {
            toastr.error("Tiền khách trả không đủ để thanh toán đơn hàng");
            return false;
        } else if (+document.getElementById("moneyCustomer").value < (${bill.totalMoney} +35000) && document.getElementById("htnh").value == 2) {
            toastr.error("Tiền khách trả không đủ để thanh toán đơn hàng");
            return false;
        }
        var sdt = document.getElementById("phoneNumber").value;
        if (!(sdt == "" || sdt == undefined || sdt == null)) {
            var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|34|33|31|32|35|36|37|38|39|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
            if (!phoneNumberRegex.test(document.getElementById("phoneNumber").value)) {
                toastr.error("Số điện thoại không hợp lệ");
                return false;
            }
        }
        if (document.getElementById("htnh").value == 2) {
            var fullName = document.getElementById("fullName").value;
            var address = document.getElementById("addressDetail").value;
            if (sdt == "" || sdt == undefined || sdt == null) {
                toastr.error("Vui lòng nhập số điện thoại");
                return false;
            }
            if (fullName == "" || fullName == undefined || fullName == null) {
                toastr.error("Vui lòng nhập tên nhận hàng");
                return false;
            }
            if (address == null || address == "" || address == undefined) {
                toastr.error("Vui lòng nhập địa chỉ nhận hàng");
                return false;
            }
        }
        if(!confirm("Bạn có muốn tiến hành thanh toán không?")){
            return false;
        }
        document.getElementById("frmAction").submit();
        // if(document.getElementById("phoneNumber").value == "" ||
        //     document.getElementById("phoneNumber") == null){
        //     alert("Vui lòng nhập số điện thoại");
        //     return false;
        // }
    }

    var handleOnActionUpdate = function () {
        if (document.getElementById("quantity").value == null || document.getElementById("quantity").value == "") {
            toastr.error("Vui lòng nhập số lượng");
            return false;
        }
        if (document.getElementById("quantity").value <= 0) {
            toastr.error("Số lượng phải lớn hơn 0");
            return false;
        }
        if (+document.getElementById("quantity").value > (+document.getElementById("quantityProduct").value + (+document.getElementById("quantityOld").value))) {
            toastr.error("Số lượng chỉ còn: " + (+document.getElementById("quantityProduct").value + (+document.getElementById("quantityOld").value)));
            return false;
        }
        document.getElementById("frmActionUpdate").submit();
    }
</script>