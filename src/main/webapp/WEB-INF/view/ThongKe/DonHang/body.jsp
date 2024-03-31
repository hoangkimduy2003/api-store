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

    .second-text {
        color: var(--second-text-color);
    }

    .primary-bg {
        background-color: var(--main-bg-color);
    }

    .secondary-bg {
        background-color: var(--second-bg-color);
    }

    .rounded-full {
        border-radius: 100%;
    }

    #wrapper {
        overflow-x: hidden;
        background-image: linear-gradient(
                to right,
                #baf3d7,
                #c2f5de,
                #cbf7e4,
                #d4f8ea,
                #ddfaef
        );
    }

    #sidebar-wrapper {
        min-height: 100vh;
        margin-left: -15rem;
        -webkit-transition: margin 0.25s ease-out;
        -moz-transition: margin 0.25s ease-out;
        -o-transition: margin 0.25s ease-out;
        transition: margin 0.25s ease-out;
    }

    #sidebar-wrapper .sidebar-heading {
        padding: 0.875rem 1.25rem;
        font-size: 1.2rem;
    }

    #sidebar-wrapper .list-group {
        width: 15rem;
    }

    #page-content-wrapper {
        min-width: 100vw;
    }

    #wrapper.toggled #sidebar-wrapper {
        margin-left: 0;
    }

    #menu-toggle {
        cursor: pointer;
    }

    .list-group-item {
        border: none;
        padding: 20px 30px;
    }

    .list-group-item.active {
        background-color: transparent;
        color: var(--main-text-color);
        font-weight: bold;
        border: none;
    }

    @media (min-width: 768px) {
        #sidebar-wrapper {
            margin-left: 0;
        }

        #page-content-wrapper1 {
            min-width: 0;
            width: 100%;
        }

        #page-content-wrapper {
            min-width: 0;
            width: 100%;
        }

        #wrapper.toggled #sidebar-wrapper {
            margin-left: -15rem;
        }
    }
</style>
<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <a href="/thong-ke" class="nav-link " id="nav-home-tab" aria-selected="false">Tổng</a>
        <a href="/thong-ke/doanh-thu" class="nav-link " id="nav-profile-tab" aria-selected="true">Doanh thu</a>
        <a href="/thong-ke/don-hang" class="nav-link active" id="nav-contact-tab" aria-selected="false">Hoá đơn</a>
        <a href="/thong-ke/san-pham" class="nav-link" id="nav-prodcut-tab" aria-selected="false">Sản phẩm</a>
    </div>
</nav>
<div class="container">
    <div class="container-fluid px-4">
        <div class="row g-3 my-2">
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBill}"/></h3>
                        <p class="fs-6" style="margin: 0px">Tất cả</p>
                    </div>
                    <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillAtStore}"/></h3>
                        <p class="fs-6" style="margin: 0px">Tổng hoá đơn tại quầy</p>
                    </div>
                    <i class="fas fa-gift fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillAtStoreCho}"/></h3>
                        <p class="fs-6" style="margin: 0px">Hoá đơn chờ tại quầy</p>
                    </div>
                    <i
                            class="fas fa-hand-holding-usd fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-3">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillAtStoreDaHT}"/></h3>
                        <p class="fs-6" style="margin: 0px">Hoá đơn đã hoàn thành tại quầy</p>
                    </div>
                    <i class="fas fa-truck fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnline}"/></h3>
                        <p class="fs-6" style="margin: 0px">Tổng đơn hàng online</p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnlineChoXacNhan}"/></h3>
                        <p class="fs-6" style="margin: 0px">Chờ xác nhận</p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnlineChoGiaoHang}"/></h3>
                        <p class="fs-6" style="margin: 0px">Chờ giao hàng</p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnlineDangGiao}"/></h3>
                        <p class="fs-6" style="margin: 0px">Đang giao hàng</p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnlineDaGiao}"/></h3>
                        <p class="fs-6" style="margin: 0px">Đã giao hàng</p>
                    </div>
                    <i class="fas fa-chart-line fs-1 primary-text border rounded-full secondary-bg p-3"></i>
                </div>
            </div>
            <div class="col-md-2">
                <div class="p-2 bg-white shadow-sm d-flex justify-content-around align-items-center rounded">
                    <div>
                        <h3 class="fs-3"><fmt:formatNumber pattern="#,###" value="${rate.totalBillOnlineDaHuy}"/></h3>
                        <p class="fs-6" style="margin: 0px">Đã huỷ</p>
                    </div>
                    <i style="background-color: red !important;" class="fas fa-chart-line fs-1 primary-text border rounded-full p-3"></i>
                </div>
            </div>
        </div>
    </div>
    <form method="get" id="searchForm" action="/thong-ke/don-hang" onsubmit="handleSearch()">
        <div class="row">
            <div class="col-1">
                <div class="mb-3">
                    <label class="form-label">Lọc:</label>
                    <button class="btn btn-dark form-control" onclick="handleSearch()">Lọc</button>
                </div>
            </div>
            <div class="col-2">
                <div class="mb-3">
                    <label class="form-label">Ngày:</label>
                    <input class="form-control" type="date" value="${searchThongKeDTO.date}" name="date" id="dataInput">
                </div>
            </div>
            <div class="col-2">
                <div class="mb-3">
                    <label for="showType" class="form-label">Sơ đồ hiển thị theo: </label>
                    <select class="form-select" onchange="handleOnChangeShowType(this)" name="showType" id="showType" aria-label="Default select example">
                        <option value="-1" ${searchThongKeDTO.showType == -1 ? "selected" : ""}>--Tổng--</option>
                        <option value="1" ${searchThongKeDTO.showType == 1 ? "selected" : ""}>Ngày</option>
                        <option value="2" ${searchThongKeDTO.showType == 2 ? "selected" : ""} >Tháng</option>
                        <option value="3" ${searchThongKeDTO.showType == 3 ? "selected" : ""}>Năm</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-5">
            <canvas id="myChartOnline" width="360px" height="240px"></canvas>
        </div>
        <div class="col-2">
            <div class="mt-5">
                <p class="fs-6">Biểu đồ đơn hàng phát sinh</p>
                <i class="bi bi-arrow-left-short"></i><span>Biểu đồ online</span>
                <br/>
                <i class="bi bi-arrow-right-short"></i><span>Biểu đồ tại quầy</span>
            </div>
        </div>
        <div class="col-5">
            <canvas id="myChartStore" width="360px" height="240px"></canvas>
        </div>
    </div>
</div>


<script>
    function handleOnChangeShowType(e) {
        var value = e.value;
        if(value == "-1" || value == -1){
            document.getElementById("dataInput").value = "";
        }
    }
    var handleSearch = function () {
        document.getElementById("searchForm").submit();
    }

    var ctx1 = document.getElementById('myChartOnline').getContext('2d');
    var myChart = new Chart(ctx1, {
        type: 'line',
        data: {
            labels: '${lableOnline}'.slice(1, -1).split(","),
            datasets: [{
                label: 'Số đơn hàng phát sinh',
                data: ${valueOnline},
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    var ctx = document.getElementById('myChartStore').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: '${lableStore}'.slice(1, -1).split(","),
            datasets: [{
                label: 'Số đơn hàng phát sinh',
                data: ${valueStore},
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    // axios.get("/thong-ke/getSumFinal")
    //     .then(function(response) {
    //         console.log(response);
    //         response.data.forEach(function(x) {
    //             dataLabels.push(x.date);
    //             dataValues.push(x.totalMoney);
    //         });
    //
    //         var ctx = document.getElementById('myChart').getContext('2d');
    //         var myChart = new Chart(ctx, {
    //             type: 'line',
    //             data: {
    //                 labels: dataLabels,
    //                 datasets: [{
    //                     label: 'Doanh thu',
    //                     data: dataValues,
    //                     backgroundColor: [
    //                         'rgba(255, 99, 132, 0.2)',
    //                         'rgba(54, 162, 235, 0.2)',
    //                         'rgba(255, 206, 86, 0.2)',
    //                         'rgba(75, 192, 192, 0.2)',
    //                         'rgba(153, 102, 255, 0.2)',
    //                         'rgba(255, 159, 64, 0.2)'
    //                     ],
    //                     borderColor: [
    //                         'rgba(255, 99, 132, 1)',
    //                         'rgba(54, 162, 235, 1)',
    //                         'rgba(255, 206, 86, 1)',
    //                         'rgba(75, 192, 192, 1)',
    //                         'rgba(153, 102, 255, 1)',
    //                         'rgba(255, 159, 64, 1)'
    //                     ],
    //                     borderWidth: 1
    //                 }]
    //             },
    //             options: {
    //                 scales: {
    //                     yAxes: [{
    //                         ticks: {
    //                             beginAtZero: true
    //                         }
    //                     }]
    //                 }
    //             }
    //         });
    //     })
    //     .catch(function(error) {
    //         console.error('Error fetching data:', error);
    //     });
</script>
