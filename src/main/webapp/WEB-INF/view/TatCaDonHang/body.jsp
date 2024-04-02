<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container m-2">
    <div class="row">
        <form id="searchForm" action="/don-hang" method="get" onsubmit="handleOnSubmitSearch()">
            <div class="row">
                <div class="col-1">
                    <label for="staff" class="form-label">Nhân viên</label>
                    <input class="form-control" name="staff" id="staff" value="${searchBillDTO.staff}"
                           aria-describedby="emailHelp">
                </div>
                <div class="col-2">
                    <label for="phoneNumber" class="form-label">Số điện thoại</label>
                    <input class="form-control" name="phoneNumber" id="phoneNumber" value="${searchBillDTO.phoneNumber}">
                </div>
                <div class="col-2">
                    <label for="strDateStart" class="form-label">Từ ngày</label>
                    <input class="form-control" name="strDateStart" type="date" id="strDateStart" value="${searchBillDTO.strDateStart}">
                </div>
                <div class="col-2">
                    <label for="strDateEnd" class="form-label">Đến ngày</label>
                    <input class="form-control" name="strDateEnd" type="date" id="strDateEnd" value="${searchBillDTO.strDateEnd}">
                </div>
                <div class="col-2">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select class="form-select" name="status" id="status"
                            aria-label="Default select example">
                        <option value="-1" ${searchBillDTO.status == (-1) ? "selected" : ""}>--Tất cả--</option>
                        <option value="0" ${searchBillDTO.status == (0) ? "selected" : ""}>Đã huỷ</option>
                        <option value="1" ${searchBillDTO.status == (1) ? "selected" : ""}>Đang chờ</option>
                        <option value="3" ${searchBillDTO.status == (3) ? "selected" : ""}>Chờ lấy hàng</option>
                        <option value="4" ${searchBillDTO.status == (4) ? "selected" : ""}>Đang giao</option>
                        <option value="5" ${searchBillDTO.status == (5) ? "selected" : ""}>Đã hoàn thành</option>
                        <option value="6" ${searchBillDTO.status == (6) ? "selected" : ""}>Trà hàng</option>
                    </select>
                </div>
                <div class="col-2">
                    <label for="billType" class="form-label">Loại đơn hàng</label>
                    <select class="form-select" name="billType" id="billType"
                            aria-label="Default select example">
                        <option value="-1" ${searchBillDTO.billType == (-1) ? "selected" : ""}>--Tất cả--</option>
                        <option value="1" ${searchBillDTO.billType == (1) ? "selected" : ""}>Tại quầy</option>
                        <option value="2" ${searchBillDTO.billType == (2) ? "selected" : ""}>Online</option>
                    </select>
                </div>
                <div class="col-1">
                    <label for="search-input" class="form-label">Search</label>
                    <input type="button" onclick="handleOnSubmitSearch()" value="Tìm" id="search-input" class="btn btn-info">
                </div>
            </div>
        </form>
    </div>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Tên người nhận</th>
                <th scope="col">Số điện thoại</th>
<%--                <th scope="col">Địa chỉ</th>--%>
<%--                <th scope="col">Tổng sản phẩm</th>--%>
                <th scope="col">Tổng tiền</th>
                <th scope="col">Ngày tạo</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bills.data}" var="bill" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${bill.fullName}</td>
                    <td>${bill.phoneNumber}</td>
<%--                    <td>${bill.addressDetail}</td>--%>
<%--                    <td><fmt:formatNumber pattern="#,###" value="${bill.tatolProduct}"/></td>--%>
                    <td><fmt:formatNumber pattern="#,###" value="${bill.totalMoney}"/></td>
                    <td>${bill.orderDate}</td>
                    <td>
                        ${bill.status == 1 ? "Chờ xác nhận" : (bill.status == 2 ? "Đang xử lý" :
                                bill.status == 3 ? "Chờ lấy hàng" : ( bill.status ==  4 ? "Đang giao" :
                                        (bill.status == 5 ? (bill.billType==1?"Hoàn thành" :"Hoàn thành") : (bill.status == 6 ? "Trả hàng" : "Đã huỷ"))))}
                    </td>
                    <td>
                        <a style="color: aliceblue;" class="btn btn-info" href="/don-hang/chi-tiet/${bill.id}" ><i class="bi bi-eye"></i>
                        </a>
                        <button class="btn btn-dark" style="${(bill.status != 1) ? "display: none" : ""}" onclick="handleOnClickXacNhan('${bill.id}')">Xác nhận</button>
                        <a class="btn btn-dark"
                           id="xacNhanAll${bill.id}"
                           style="display: none"
                           href="/don-hang/updateStatus/${bill.id}/3/3"
                        >Xác nhận</a>
                        <a class="btn btn-dark"
                           style="${(bill.status != 3) ? "display: none" : ""}"
                           href="/don-hang/updateStatus/${bill.id}/4/3"
                           onclick="return confirm('Xác nhận giao hàng')"
                        >Giao hàng</a>
                        <a class="btn btn-dark"
                           style="${(bill.status != 4) ? "display: none" : ""}"
                           href="/don-hang/updateStatus/${bill.id}/5/3"
                           onclick="return confirm('Xác nhận giao hàng thành công')"
                           >Giao hàng thành công</a>
                        <a class="btn btn-danger"
                           style="${(bill.status != 4) ? "display: none" : ""}"
                           href="/don-hang/updateStatus/${bill.id}/5/3"
                           onclick="return confirm('Xác nhận giao hàng thất bại')"
                        >Giao hàng thất bại</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <ul class="pagination">
            <c:forEach begin="1" end="${bills.totalPages}" varStatus="loop">
                <li class="page-item">
                    <a class="page-link" href="/don-hang?page=${loop.begin + loop.count -2}">
                            ${loop.begin + loop.count -1}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<script>
    function handleOnSubmitSearch(){
        // if(document.getElementById("dateEnd").value == '' || document.getElementById("dateEnd").value == undefined
        //     || document.getElementById("dateEnd").value === ''){
        //     document.getElementById("dateEnd").value = null;
        // }
        // if(document.getElementById("dateStart").value == '' || document.getElementById("dateStart").value == undefined
        //     || document.getElementById("dateStart").value === ''){
        //     document.getElementById("dateStart").value = null;
        // }
        document.getElementById("searchForm").submit();
    }
    function handleOnClickXacNhan2(e){
        var _href = a.href;
        var quantity = prompt("Nhập phí ship: ")
        console.log(quantity);
        if(quantity == null || quantity == "" || quantity < 0){
            toastr.error("Vui lòng nhập phí ship!");
            return false;
        }
        if(isNaN(quantity)){
            toastr.error("Phí ship phải là số");
            return false;
        }

    }
    function handleOnClickXacNhan(id){
        var a = document.getElementById("xacNhanAll"+id);
        var _href = a.href;
        var quantity = prompt("Nhập phí ship: ")
        console.log(quantity);
        if(quantity == null || quantity == "" || quantity < 0){
            toastr.error("Vui lòng nhập phí ship!");
            return false;
        }
        if(isNaN(quantity)){
            toastr.error("Phí ship phải là số");
            return false;
        }
        _href = _href + "?ship=" + quantity;
        a.setAttribute("href", _href);
        toastr.success("Xác nhận thành công")
        setTimeout(function() {
            a.click();
        }, 500);

    }

</script>