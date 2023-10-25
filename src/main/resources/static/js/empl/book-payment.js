import { formatCurrency } from "../../common/NumberUtil.js";
import { fetch } from "../axios.js";

let seat = [];
let movie = {};
let showtime = {};
let price = {};

$(document).ready(async function () {
    seat = JSON.parse(localStorage.getItem(`seat_${showtimeid}_${emplId}`));
    movie = (await fetch.get("/movie/getByShowTime?showtimeid=" + showtimeid)).data;
    showtime = (await fetch.get("/showtime/" + showtimeid)).data;
    $.when(seat, movie, showtime).done(() => {
        $("#loading").fadeToggle(1000, () => $(".main").fadeIn(500));
        Object.entries(movie).forEach(([key, value]) => {
            $(`#movie-${key}`)?.text(value);
        })
        $(`#seat-name`).text(seat.map(s => s.name).reduce((a, b) => a + ", " + b));
        $(`#st-starttime`).text(showtime.startTime);
    })
    price = {
        seat: seat.map(obj => obj.total).reduce((a, b) => a + b),
        vat: 0,
        total: 0
    };
    $("#price-seat").text(price.seat);
    price.vat = price.seat * 0.05;
    price.total = price.seat + price.vat;
    $("#vat").text(price.vat);
    $("#total").text(price.total);

    $("input[type=radio][name=options]").change(function () {
        let payment = {
            cash: ["d-flex justify-content-center align-items-center w-100", "d-flex justify-content-between mt-2"],
            visa: "",
            master: ""
        }
        Object.keys(payment).forEach(s => {
            $(`#${s}`)?.attr("class", "d-none w-100");
            $(`#pay-${s}`)?.attr("class", "d-none");
        })
        // payment by cash
        $(`#${this.value}`) && $(`#${this.value}`).attr("class", payment[this.value][0]);
        $(`#pay-${this.value}`) && $(`#pay-${this.value}`).attr("class", payment[this.value][1]);

    })
    $("#cash").children().blur(function () {
        $(".output-cash").text(formatCurrency(this.value));
        $(".refund-cash").text(formatCurrency(this.value - price.seat))
    });
    $.each($("[data=money]"), (index, element) => {
        $(element).text(formatCurrency($(element).text()));
    });
    $("#pay-button").click(() => {
        $("#loading-button").fadeIn(5000, $("#temp").load("/js/empl/receipt.html", function (response, status, request) {
            window.print();
        }));

    });
});




