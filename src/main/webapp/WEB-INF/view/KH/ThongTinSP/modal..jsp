<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="addCart" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" id="frmAction" onsubmit="handleOnAction()">
                <input class="form-control" name="productDetail.id" id="productDetailId" style="display: none">
                <input id="quantityOld" type="number" style="display: none">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Số lượng</label>
                        <input class="form-control" type="number" name="quantity" id="quantity" >
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
    var handleOnAction = async function (){
        if(document.getElementById("quantity").value == "" || document.getElementById("quantity").value == null
            || document.getElementById("quantity").value < 1){
            toastr.error("Vui lòng nhập số lượng");
            return false;
        }
        if(document.getElementById("quantity").value.includes(".") || document.getElementById("quantity").value.includes(",")){
            toastr.error("Số lượng phải là số nguyên");
            return false;
        }
        await axios.get('/CTSP/checkQuantity/'+ document.getElementById("productDetailId").value)
            .then(function (response) {
                // Xử lý dữ liệu trả về nếu yêu cầu thành công
                var quantity = document.getElementById("quantity").value;
                var quantityReal = +document.getElementById("quantityOld").value;
                if((+quantity + (+response.data)) > +quantityReal){
                    toastr.error("Số lượng chỉ còn: " + quantityReal +", hiện tại trong giỏ hàng của bạn đang có " + response.data + " sản phẩm");
                    return false;
                }
                var formCreate = document.getElementById("frmAction");
                console.log(document.getElementById("quantityOld").value);
                formCreate.action = "/CTSP/create/${product.id}";
                formCreate.submit();
            })
            .catch(function (error) {
                // Xử lý lỗi nếu yêu cầu thất bại
                console.error(error);
            });
        <%--if(+document.getElementById("quantity").value > +document.getElementById("quantityOld").value){--%>
        <%--    alert("Số lượng sản phẩm chỉ còn: " + document.getElementById("quantityOld").value);--%>
        <%--    return false;--%>
        <%--}--%>

    }
    var preAction = function (){
    }

</script>