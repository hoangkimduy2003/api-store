<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" ng-controller="detailController" style="margin-bottom: 36px;">
    <jsp:include page="modal..jsp"></jsp:include>
    <div class="row" style="margin-bottom: 24px;">
        <span style="font-size: 11px;"><a href="#!/" style="text-decoration: none; color: black;">TRANG CHỦ</a> | ${product.name}</span>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-xs-12 ">
                <div id="slide_detail" class="carousel slide w-75" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="/san-pham/img?fileName=${product.images[0]}" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item" ng-repeat="img in product.images">
                            <img src="/san-pham/img?fileName=${product.images[1]}" class="d-block w-100" alt="...">
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#slide_detail"
                            data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#slide_detail"
                            data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div class="col-md-6 col-xs-12">
                <div class="row">
                    <h6>${product.name}</h6>
                </div>
                <div class="row">
                    <p style="color: gray;">
                        ${product.price} đ
                    </p>
                </div>
                <div class="row">
                    <h6>Màu sắc: </h6>
                    <div class="row">
                        <c:forEach items="${colors}" var="color">
                        <div class="col-2" ng-repeat="detail in uniqueColors">
                                <input type="radio" onchange="handleOnChangeColor(this)" name="color" value="${color}"> <span>${color}</span>
                            <br/>
                        </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="row" style="margin: 12px 0px;">
                    <h6 style="padding: 0;">Kích cỡ: </h6>
                    <div class="row" style="padding: 0;" id="divSize">
                    </div>
                </div>
                <div class="row" style="margin-top: 24px;">
                    <button type="button" class="btn btn-dark"
                            style="width: 60%" onclick="preAction()" data-bs-toggle="modal" data-bs-target="#addCart">Thêm vào giỏ hàng</button>
                </div>
                <div class="row" style="margin-top: 36px;">
                    <h6>Mô tả: </h6>
                    <hr style="width: 60%">
                    <div class="row" style="padding: 0;">
                        <div class="col-7">
                            ${product.description}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    var handleOnChangeSize =  function(e){
        var value = e.value;
        document.getElementById("productDetailId").value = "";
        // var value = +e.value.split("|")[1];

        // document.getElementById("quantityOld").value = ;
        // console.log(e.value.split("|")[1]);
        if(value != null){
            var arrValue = value.split("|");
            var quantity = arrValue[1];
            var productDetailId = arrValue[2];
            document.getElementById("quantityOld").value = quantity;
            document.getElementById("productDetailId").value = productDetailId;
            console.log(quantity);
        }
        // console.log(value);
    }

    async function handleOnChangeColor(e){
        // console.log(e.target.value)
        var colorName = e.value;
        var divSize = document.getElementById("divSize");
        document.getElementById("productDetailId").value = "";
        divSize.innerHTML = "";
        axios.get(`/CTSP/getByColor/`+ colorName+ `/`+${product.id})
            .then(function (response) {
                // Xử lý dữ liệu trả về nếu yêu cầu thành công
                var arrSize = response.data;

                arrSize.forEach((item) => {
                    var divElement = document.createElement('div');
                    divElement.className = 'col-2';

                    // Tạo phần tử input
                    var inputElement = document.createElement('input');
                    inputElement.setAttribute('type', 'radio');
                    inputElement.setAttribute('name', 'size');
                    inputElement.setAttribute('value', item.size+"|"+item.quantity+"|"+item.id);
                    inputElement.setAttribute('onchange', 'handleOnChangeSize(this)');

                    // Tạo phần tử span
                    var spanElement = document.createElement('span');
                    spanElement.textContent = item.size;

                    // Thêm input và span vào div
                    divElement.appendChild(inputElement);
                    divElement.appendChild(spanElement);

                    // Tìm divContent theo id và thêm phần tử div vào trong đó
                    document.getElementById('divSize').appendChild(divElement);
                })
            })
            .catch(function (error) {
                // Xử lý lỗi nếu yêu cầu thất bại
                console.error(error);
            });
    }
</script>