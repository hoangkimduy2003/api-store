<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" ng-controller="loginController">
    <div class="row" style="margin-bottom: 24px;">
        <span><a href="/trang-chu" style="text-decoration: none; color: black;">TRANG CHỦ</a> | TÀI KHOẢN </span>
    </div>
    <c:if test="${not empty tb}">
        <script>
            alert("${tb}")
        </script>
    </c:if>
    <div class="container" style="margin-top: 24px;">
        <div class="row">
            <div class="col-md-6 col-xs-12">
                <div class="container">
                    <div class="row">
                        <h4>TÀI KHOẢN</h4>
                    </div>
                    <div style="margin-top: 36px;" class="row">
                        <form id="frmSignUp" action="/thong-tin-tai-khoan" method="post" onsubmit="handleOnSubmit()">
                            <div class="mb-1">
                                <label for="phoneNumber" class="form-label">SỐ ĐIỆN THOẠI*</label>
                                <input type="text" class="form-control" id="phoneNumber"
                                       name="phoneNumber" value="${user.phoneNumber}">
                                <p id="errSdt" style="color: red;font-size: 12px">${msg}</p>
                            </div>
                            <div class="mb-1">
                                <label for="email" class="form-label">EMAIL</label>
                                <input type="text" class="form-control" id="email"
                                       name="email" value="${user.email}">
                                <p id="errEmail" style="color: red;font-size: 12px">${msgEmail}</p>
                            </div>
                            <div class="mb-1">
                                <label for="fullName" class="form-label">FULL NAME*</label>
                                <input type="text" class="form-control" id="fullName"
                                       name="fullName" value="${user.fullName}">
                                <p id="errName" style="color: red;font-size: 12px"></p>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <button style="height: 50px; margin-top: 20px;"
                                            class="btn btn-outline-secondary w-100 ">CẬP NHẬT THÔNG TIN</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xs-12">
                <img src="https://cdn.pixabay.com/photo/2021/07/25/08/03/account-6491185_1280.png" class="w-100 d-block" alt="">
            </div>
        </div>
    </div>
</div>
<script>
    var handleOnSubmit = function (){
        if(document.getElementById("phoneNumber").value == "" || document.getElementById("phoneNumber").value == null){
            alert("Vui lòng nhập số điện thoại");
            return false;
        }
            // else{
        //     // Biểu thức chính quy kiểm tra số điện thoại Việt Nam
        //     let phoneNumberPattern = /^(0|\+84)(3[2-9]|5[2689]|7[0|6-9]|8[1-9]|9[0-9])\d{7}$/;
        //     if (!phoneNumberPattern.test(document.getElementById("phoneNumber").value)) {
        //         alert("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
        //         return false;
        //     }
        // }
        // if(document.getElementById("email").value!=""){
        //     // Biểu thức chính quy kiểm tra định dạng email
        //     var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        //
        //     if (!emailPattern.test(document.getElementById("email").value)) {
        //         alert("Email không hợp lệ. Vui lòng nhập lại.");
        //         return false;
        //     }
        // }
        if(document.getElementById("fullName").value == "" || document.getElementById("fullName").value == null){
            alert("Vui lòng nhập tên");
            return false;
        }
        if(!confirm("Bạn có muốn thao tác không?")){
            return false;
        }
        // alert("Cập nhật thông tin thành công")
        return true;
    }

</script>