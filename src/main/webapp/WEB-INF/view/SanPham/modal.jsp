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
                                <label for="name" class="form-label">Tên sản phẩm</label>
                                <input class="form-control"  name="name" id="name">
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
                            </div>
                            <div class="mb-3">
                                <label for="importPrice" class="form-label">Giá nhập</label>
                                <input class="form-control" type="number" name="importPrice"
                                       id="importPrice">
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Giá bán</label>
                                <input class="form-control" type="number" onchange="handleOnChange(this)" name="price" id="price">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="brand" class="form-label">Thương hiệu</label>
                                <select class="form-select" name="brand.id" id="brand"
                                        aria-label="Default select example">
                                    <option value="-1">--Thương hiệu--</option>
                                    <c:forEach items="${brands}" var="x">
                                        <option value="${x.id}">${x.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="category" class="form-label">Loại sản phẩm</label>
                                <select class="form-select" name="category.id" id="category"
                                        onchange="handleOnChange(this)"
                                        aria-label="Default select example">
                                    <option value="-1">--Loại sản phẩm--</option>
                                    <c:forEach items="${categories}" var="x">
                                        <option value="${x.id}">${x.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Mô tả sản phẩm</label>
                                <textarea class="form-control" name="description" id="description"></textarea>
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
        if (!confirm("Bạn có muốn thao tác không?")) {
            return false;
        }
        document.getElementById("frmAction").submit();
    }

    // var handleOnChange = function (e) {
    //     product = {...product, [e.name]: e.value};
    // }

    var preAction = function (id, name, brandId, categoryId, price, priceImport, description, files) {
        document.getElementById("id").value = id;
        document.getElementById("name").value = name;
        document.getElementById("description").value = description;
        document.getElementById("brand").value = brandId;
        document.getElementById("category").value = categoryId;
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