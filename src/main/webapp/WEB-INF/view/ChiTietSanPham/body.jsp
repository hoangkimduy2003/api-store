<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
    }

    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
    }

    input:checked + .slider {
        background-color: #2196F3;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px #2196F3;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(26px);
        -ms-transform: translateX(26px);
        transform: translateX(26px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 34px;
    }

    .slider.round:before {
        border-radius: 50%;
    }
</style>
<div class="container m-2">
    <jsp:include page="modal.jsp"></jsp:include>
    <a class="btn btn-warning" href="/san-pham"><i class="bi bi-sign-turn-left-fill"></i></a>
    <button type="button" onclick="preAction(null,0,-1,-1)" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Thêm
    </button>
    <div style="min-height: 320px">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã chi tiết</th>
                <th scope="col">Màu sắc</th>
                <th scope="col">Kích cỡ</th>
                <th scope="col">Đơn giá</th>
                <th scope="col">Số lượng</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                <tr>
                    <td>${loopStatus.index + 1}</td>
                    <td>${x.productDetailCode}</td>
                    <td>${x.color.name}</td>
                    <td>${x.size.name}</td>
                    <td>${x.priceSale}</td>
                    <td>${x.quantity}</td>
                    <td>
                        <label class="switch">
                            <input id="active${x.id}" onclick="return confirm('Bạn có muốn thay đổi trạng thái không')" type="checkbox" ${x.status == 1 ? "checked" : ""} onchange="handleOnChangeToggleActiveProductDetail('${x.id}')">
                            <span class="slider round"></span>
                        </label>
                    </td>
                    <td>
                        <button type="button" class="btn btn-warning" onclick="preAction(${x.id},${x.quantity},${x.size.id},${x.color.id})"
                                data-bs-toggle="modal" data-bs-target="#exampleModal">
                            Sửa
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <ul class="pagination">
        <c:forEach begin="1" end="${list.totalPages}" varStatus="loop">
            <li class="page-item">
                <a class="page-link" href="/kich-co?page=${loop.begin + loop.count -2}">
                        ${loop.begin + loop.count -1}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<script>
    async function handleOnChangeToggleActiveProductDetail(id){
        var active = document.getElementById("active"+id).checked;
        await axios.get("/chi-tiet-sp/changeStatus/"+ id + "/" + (active ? 1 : 0)).then(res => {
            if(res.status == 200){
                console.log(res);
                toastr.success("Thay đổi trạng thái thành công");
            }
        }).catch(e => {
            console.log(e);
            document.getElementById("active"+id).checked = false;
            toastr.error("Thay đổi trạng thái thất bại!");
        })
    }
</script>