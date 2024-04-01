<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    :root {
        --main-bg-color: #009d63;
        --main-text-color: #009d63;
        --second-text-color: #bbbec5;
        --second-bg-color: #c1efde;
    }

    .primary-text {
        color: var(--main-text-color);
    }


    .secondary-bg {
        background-color: var(--second-bg-color);
    }

    .rounded-full {
        border-radius: 100%;
    }


    #wrapper.toggled {
        margin-left: 0;
    }


    .highcharts-figure,
    .highcharts-data-table table {
        min-width: 320px;
        max-width: 800px;
        margin: 1em auto;
    }

    .highcharts-data-table table {
        font-family: Verdana, sans-serif;
        border-collapse: collapse;
        border: 1px solid #ebebeb;
        margin: 10px auto;
        text-align: center;
        width: 100%;
        max-width: 500px;
    }

    .highcharts-data-table caption {
        padding: 1em 0;
        font-size: 1.2em;
        color: #555;
    }

    .highcharts-data-table th {
        font-weight: 600;
        padding: 0.5em;
    }

    .highcharts-data-table td,
    .highcharts-data-table th,
    .highcharts-data-table caption {
        padding: 0.5em;
    }

    .highcharts-data-table thead tr,
    .highcharts-data-table tr:nth-child(even) {
        background: #f8f8f8;
    }

    .highcharts-data-table tr:hover {
        background: #f1f7ff;
    }

    input[type="number"] {
        min-width: 50px;
    }
</style>
<%--bieu do--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
<!-- Thêm thư viện Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script src="https://www.gstatic.com/charts/loader.js"></script>
<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
                role="tab" aria-controls="nav-home" aria-selected="true">Tổng
        </button>
        <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button"
                role="tab" aria-controls="nav-profile" aria-selected="false">Hóa đơn
        </button>
        <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact" type="button"
                role="tab" aria-controls="nav-contact" aria-selected="false">Doanh thu
        </button>
        <button class="nav-link" id="nav-disabled-tab" data-bs-toggle="tab" data-bs-target="#nav-disabled" type="button"
                role="tab" aria-controls="nav-disabled" aria-selected="false">Sản phẩm
        </button>
    </div>
</nav>

<div class="tab-content" id="nav-tabContent">

    <%--    // tong--%>
    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab" tabindex="0">
        <div class="container-fluid px-4">
            <div class="row g-3 my-2">
                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${billCount}"/></h3>
                            <p class="fs-5">Tổng hóa đơn </p>
                        </div>
                        <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${revenue}"/> VNĐ</h3>
                            <p class="fs-5">Tổng doanh thu </p>
                        </div>
                        <i
                                class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${productsold}"/></h3>
                            <p class="fs-5">Tổng sản phẩm đã bán </p>
                        </div>
                        <i class="fas fa-truck fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${totalCustomers}"/></h3>
                            <p class="fs-5">Tổng số khách hàng </p>
                        </div>
                        <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="p-5 ">
            <div class="d-flex ">
                <div class="p-2 flex-fill border">
                    <p class="text-center fx-2 text-info">Đơn hàng mới </p>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Mã hóa đơn</th>
                            <th scope="col">Tổng tiền</th>
                            <th scope="col">Số điện thoại</th>
                            <th scope="col">Trạng thái</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list.data}" var="x" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index + 1}</td>
                                <td>${x.billCode}</td>
                                <td><fmt:formatNumber pattern="#,###" value="${x.totalMoney}"/></td>
                                <td>${x.phoneNumber}</td>
                                <td> ${x.status == 1 ? "Đang chờ" : (x.status == 2 ? "Đang xử lý" :
                                        x.status == 3 ? "Chờ lấy hàng" : ( x.status ==  4 ? "Đang giao" :
                                                (x.status == 5 ? (x.billType==1?"Đã hoàn thành" :"Đã giao") : (x.status == 6 ? "Trả hàng" : "Đã huỷ"))))}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="p-2 ms-5 flex-fill border">
                    <p class="text-center fx-2 text-info">Sản phẩm bán chạy </p>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Mã sản phẩm</th>
                            <th scope="col">Tên sản phẩm</th>
                            <th scope="col">Số lượng bán</th>
                            <th scope="col">Tổng tiền</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${bestseller.data}" var="x" varStatus="loopStatus">
                            <tr>
                                <td>${loopStatus.index + 1}</td>
                                <td>${x.id}</td>
                                <td>${x.name}</td>
                                <td><fmt:formatNumber pattern="#,###" value="${x.totalQuantitySold}"/></td>
                                <td><fmt:formatNumber pattern="#,###" value="${x.priceSale}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>


            </div>

        </div>
    </div>

    <%--    // Hoa don--%>
    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab" tabindex="0">
        <div class="container-fluid px-4">
            <div class="row g-3 my-2">
                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><c:out value="${billCount}"/></h3>
                            <p class="fs-5">Tổng hóa đơn </p>
                        </div>
                        <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><c:out value="${pendingVoice}"/></h3>
                            <p class="fs-5"> Đang giao </p>
                        </div>
                        <i
                                class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><c:out value="${billTransacted}"/></h3>
                            <p class="fs-5"> Đã hoàn thành </p>
                        </div>
                        <i class="fas fa-truck fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                        <div>
                            <h3 class="fs-2"><c:out value="${billCancelled}"/></h3>
                            <p class="fs-5"> Đã hủy </p>
                        </div>
                        <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                    </div>
                </div>
            </div>
            <%--       bieu do--%>

            <div style="width: 40%; margin: auto;" class="container">
                <canvas id="columnChart1"></canvas>
                <div><h3 class="text-center">Biểu đồ tỉ lệ hóa đơn </h3></div>
            </div>
            <script>
                // Gọi API để lấy dữ liệu (ví dụ: từ JSON)
                // Thay thế URL bằng API của bạn
                fetch('http://localhost:8081/thong-ke/bieu-do')
                    .then(response => response.json())
                    .then(data => {
                        // Xử lý dữ liệu từ API (ví dụ: lấy các giá trị cần thiết)
                        // const labels = data.map(item => item.orderDateFinal);
                        const values = data.map(item => item.status);

                        // Tạo biểu đồ cột
                        const ctx = document.getElementById('columnChart1').getContext('2d');
                        new Chart(ctx, {
                            type: 'pie',
                            data: {
                                labels: ['Đang giao/Đang chuẩn bị hàng ', "Đã hoàn thành ", "Đã hủy ",],
                                datasets: [{
                                        label: 'Tỉ lệ %',
                                        data: [<c:out value="${pendingVoice}"/> * 100 /<c:out value="${billCount}"/>,
                                        <c:out value="${billTransacted}"/> * 100 /<c:out value="${billCount}"/> ,
                                    <c:out value="${billCancelled}"/> * 100 /<c:out value="${billCount}"/>],
                            backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.6)', 'rgba(255, 100, 100, 1)'],
                            borderColor:'rgba(75, 192, 192, 1)',
                            borderWidth:1
                    }]
                    },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                }
                            ,
                            }
                        }
                    ,
                    })
                        ;
                    })
                    .catch(error => console.error('Lỗi khi gọi API:', error));
            </script>

        </div>
    </div>

    <%--    // Doanh thu--%>
    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab" tabindex="0">
        <div class="row g-3 my-2">
            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${revenue}"/> VNĐ</h3>
                        <p class="fs-5">Tổng doanh thu </p>
                    </div>
                    <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>

            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${revenueDay}"/> VNĐ</h3>
                        <p class="fs-5"> Trong ngày </p>
                    </div>
                    <i
                            class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>

            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${revenueMonth}"/> VNĐ</h3>
                        <p class="fs-5"> Trong tháng </p>
                    </div>
                    <i class="fas fa-truck fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>

            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${revenueYear}"/> VNĐ</h3>
                        <p class="fs-5"> Trong năm </p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
        </div>

        <div style="width: 60%; margin: auto;" class="container">
            <canvas id="columnChart"></canvas>
            <div><h3 class="text-center">Biểu đồ doanh thu theo từng tháng,năm</h3></div>
        </div>

        <script>
            // Gọi API để lấy dữ liệu (ví dụ: từ JSON)
            // Thay thế URL bằng API của bạn
            fetch('http://localhost:8081/thong-ke/bieu-do')
                .then(response => response.json())
                .then(data => {
                    // Xử lý dữ liệu từ API (ví dụ: lấy các giá trị cần thiết)
                    // const labels = data.map(item => item.getMonth());
                    // const values = data.map(item => item.getMoney());

                    // Tạo biểu đồ cột
                    const ctx = document.getElementById('columnChart').getContext('2d');
                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels:<c:out  value="${month}"/> ,
                            datasets: [{
                                label: 'Giá tiền (VND)',
                                data: <c:out value="${money}"/>,
                                backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.6)'],
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                },
                            }

                        },

                    });
                })
                .catch(error => console.error('Lỗi khi gọi API:', error));
        </script>
    </div>
    <%--    // San pham--%>
    <div class="tab-pane fade" id="nav-disabled" role="tabpanel" aria-labelledby="nav-disabled-tab" tabindex="0">
        <div class="row g-3 my-2">
            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${productsold+productExist}"/></h3>
                        <p class="fs-5">Tổng sản phẩm </p>
                    </div>
                    <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>

            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${productsold}"/></h3>
                        <p class="fs-5"> Đã bán </p>
                    </div>
                    <i
                            class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>

            <div class="col-md-3">
                <div class="p-3 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-2"><fmt:formatNumber pattern="#,###" value="${productExist}"/></h3>
                        <p class="fs-5"> Còn tồn </p>
                    </div>
                    <i class="fas fa-truck fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
        </div>

    </div>
</div>