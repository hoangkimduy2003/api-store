<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/nhan-vien/action" method="post" id="frmAction">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="userCode" class="form-label">Mã nhân viên</label>
                        <input class="form-control" name="userCode" id="userCode" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Tên nhân viên</label>
                        <input class="form-control" name="fullName" id="fullName" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                        <input class="form-control" name="phoneNumber" id="phoneNumber" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input class="form-control" name="email" id="email" aria-describedby="emailHelp">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button"  onclick="handleOnAction()" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    var handleOnAction = function (){
        if(!confirm("Bạn có muốn thao tác không?")){
            return false;
        }
        if(document.getElementById("userCode").value == "" || document.getElementById("userCode").value == null){
            alert("Mã không được để trống");
            return false;
        }
        if(document.getElementById("fullName").value == "" || document.getElementById("fullName").value == null){
            alert("Tên không được để trống");
            return false;
        }
        if(document.getElementById("phoneNumber").value == "" || document.getElementById("phoneNumber").value == null){
            alert("Số điện thoại không được để trống");
            return false;
        }
        if(document.getElementById("email").value == "" || document.getElementById("email").value == null){
            alert("Email không được để trống");
            return false;
        }
        document.getElementById("frmAction").submit();
    }
    var preAction = function (id,userCode,fullName,phoneNUmber,email){
        document.getElementById("id").value = id;
        document.getElementById("userCode").value = userCode;
        document.getElementById("fullName").value = fullName;
        document.getElementById("phoneNumber").value = phoneNUmber;
        document.getElementById("email").value = email;
    }
</script>