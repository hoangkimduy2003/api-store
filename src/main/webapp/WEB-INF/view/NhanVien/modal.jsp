<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/nhan-vien/action" method="post" id="frmAction">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="name" class="form-label">Họ và tên</label>
                        <input class="form-control" name="fullName" id="name" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                        <input class="form-control" name="phoneNumber" id="phoneNumber" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input class="form-control" name="email" id="email" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" name="status" id="status" aria-label="Default select example">
                            <option value="1" selected>Hoạt động</option>
                            <option value="0">Không hoạt động</option>
                        </select>
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
        if(document.getElementById("name").value == null || document.getElementById("name").value == ""){
            toastr.error("Vui lòng nhập tên");
            return false;
        }

        if(document.getElementById("phoneNumber").value == null || document.getElementById("phoneNumber").value == ""){
            toastr.error("Vui lòng nhập số điện thoại");
            return false;
        }
        var phoneNumberRegex = /^(?!(0{10}|(\+?84|0)0+)\b)(\+?84|0)(86|88|89|96|97|98|90|93|70|79|77|76|78|91|94|88|85|81|82|83|84|92|56|58|99|34|39|36|59|87|89|81|82|83|84|85|86|88|89|99|96|97|98|96|97|98|96|97|98)\d{7}$/;
        if (!phoneNumberRegex.test(document.getElementById("phoneNumber").value)) {
            toastr.error("Số điện thoại không hợp lệ");
            return false;
        }
        if(document.getElementById("email").value == null || document.getElementById("email").value == ""){
            toastr.error("Vui lòng nhập email");
            return false;
        }
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regex.test(document.getElementById("email").value)) {
            toastr.error("Email không hợp lệ");
            return false;
        }
        if(!confirm("Bạn có muốn thao tác không?")){
            return false;
        }
        document.getElementById("frmAction").submit();
    }
    var preAction = function (id,name,status,phoneNumber, email){
        document.getElementById("id").value = id;
        document.getElementById("name").value = name;
        document.getElementById("status").value = status;
        document.getElementById("phoneNumber").value = phoneNumber;
        document.getElementById("email").value = email;
    }
</script>