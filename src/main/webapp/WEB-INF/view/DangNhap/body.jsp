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
                        <h4>ĐĂNG NHẬP</h4>
                        <p style="font-size: 12px;">Nếu bạn đã có tài khoản, hãy đăng nhập để tích lũy điểm thành
                            viên và nhận được những ưu đãi tốt hơn!</p>
                    </div>
                    <div style="margin-top: 36px;" class="row">
                        <form id="frmLogin" action="/account/dang-nhap" method="post" onsubmit="handleOnSubmit()">
                            <div class="mb-3">
                                <label for="phoneNumber" class="form-label">SỐ ĐIỆN THOẠI</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">MẬT KHẨU</label>
                                <input type="password" class="form-control" id="password" name="password">
                            </div>
                            <div class="mb-3">
                                <p style="color: red;font-size: 12px">${msg}</p>
                            </div>
                            <div class="row">
                                <div class="col-12"><button type="submit" style="height: 50px; margin-top: 36px;"
                                                            class="btn btn-dark w-100">ĐĂNG NHẬP</button></div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <a href="/account/createAccount">
                                        <p style="height: 50px; margin-top: 64px;"
                                                class="btn btn-outline-secondary w-100 ">TẠO TÀI KHOẢN</p>
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xs-12">
                <img src="https://bom.so/XnRTvM" class="w-100 d-block" alt="">
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
        if(document.getElementById("password").value == "" || document.getElementById("password").value == null){
            alert("Vui lòng nhập mật khẩu");
            return false;
        }
        return true;
    }

</script>