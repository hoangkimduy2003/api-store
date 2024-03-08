<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/danh-sach-san-pham/create" method="post" id="frmAction" onsubmit="handleOnAction()">
                <input id="quantityOld" type="number" style="display: none">
                <input class="form-control" name="bill.id" id="billId" value="${idBill}" style="display: none">
                <input class="form-control" name="productDetail.id" id="productDetailId" style="display: none">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Số lượng</label>
                        <input class="form-control" name="quantity" id="quantity" >
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
        if(document.getElementById("quantity").value == "" || document.getElementById("quantity").value == null
            || document.getElementById("quantity").value < 1){
            alert("Vui lòng nhập số lượng");
            return false;
        }
        if(+document.getElementById("quantity").value > +document.getElementById("quantityOld").value){
            alert("Số lượng sản phẩm chỉ còn: " + document.getElementById("quantityOld").value);
            return false;
        }
        document.getElementById("frmAction").submit();
    }
    var preAction = function (productDetailId, quantityOld){
        document.getElementById("productDetailId").value = productDetailId;
        document.getElementById("quantityOld").value = quantityOld;
        console.log(productDetailId);
        console.log(quantityOld);
    }
</script>