import { fetch } from "../axios.js";
import ChartVarianceIndicators from "./chart/chartVarianceIndicators.js";
import ChartBar from "./chart/chartBar.js";
import ChartBarHorizontal from "./chart/chartBarHorizontal.js";

var root = '';
var root2 = '';
var root3 = '';
var root4 = '';
var root5 = '';
var root6 = '';
var root7 = '';
let thongke = [];
let selectedOption = "";
let selectedYear = "";
let selectedDateEnd = moment().format("YYYY-MM-DD");
let selectedDateStart = '2023-01-01';

const selectNameMovie = async (id) => {
    let listMovie = await fetch.get("dashboard/movieOfBranch", { params: { branchId: "cn2" } });
    $("#" + id).append(`
    <option value = "" selected>tất cả</ >
    ${listMovie.data.map(s => {
        return `<option value="${s.movieName}" >${s.movieName}</option>`
    })}`)
    $("#" + id).trigger("change");
}

const checkChart = (root) => {
    if (root != '') {
        root.dispose();
    }
}

const select = () => {
    $("#date").val(selectedDateEnd);
    selectNameMovie('select');
    const fetchData = async () => {
        thongke = await fetch.get("dashboard/statisticsTicketPriceByMovieForDay", { params: { movieName: selectedOption, date: selectedDateEnd, branchId: "cn2" } });
        checkChart(root4);
        checkChart(root5);
        root4 = am5.Root.new("priceCurrentDay");
        root5 = am5.Root.new("ticketCurrentDay");
        ChartBar.chartBar(thongke.data, root4, 'totalPrice', 'hour');
        ChartBar.chartBar(thongke.data, root5, 'totalTicket', 'hour');
    };

    $("#select").change(async function () {
        $(".date").text(selectedDateEnd)
        selectedOption = $(this).val();
        selectedDateEnd = $("#date").val();
        fetchData();

    });
    $('#date').change(function () {
        $(".date").text($(this).val())
        selectedDateEnd = $(this).val();
        selectedOption = $("#select").val();
        fetchData();
    });

}

const select2 = () => {
    $("#startDate").attr({
        "max": selectedDateEnd
    });
    $("#endDate").attr({
        "min": selectedDateStart
    });
    $("#endDate").val(selectedDateEnd);
    $("#startDate").val(selectedDateStart);
    selectNameMovie('select2');
    const fetchData = async () => {
        thongke = await fetch.get("dashboard/statisticsTicketPriceByMovieFromDate", { params: { movieName: selectedOption, startDate: selectedDateStart, endDate: selectedDateEnd, branchId: "cn2" } });
        checkChart(root);
        checkChart(root2);
        root = am5.Root.new("chartPriceFromDate");
        root2 = am5.Root.new("chartTicketFromDate");
        ChartBarHorizontal.chartBarHorizontal(thongke.data, root, "totalPrice", "date");
        ChartBarHorizontal.chartBarHorizontal(thongke.data, root2, "totalTicket", "date");
    };

    $("#select2").change(function () {
        selectedOption = $(this).val();
        selectedDateEnd = $("#endDate").val();
        fetchData();
    });

    $('#endDate').change(function () {
        selectedDateEnd = $(this).val();
        selectedOption = $("#select2").val();
        $("#startDate").attr({
            "max": $(this).val()
        });
        fetchData();
    });

    $('#startDate').change(function () {
        selectedDateStart = $(this).val();
        selectedOption = $("#select2").val();
        $("#endDate").attr({
            "min": $(this).val()
        });
        fetchData();
    });
}

const select3 = () => {
    $("#selectYear").append(`
    <option value="2022">2022</option>
    <option value="2023"selected>2023</option>
    `);
    const fetchData = async () => {
        thongke = await fetch.get("/dashboard/findTotalPriceTicket", { params: { year: selectedYear, branchName: "Bình Tân" } });
        checkChart(root3);
        checkChart(root7);
        root3 = am5.Root.new("chartYearTotalPrice");
        root7 = am5.Root.new("chartYearTotalTicket");
        ChartVarianceIndicators.ChartVarianceIndicators(thongke.data, root3, 'totalPrice', 'id');
        ChartBarHorizontal.chartBarHorizontal(thongke.data, root7, "totalTicket", "id");
    };
    const fetchDataShowTime = async () => {
        thongke = await fetch.get("/dashboard/statisticsTotalShowtimeOfYear", { params: { year: selectedYear, branchId: "cn2" } });
        checkChart(root6);
        root6 = am5.Root.new("chartYearTotalShowtime");
        ChartBarHorizontal.chartBarHorizontal(thongke.data, root6, "totalShowtime", "id");
    }

    $("#selectYear").change(function () {
        selectedYear = $(this).val();
        fetchData();
        fetchDataShowTime();
    });
}


const appendByDate = () => {
    $('.content').empty();
    $('.content').append(`
   <div class="row">
        <h3 class="text-center">Thống kế chi tiết theo ngày </h3>
        <br>
        <div class="col-12">
            <p class="date justify-content-center d-flex fw-bold text-primary"></p>
            <div class="justify-content-center d-flex"style="height:40px;">
                <p class="me-3" style="line-height: 40px;">Ngày bắt đầu</p>
                <input type="date" id="date">
                <p class="ms-5 me-3" style="line-height: 40px;">Chọn phim</p>
                <select name="movieName" id="select"></select>
            </div>
            <div class="row mt-3">
                <div class="col-6">
                    <h5>Doanh thu<h5>
                    <div id="priceCurrentDay" class=""></div>
                </div>
                <div class="col-6">
                <h5>Số lượng vé<h5>
                    <div id="ticketCurrentDay" class=""></div>
                </div>
            </div>
        </div>
    </div>`)
}
const appendFromDate = () => {
    $('.content').empty();
    $('.content').append(`
    <div class="row">
    <h3 class="text-center">Thống kế từ ngày </h3>
    <br>
    <div class="col-12">
        <div class="justify-content-center d-flex" style="height:40px;">
            <p class="me-3" style="line-height: 40px;" >Ngày bắt đầu</p>
            <input type="date" id="startDate">
            <p class="ms-5 me-3" style="line-height: 40px;">Ngày kết thúc</p>
            <input type="date" id="endDate">
            <p class="ms-5 me-3" style="line-height: 40px;">Chọn phim</p>
            <select name="movieName" id="select2"></select>
        </div>
        </select>
        <div class="row mt-5">
            <div class="col-6">
            <h5>Doanh thu<h5>
                <div id="chartPriceFromDate"></div>
            </div>
            <div class="col-6">
            <h5>Số lượng vé<h5>
                <div id="chartTicketFromDate"></div>
            </div>
        </div>
    </div>
</div>`)
}
const appendByYear = () => {
    $('.content').empty();
    $('.content').append(`
    <div class="row">
        <h3 class="text-center">Thống kế trong năm </h3>
        <br>
        <div class="col-12">
            <div class="justify-content-center d-flex"style="height:40px;">
                <p class="me-3" style="line-height: 40px;" >Chọn năm</p>
                <select name="year" id="selectYear"></select>
            </div>
            </select>
            <div class="row">
                <div class="col-12">
                    <h5 class="">Tổng doanh thu</h5>
                    <div id="chartYearTotalPrice"></div>
                </div>
                <div class="col-6">
                    <h5 class="">Tổng xuất chiếu</h5>
                    <div id="chartYearTotalShowtime"></div>
                </div>
                <div class="col-6">
                    <h5 class="">Tổng Vé</h5>
                    <div id="chartYearTotalTicket"></div>
                </div>
            </div>
        </div>
    </div>`)
}

const byYear = () => {
    $('#byYear').click(function () {
        appendByYear();
        select3();
        $("#selectYear").trigger("change");
    });
}
const fromDate = () => {
    $('#fromDate').click(function () {
        appendFromDate();
        select2();
    });
}
const byDate = () => {
    $('#byDate').click(function () {
        appendByDate();
        select();
    });
}

$(document).ready(async function () {
    if ($(".content").text() === '') {
        $('#byDate').addClass('selected');
        appendByDate();
        select();
    }
    $('li').click(function () {
        $('li').removeClass('selected');
        $(this).addClass('selected');
    });
    byDate();
    fromDate();
    byYear();
});