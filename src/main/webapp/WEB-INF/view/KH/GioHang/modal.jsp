<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal fade" id="sua-gio-hang" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/gio-hang/update" method="post" id="frmActionUpdate"
                  onsubmit="handleOnActionUpdate()">
                <input class="form-control" name="id" id="idBillDetail" style="display: none"
                       aria-describedby="emailHelp">
                <input class="form-control" name="productDetail.id" id="productDetail" style="display: none"
                       aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Số lượng:</label>
                        <input class="form-control" type="number" name="quantity" id="quantity"
                               aria-describedby="emailHelp">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" onclick="handleOnActionUpdate()" class="btn btn-primary">Đồng ý</button>
                </div>
                <input id="quantityOld" style="display: none">
            </form>
        </div>
    </div>
</div>
<script>

    function handleOnActionUpdate() {
        var quantity = document.getElementById("quantity").value;
        var quantityOld = document.getElementById("quantityOld").value;
        if (quantity == null || quantity == "") {
            toastr.error("Vui lòng nhập số lượng");
            return false;
        }
        if(+quantity < 1){
            toastr.error("Vui lòng nhập số lượng lớn hơn 0");
            return false;
        }
        if(+quantity > +quantityOld){
            toastr.error("Số lượng sản phẩm chỉ còn: " + quantityOld);
            return false;
        }
        document.getElementById("frmActionUpdate").submit();
    }
</script>