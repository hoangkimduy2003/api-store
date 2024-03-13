<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="thanhtoan" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/tai-quay/atStore/${bill.id}" method="post" id="frmAction" onsubmit="handleOnAction()">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                        <input class="form-control" name="user.phoneNumber" id="phoneNumber" aria-describedby="emailHelp">
                        <input class="form-control" name="status" value="5" hidden id="status" aria-describedby="emailHelp">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit"  onclick="handleOnAction()" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="sua-gio-hang" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/danh-sach-san-pham/update" method="post" id="frmActionUpdate" onsubmit="handleOnActionUpdate()">
                <input class="form-control" name="id" id="idBillDetail"  style="display: none" aria-describedby="emailHelp">
                <input class="form-control" name="bill.id" id="idBill" value="${bill.id}"  style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số lượng:</label>
                        <input class="form-control" type="number" name="quantity" id="quantity" aria-describedby="emailHelp">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button"  onclick="handleOnActionUpdate()" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function preActionUpdate(quantity, id){
        document.getElementById("idBillDetail").value = id;
        document.getElementById("quantity").value = quantity;
    }
    function handleOnAction(){
        // if(document.getElementById("phoneNumber").value == "" ||
        //     document.getElementById("phoneNumber") == null){
        //     alert("Vui lòng nhập số điện thoại");
        //     return false;
        // }
    }
    var  handleOnActionUpdate = function (){
        if(document.getElementById("quantity").value == null || document.getElementById("quantity").value == ""){
            alert("Vui lòng nhập số lượng");
            return false;
        }
        console.log(document.getElementById("quantity").value);
        if(document.getElementById("quantity").value <= 0){
            alert("Số lượng phải lớn hơn 0");
            return false;
        }
        document.getElementById("frmActionUpdate").submit();
    }
</script>