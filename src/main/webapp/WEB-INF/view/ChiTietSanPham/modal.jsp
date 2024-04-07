<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/chi-tiet-sp/action" method="post" id="frmAction">
                <input class="form-control" name="id" id="id" style="display: none" >
                <input class="form-control" name="product.id" id="productId" value="${idSp}" style="display: none" >
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="color" class="form-label">Màu sắc</label>
                        <select class="form-select" name="color.id" id="color" aria-label="Default select example">
                            <option value="-1" selected>--Màu sắc--</option>
                            <c:forEach items="${colors}" var="x">
                                <option value="${x.id}">${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="size" class="form-label">Kích cỡ</label>
                        <select class="form-select" name="size.id" id="size" aria-label="Default select example" >
                            <option value="-1" selected>--Kích cỡ--</option>
                            <c:forEach items="${sizes}" var="x">
                                <option value="${x.id}">${x.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Số lượng</label>
                        <input class="form-control" type="number" name="quantity" id="quantity" aria-describedby="emailHelp">
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
        var color = document.getElementById("color").value;
        var size = document.getElementById("size").value;
        if(!confirm("Bạn có muốn thao tác không?")){
            return false;
        }
        if(document.getElementById("color").value == -1 ){
            alert("Vui lòng chọn màu sắc");
            return false;
        }
        if(document.getElementById("size").value == -1 ){
            alert("Vui lòng chọn kích cỡ");
            return false;
        }
        if(document.getElementById("quantity").value == "" ){
            alert("Vui lòng nhập số lượng");
            return false;
        }
        if(document.getElementById("quantity").value < 1){
            console.log(typeof color);
            console.log(color);
            alert("Số lượng phải lớn hơn 0");
            return false;
        }
        // document.getElementById("frmAction").submit();
        await axios.get(`/chi-tiet-sp/check?idProduct=${idSp}`+`&`+`idColor=`+color+`&`+`idSize=`+ size)
            .then(function (response) {

                if(response.data===-1){
                    document.getElementById("frmAction").submit();
                }else {
                    if(!(document.getElementById("id").value != null && document.getElementById("id").value != "")){
                        alert("Chi  tiết sản phẩm đã tồn tại");
                        return false;
                    }else{
                        document.getElementById("frmAction").submit();
                    }
                }
            })

    }
    var preAction = function (id,quantity,size,color){
        document.getElementById("id").value = id;
        document.getElementById("quantity").value = quantity;
        document.getElementById("size").value = size;
        document.getElementById("color").value = color;
    }
</script>