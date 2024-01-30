<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/loai-san-pham/action" method="post" id="frmAction">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="name" class="form-label">Tên thuộc tính</label>
                        <input class="form-control" name="name" id="name" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" name="status" id="status" aria-label="Default select example">
                            <option value="1" selected>Hoạt động</option>
                            <option value="0">Không hoạt động</option>
                        </select>
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
        if(document.getElementById("name").value == "" || document.getElementById("name").value == null){
            alert("Tên không được để trống");
            return false;
        }
        document.getElementById("frmAction").submit();
    }
    var preAction = function (id,name,status){
        document.getElementById("id").value = id;
        document.getElementById("name").value = name;
        document.getElementById("status").value = status;
    }
</script>