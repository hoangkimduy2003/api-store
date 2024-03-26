<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${not empty tb}">
    <script>
        alert("${tb}")
    </script>
</c:if>
<div class="container" ng-controller="loginController">
    <div class="row" style="margin-bottom: 24px;">
        <span><a href="/trang-chu" style="text-decoration: none; color: black;">TRANG CHỦ</a> | TÀI KHOẢN </span>
    </div>

    <div class="container" style="margin-top: 24px;">
        <div class="row">
            <div class="col-md-6 col-xs-12">
                <div class="container">
                    <div class="row">
                        <h4>ĐỔI MẬT KHẨU</h4>
                    </div>
                    <div style="margin-top: 36px;" class="row">
                        <form id="frmSignUp" action="/account/changePassword" method="post" onsubmit="handleOnSubmit()">
                            <div class="mb-1">
                                <label for="oldPassword" class="form-label">MẬT KHẨU CŨ*</label>
                                <input type="password" class="form-control" id="oldPassword" name="oldPassword">
                                <p id="errOldPassword" style="color: red;font-size: 12px"></p>
                            </div>
                            <div class="mb-1">
                                <label for="newPassword" class="form-label">MẬT KHẨU MỚI*</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword">
                                <p id="errNewPassword" style="color: red;font-size: 12px"></p>
                            </div>
                            <div class="mb-1">
                                <label for="PasswordConfirm" class="form-label">NHẬP LẠI MẬT KHẨU MỚI*</label>
                                <input type="password" class="form-control" id="PasswordConfirm" name="PasswordConfirm">
                                <p id="errRePass" style="color: red;font-size: 12px"></p>

                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <button style="height: 50px; margin-top: 20px;"
                                            class="btn btn-outline-secondary w-100 ">Đổi mật khẩu</button>
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

        if(document.getElementById("oldPassword").value == "" || document.getElementById("oldPassword").value == null){
            alert("Vui lòng nhập mật khẩu cũ");
            return false;
        }
        if(document.getElementById("newPassword").value == "" || document.getElementById("newPassword").value == null){
            alert("Vui lòng nhập mật khẩu mới");
            return false;
        }
        if(document.getElementById("PasswordConfirm").value == "" || document.getElementById("PasswordConfirm").value == null){
            alert("Vui lòng nhập lại mật khẩu mới");
            return false;
        }else if (document.getElementById("newPassword").value != document.getElementById("PasswordConfirm").value ){
            alert("Nhập lại mật khẩu mới không chính xác")
            return false;
        }
        if(!confirm("Bạn có muốn thao tác không?")){
            return false;
        }
        return true;
    }

</script>