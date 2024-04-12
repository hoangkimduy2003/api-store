<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
    var handleCreateBill = function () {
        var size = ${sizeBill};
        if (size == 5) {
            alert("Chỉ được tối đa 5 hoá đơn chờ");
            return;
        }
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "/tai-quay/create");
        document.body.appendChild(form);
        form.submit();
        document.body.removeChild(form);
        //
    }
    var handleCancelBill = function (id) {
        if (!confirm("Bạn có đồng ý huỷ hoá đơn không?")) {
            return;
        }
        var aremove = document.createElement("a");
        aremove.setAttribute("href", "/tai-quay/huy/" + id);
        aremove.click();
        aremove.remove();
    }

    var handleOnClickDetailBill = function (id) {
        var aremove = document.createElement("a");
        aremove.setAttribute("href", "/tai-quay?idBill=" + id);
        aremove.click();
        aremove.remove();
    }
</script>
<style>
    .cancel-bill:hover {
        background-color: antiquewhite;
    }
</style>
<div class="container m-2 ">
    <jsp:include page="modal.jsp"></jsp:include>
    <div>
        <ul class="nav">
            <li class="nav-item fs-6 ">
                <button class="btn btn-dark " onclick="handleCreateBill()">Tạo hoá đơn</button>
            </li>
            <c:forEach items="${listBill.data}" var="x">
                <li class="nav-item fs-6 ">
                    <a class=" billCode btn ${x.id == bill.id ? "btn-secondary" : ""}"
                       style="color:black; text-decoration: none">
                        <span style="cursor:pointer "
                              onclick="handleOnClickDetailBill(${x.id})">HĐ: ${x.billCode}</span>
                        <i class="bi bi-x cancel-bill" style="cursor:pointer " onclick="handleCancelBill(${x.id})"></i>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <p/>
    <div class="d-flex justify-content-between">
        <div>
            <button class="btn btn-dark" onclick="toggleCamera('${bill.id}')">Quét QR</button>
            <button onclick="handleOnClickThemSanPham('${bill.id}')" class="btn btn-dark" >Tìm sản phẩm</button>&nbsp;
            <a class="btn btn-dark" id="aTimSP" style="display: none" href="/danh-sach-san-pham/${bill.id}/${bill.billType}">Tìm sản phẩm</a>&nbsp;
            <button class="btn btn-dark" onclick="handleOnClickThanhToan('${bill.id}')">Thanh toán</button>
            <button data-bs-toggle="modal" id="btnShowTT" style="display: none" data-bs-target="#thanhtoan" onclick="preActionThanhToan()" class="btn btn-dark">Thanh toán</button>
        </div>
        <div>
            <video id="qr-video" width="240" height="160" style="display: none" autoplay></video>
            <canvas id="qr-canvas" width="400" height="300" style="display:none;"></canvas>
        </div>
    </div>
    <div style="min-height: 320px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">MÃ SP</th>
                <th scope="col">TÊN SP</th>
                <th scope="col">SIZE</th>
                <th scope="col">MÀU SẮC</th>
                <th scope="col">SỐ LƯỢNG</th>
                <th scope="col">GIÁ</th>
                <th scope="col">THÀNH TIỀN</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.productDetail.productDetailCode}</td>
                    <td>${x.productDetail.name}</td>
                    <td>${x.productDetail.size.name}</td>
                    <td>${x.productDetail.color.name}</td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.quantity}" /></td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.price}" /></td>
                    <td><fmt:formatNumber pattern="#,###" value="${x.totalPrice}" /></td>
                    <td>
                        <button type="button" class="btn btn-warning"
                                onclick="preActionUpdate(${x.quantity}, ${x.id}, ${x.productDetail.quantity})"
                                data-bs-toggle="modal" data-bs-target="#sua-gio-hang">
                            Sửa
                        </button>
                        <a class="btn btn-danger" href="/tai-quay/deleteCart/${x.id}/${bill.id}"
                           onclick="handleOnClickDeleteCartDetail()">Xoá</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="row">
        <div class="col-8">
            <ul class="pagination">
                <c:forEach begin="1" end="${list.totalPages}" varStatus="loop">
                    <li class="page-item">
                        <a class="page-link" href="/tai-quay?page=${loop.begin + loop.count -2}&idBill=${bill.id}">
                                ${loop.begin + loop.count -1}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-4">
            Tổng tiền: <fmt:formatNumber pattern="#,###" value="${bill.totalMoney}" /> VND
        </div>
    </div>
</div>
<script>

    function handleOnClickDeleteCartDetail() {
        if (!confirm("Bạn có muốn xoá không")) {
            return false;
        } else {
            return true;
        }
    }

    const video = document.getElementById('qr-video');
    const canvas = document.getElementById('qr-canvas');
    const context = canvas.getContext('2d');
    let isCameraOn = false;

    function toggleCamera(billId) {
        if(billId != null && billId != "" && billId != undefined){
            if (isCameraOn) {
                video.style.display = 'none';
                canvas.style.display = 'none';
                isCameraOn = false;
            } else {
                startCamera();
                isCameraOn = true;
            }
        }else{
            toastr.error("Vui lòng tạo hoá đơn trước");
        }
    }

    function handleOnClickThanhToan(billId){
        if(billId != null && billId != "" && billId != undefined){
            document.getElementById("btnShowTT").click();
        }else{
            toastr.error("Vui lòng tạo hoá đơn trước");
        }
    }

    function handleOnClickThemSanPham(billId){
        console.log(billId);/**/
        if(billId != null && billId != "" && billId != undefined){
            document.getElementById("aTimSP").click();
        }else{
            toastr.error("Vui lòng tạo hoá đơn trước");
        }
    }

    function startCamera() {
        navigator.mediaDevices.getUserMedia({video: {facingMode: 'environment'}})
            .then(stream => {
                video.srcObject = stream;
                video.play();
                requestAnimationFrame(tick);
            })
            .catch(error => {
                console.error('Unable to access the camera/webcam.', error);
            });
        video.style.display = 'block';
        canvas.style.display = 'none';
    }

    async function tick() {
        if (video.readyState === video.HAVE_ENOUGH_DATA) {
            canvas.height = video.videoHeight;
            canvas.width = video.videoWidth;
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, imageData.width, imageData.height);
            if (code) {
                console.log('Found QR code:', code.data);
                // handleQRCode(code.data); // You can send the QR data to a backend or handle it as needed.
               await axios.get('/chi-tiet-sp/api/' + code.data)
                    .then(response => {
                        console.log(response.data);
                        var input = prompt("Nhập số:");
                        var number = parseFloat(input); // Chuyển đổi input thành số

                        if (isNaN(number)) {
                            alert("Bạn đã nhập không phải là số. Vui lòng nhập lại.");
                        } else {
                            axios.get('/chi-tiet-sp/api/' + code.data)
                                .then(function (response) {
                                    // Handle success
                                    let quantity = response.data.quantity;
                                    if(quantity < number){
                                        alert("Sản phẩm chỉ còn " + quantity);
                                        return;
                                    }else{
                                        var form = document.createElement("form");
                                        form.setAttribute("method", "post");
                                        form.setAttribute("action", "/danh-sach-san-pham/create");

                                        var input = document.createElement("input");
                                        input.setAttribute("type", "hidden"); // Đặt loại input là hidden
                                        input.setAttribute("name", "bill.id");
                                        input.setAttribute("value", "${bill.id}");
                                        var input2 = document.createElement("input");
                                        input2.setAttribute("type", "hidden"); // Đặt loại input là hidden
                                        input2.setAttribute("name", "quantity");
                                        input2.setAttribute("value", number);
                                        var input3 = document.createElement("input");
                                        input3.setAttribute("type", "hidden"); // Đặt loại input là hidden
                                        input3.setAttribute("name", "productDetail.id");
                                        input3.setAttribute("value", response.data.id);
                                        form.appendChild(input3);
                                        form.appendChild(input2);
                                        form.appendChild(input);
                                        document.body.appendChild(form);
                                        form.submit();
                                        document.body.removeChild(form);
                                    }
                                })
                        }
                    }).catch(function (error) {
                       toastr.error("Sản phẩm không tồn tại");
                    return;
                });

            }
        }
        if (isCameraOn) {
            requestAnimationFrame(tick);
        }
    }

</script>