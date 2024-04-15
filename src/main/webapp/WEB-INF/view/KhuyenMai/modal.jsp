<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <form action="/khuyen-mai/action" method="post" id="frmAction" onsubmit="handleOnAction()">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="voucherCode" class="form-label">Mã giảm giá</label>
                                <input class="form-control"  name="voucherCode" id="voucherCode">
                            </div>
                            <div class="mb-3">
                                <label for="minimumInvoice" class="form-label">Đơn tối thiểu</label>
                                <input class="form-control" type="number" name="minimumInvoice" id="minimumInvoice" aria-describedby="emailHelp">
                            </div>
                            <div class="mb-3">
                                <label for="dateStart" class="form-label">Ngày bắt đầu</label>
                                <input class="form-control" type="date" name="dateStart" id="dateStart" aria-describedby="emailHelp">
                            </div>
                            <div class="mb-3">
                                <label for="dateEnd" class="form-label">Ngày kết thúc</label>
                                <input class="form-control" name="dateEnd" type="date" id="dateEnd" aria-describedby="emailHelp">
                            </div>
                            <div class="mb-3">
                                <label for="quantity" class="form-label">Số lượng</label>
                                <input class="form-control" type="number" name="quantity" id="quantity" aria-describedby="emailHelp">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="voucherType" class="form-label">Giảm giá theo</label>
                                <select class="form-select" onchange="handleOnChangeVoucherType(this)" name="voucherType" id="voucherType" aria-label="Default select example">
                                    <option value="1" selected>Phần trăm</option>
                                    <option value="2">Giá</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="promotionalLevel" class="form-label">Giá trị giảm (Giá/%)</label>
                                <input class="form-control" type="number" name="promotionalLevel" id="promotionalLevel">
                            </div>
                            <div class="mb-3" id="maximumPromotionDiv">
                                <label for="maximumPromotion" class="form-label">Giảm tối đa</label>
                                <input class="form-control" type="number" name="maximumPromotion" id="maximumPromotion">
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng thái</label>
                                <select class="form-select" name="status" id="status" aria-label="Default select example">
                                    <option value="1" selected>Hoạt động</option>
                                    <option value="0">Không hoạt động</option>
                                </select>
                            </div>
                        </div>
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
        //
        if(validate()){
            var voucherCode = document.getElementById("voucherCode").value;
            await axios.get("/khuyen-mai/voucherApp/"+voucherCode).then(res => {
                toastr.error("Mã khuyến mại đã tồn tại");
            }).catch(e => {
                document.getElementById("frmAction").submit();
            })
        }
    }


    function validate(){
        var voucherCode = document.getElementById("voucherCode").value;
        var minimumInvoice = document.getElementById("minimumInvoice").value;
        var dateStart = document.getElementById("dateStart").value;
        var dateEnd = document.getElementById("dateEnd").value;
        var quantity = document.getElementById("quantity").value;
        var promotionalLevel = document.getElementById("promotionalLevel").value;
        var maximumPromotion = document.getElementById("maximumPromotion").value;
        var voucherType = document.getElementById("voucherType").value;
        console.log(dateStart);
        if(voucherCode == null || voucherCode.trim() == "" || voucherCode == undefined){
            toastr.error("Vui lòng nhập mã khuyến mại");
            return false;
        }
        if(voucherCode.length > 10){
            toastr.error("Độ dài mã tối đa 10 kí tự");
            return false;
        }
        if(minimumInvoice == null || minimumInvoice == "" || minimumInvoice == undefined){
            toastr.error("Vui lòng nhập giá trị tối thiểu đơn hàng");
            return false;
        }
        if(dateStart == null || dateStart == "" || dateStart == undefined){
            toastr.error("Vui lòng nhập ngày bắt đầu");
            return false;
        }
        if(dateEnd == null || dateEnd == "" || dateEnd == undefined){
            toastr.error("Vui lòng nhập ngày kết thúc");
            return false;
        }
        if(new Date(dateStart) > new Date(dateEnd)){
            toastr.error("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            return false;
        }
        if(quantity == null || quantity == "" || quantity == undefined){
            toastr.error("Vui lòng nhập số lượng");
            return false;
        }
        if(promotionalLevel == null || promotionalLevel == "" || promotionalLevel == undefined){
            toastr.error("Vui lòng nhập giá trị giá");
            return false;
        }
        if(voucherType == 1){
            if(maximumPromotion == null || maximumPromotion == "" || maximumPromotion == undefined){
                toastr.error("Vui lòng nhập giảm tối đa");
                return false;
            }
        }
        return true;
    }

    var preAction = function (id,voucherCode,minimumInvoice,dateStart,dateEnd,quantity,voucherType,promotionalLevel,maximumPromotion,status,action){

        document.getElementById("id").value = id;
        document.getElementById("voucherCode").value = voucherCode;
        document.getElementById("minimumInvoice").value = minimumInvoice;
        document.getElementById("dateStart").value = dateStart;
        document.getElementById("dateEnd").value = dateEnd;
        document.getElementById("quantity").value = quantity;
        document.getElementById("voucherType").value = voucherType;
        document.getElementById("promotionalLevel").value = promotionalLevel;
        document.getElementById("maximumPromotion").value = maximumPromotion;
        document.getElementById("status").value = status;
        if(action == 1){
            document.getElementById("voucherCode").disabled = false;
            document.getElementById("status").disabled = false;
        }else{
            document.getElementById("voucherCode").disabled = true;
            document.getElementById("status").disabled = true;
        }
    }

    var handleOnChangeVoucherType = function (e){
        var value = +e.value;
        if(value == 1){
            document.getElementById("maximumPromotionDiv").style.display = "block";
        }else{
            document.getElementById("maximumPromotionDiv").style.display = "none";
        }
    }
</script>