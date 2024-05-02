<%@ page import="com.duyhk.clothing_ecommerce.dto.ColorDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.duyhk.clothing_ecommerce.dto.SizeDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-xl">
        <div class="modal-content">
            <form action="/san-pham/action" method="post" id="frmAction" enctype="multipart/form-data">
                <input class="form-control" name="id" id="id" style="display: none" aria-describedby="emailHelp">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="nameProduct" class="form-label">Tên sản phẩm</label>
                                <input class="form-control"  name="name" id="nameProduct">
                                <label id="nameErr" style="color: red "></label>
                            </div>
                            <div class="mb-3">
                                <label for="filesUpload" class="form-label">
                                    <div class="row">
                                        <div id="imagesUpload" style="width: auto;"></div>
                                        <i style="font-size: 70px; width: auto" class="bi bi-plus-square-dotted"></i>
                                    </div>
                                </label>
                                <input class="form-control" style="display: none" type="file" name="filesUpload"
                                       id="filesUpload" multiple onchange="handleOnChangeFile(this)">
                                <label id="filesUploadErr" style="color: red "></label>
                            </div>
                            <div class="mb-3">
                                <label for="importPrice" class="form-label">Giá nhập</label>
                                <input class="form-control" type="number" name="importPrice"
                                       id="importPrice">
                                <label id="importPriceErr" style="color: red "></label>
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Giá bán</label>
                                <input class="form-control" type="number" name="price" id="price">
                                <label id="priceErr" style="color: red "></label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="brandId" class="form-label">Thương hiệu</label>
                                <select class="form-select" name="brand.id" id="brandId"
                                        aria-label="Default select example">
                                    <option value="-1" >--Thương hiệu--</option>
                                    <c:forEach items="${brands}" var="x">
                                        <option value="${x.id}">${x.name}</option>
                                    </c:forEach>
                                </select>
                                <label id="brandErr" style="color: red "></label>
                            </div>
                            <div class="mb-3">
                                <label for="categoryId" class="form-label">Loại sản phẩm</label>
                                <select class="form-select" name="category.id" id="categoryId"
                                        onchange="handleOnChange(this)"
                                        aria-label="Default select example">
                                    <option value="-1">--Loại sản phẩm--</option>
                                    <c:forEach items="${categories}" var="x">
                                        <option value="${x.id}">${x.name}</option>
                                    </c:forEach>
                                </select>
                                <label id="categoryErr" style="color: red "></label>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Mô tả sản phẩm</label>
                                <textarea class="form-control" name="description" id="description"></textarea>
                                <label id="descriptionErr" style="color: red "></label>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" onclick="handleOnAction()" class="btn btn-primary">Đồng ý</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    var handleOnAction = function () {
        if(document.getElementById("nameProduct").value == "" ){
            var errorMessage = document.getElementById("nameErr");
            errorMessage.innerText = "Vui lòng điền tên ";
            return false;
        }
        if(document.getElementById("filesUpload").value == null ){
            var errorMessage = document.getElementById("filesUploadErr");
            errorMessage.innerText = "Vui lòng chọn ảnh cho sản phẩm";
            return false;
        }
        if(document.getElementById("importPrice").value == "" ){
            var errorMessage = document.getElementById("importPriceErr");
            errorMessage.innerText = "Vui lòng điền giá nhập ";
            return false;
        }else if(document.getElementById("importPrice").value <=0){
            var errorMessage = document.getElementById("importPriceErr");
            errorMessage.innerText = "Giá nhập phải lớn hơn 0 ";
            return false;
        }
        if(document.getElementById("price").value == ""){
            var errorMessage = document.getElementById("priceErr");
            errorMessage.innerText = "Vui lòng điền giá bán ";
            return false;
        }else if(document.getElementById("price").value <=0 ||
            (+document.getElementById("price").value) <= (+document.getElementById("importPrice").value)){
            var errorMessage = document.getElementById("priceErr");
            errorMessage.innerText = "Giá bán phải lớn hơn giá nhập ";
            return false;
        }
        if(document.getElementById("brandId").value == -1 ){
            var errorMessage = document.getElementById("brandErr");
            errorMessage.innerText = "Vui lòng chọn thương hiệu ";
            return false;
        }
        if(document.getElementById("categoryId").value == -1 ){
            var errorMessage = document.getElementById("categoryErr");
            errorMessage.innerText = "Vui lòng chọn loại sản phẩm";
            return false;
        }
        if(document.getElementById("description").value == ""){
            var errorMessage = document.getElementById("descriptionErr");
            errorMessage.innerText = "Vui lòng điền mô tả";
            return false;
        }
        if (!confirm("Bạn có muốn thao tác không?")) {
            return false;
        }
        document.getElementById("frmAction").submit();
    }

    // var handleOnChange = function (e) {
    //     product = {...product, [e.name]: e.value};
    // }

    var preAction = function (id, name, brandId, categoryId, price, priceImport, description, files,status) {
        console.log("name: " + name);
        console.log("brandId: " + brandId);
        console.log("categoryId: " + categoryId);

        document.getElementById("id").value = id;
        document.getElementById("nameProduct").value = name;
        document.getElementById("description").value = description;
        document.getElementById("brandId").value = brandId;
        document.getElementById("categoryId").value = categoryId;
        document.getElementById("price").value = price;
        document.getElementById("importPrice").value = priceImport;
        document.getElementById("filesUpload").value = files;
    }

    var handleOnChangeFile = function (e) {
        var divImages = document.getElementById("imagesUpload");
        divImages.innerHTML = "";
        const files = e.files;
        var content = "";
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const url = URL.createObjectURL(file);
            content = content + '<img width="80px" height="100px" style="margin-right: 12px" src="' + url + '">';
        }
        divImages.innerHTML = content;
    }
</script>