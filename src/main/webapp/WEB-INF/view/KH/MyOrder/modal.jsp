<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
     style="z-index: 999999;">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <form ng-submit="onSubmit()">
                <div class="modal-header">
                    <h4>Thông tin đơn hàng</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <fieldset disabled>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6 col-xs-12">
                                <form >
                                    <div class="mb-3">
                                        <label for="name" class="form-label">Họ và tên</label>
                                        <input type="text" class="form-control" id="name" >
                                    </div>
                                    <div class="mb-3">
                                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                                        <input type="text" class="form-control" id="phoneNumber">
                                    </div>
                                    <div class="mb-3">
                                        <label for="address" class="form-label">Địa chỉ</label>
                                        <input type="text" class="form-control" id="address">
                                    </div>
                                    <div class="mb-3">
                                        <label for="note" class="form-label">Ghi chú</label>
                                        <input type="text" class="form-control" id="note" >
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 col-xs-12" style="background-color: #f9f9f9;">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">
                                            Sản phẩm
                                        </th>
                                        <th scope="col">Số lượng</th>
                                        <th scope="col">Giá</th>
                                        <th scope="col">Tổng tiền</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="orderDetail in order.orderDetails">
                                        <td scope="row">
                                            <a href="#!/details/{{orderDetail.productDetail.product.id}}"
                                               data-bs-dismiss="modal">
                                                <div class="row">
                                                    <img ng-src="images/{{orderDetail.productDetail.product.images[0]}}"
                                                         style="width: 130px;" class="d-block" alt="">
                                                    <div class="col-6">
                                                        <p>{{orderDetail.productDetail.name}}</p>
                                                        <p>Màu sắc: {{orderDetail.productDetail.color.name}}</p>
                                                        <p>Kích cỡ: {{orderDetail.productDetail.size.name}}</p>
                                                    </div>
                                                </div>
                                            </a>
                                        </td>
                                        <td>
                                            <p>{{orderDetail.quantity}}</p>
                                        </td>
                                        <td>
                                            <p>{{orderDetail.price}}</p>
                                        </td>
                                        <td>{{orderDetail.price * orderDetail.quantity}}</td>
                                    </tr>
                                    </tbody>

                                </table>
                                <div class="row">
                                    <div class="col-12">
                                        <div style="width: 30%; float: right;">
                                            <p>
                                                Tổng sản phẩm: {{order.totalProduct}} <br>
                                                Tạm tính: {{order.toltalMoney}}
                                            </p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </fieldset>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">Đóng</button>
                </div>
            </form>
        </div>
    </div>
</div>