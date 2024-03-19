<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal fade" id="thanhtoan" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/tai-quay/atStore/${bill.id}" method="post" id="frmAction" onsubmit="handleOnAction()">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber"
                               aria-describedby="emailHelp">
                        <input class="form-control" name="status" value="5" hidden id="status"
                               aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <p>Tổng tiền: <span id="totalMoney"><fmt:formatNumber pattern="#,###"
                                                                              value="${bill.totalMoney}"/></span>
                            VND</p>
                    </div>
                    <div class="mb-3">
                        <p>Trả lại: <span id="moneyTransfers"></span> VND</p>
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Tiền khách đưa:</label>
                        <input class="form-control" type="number" id="moneyCustomer"
                               onchange="handleOnChangeInputMoney(this)" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
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
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" onclick="handleOnAction()" class="btn btn-primary">Đồng ý</button>
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
        if (value != "" && value != null) {
            if (document.getElementById("htnh").value == 1) {
                value = (+value);
                // moneyTransfers
                var frmMoney = (+${bill.totalMoney} -value);
                var valueNew = new Intl.NumberFormat('en-US').format(frmMoney);
                document.getElementById("moneyTransfers").innerHTML = valueNew;
            } else {
                var frmMoney = (+${bill.totalMoney} -value + 35000);
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

    function handleOnAction() {

        if (${bill.totalMoney == 0}) {
            alert("Đơn hàng chưa có sản phẩm nào");
            return false;
        }
        if (+document.getElementById("moneyCustomer").value <= 0 || document.getElementById("moneyCustomer").value == "" || document.getElementById("moneyCustomer").value == null
            || document.getElementById("moneyCustomer").value == undefined) {
            alert("Vui lòng nhập tiền khách trả");
            return false;
        }
        if (+document.getElementById("moneyCustomer").value < ${bill.totalMoney} && document.getElementById("htnh").value == 1) {
            alert("Tiền khách trả không đủ để thanh toán đơn hàng");
            return false;
        } else if (+document.getElementById("moneyCustomer").value < (${bill.totalMoney} +35000) && document.getElementById("htnh").value == 2) {
            alert("Tiền khách trả không đủ để thanh toán đơn hàng");
            return false;
        }
        if (document.getElementById("htnh").value == 2) {
            var fullName = document.getElementById("fullName").value;
            var address = document.getElementById("addressDetail").value;
            var sdt = document.getElementById("phoneNumber").value;
            if (sdt == "" || sdt == undefined || sdt == null) {
                alert("Vui lòng nhập số điện thoại");
                return false;
            }
            if (fullName == "" || fullName == undefined || fullName == null) {
                alert("Vui lòng nhập tên nhận hàng");
                return false;
            }
            if (address == null || address == "" || address == undefined) {
                alert("Vui lòng nhập địa chỉ nhận hàng");
                return false;
            }
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
            alert("Vui lòng nhập số lượng");
            return false;
        }
        if (document.getElementById("quantity").value <= 0) {
            alert("Số lượng phải lớn hơn 0");
            return false;
        }
        if (+document.getElementById("quantity").value > (+document.getElementById("quantityProduct").value + (+document.getElementById("quantityOld").value))) {
            alert("Số lượng chỉ còn: " + (+document.getElementById("quantityProduct").value + (+document.getElementById("quantityOld").value)));
            return false;
        }
        document.getElementById("frmActionUpdate").submit();
    }
</script>