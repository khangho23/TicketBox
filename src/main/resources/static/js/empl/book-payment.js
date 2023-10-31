import { formatCurrency } from "../../common/NumberUtil.js";
import { fetch } from "../axios.js";

let seat = [];
let movie = {};
let showtime = {};
let price = {};
let topping = [];
let priceTopping = {};
$(document).ready(async function () {
    seat = JSON.parse(localStorage.getItem(`seat_${showtimeid}_${emplId}`));
    topping = JSON.parse(localStorage.getItem(`topping_${showtimeid}_${emplId}`));
    movie = (await fetch.get("/movie/getByShowTime?showtimeid=" + showtimeid)).data;
    showtime = (await fetch.get("/showtime/" + showtimeid)).data;
    $.when(seat, movie, showtime, topping).done(() => {
        $("#loading").fadeToggle(1000, () => $(".main").fadeIn(500));
        Object.entries(movie).forEach(([key, value]) => {
            $(`#movie-${key}`)?.text(value);
        })
        $(`#seat-name`).text(seat.map(s => s.name).reduce((a, b) => a + ", " + b));
        let nameTopping = topping?.map(s => s.name).reduce((a, b) => a + ", " + b) || "";
        nameTopping == "" ? $(`#topping-name`).parent().hide() : $(`#topping-name`).text(nameTopping);
        $(`#st-starttime`).text(showtime.startTime);
        price = {
            seat: seat.map(obj => obj.total).reduce((a, b) => a + b),
            vat: 0,
            total: 0
        };
        priceTopping = {
            topping: topping?.map(obj => obj.total).reduce((a, b) => a + b) || null
        }
        priceTopping.topping == null ? $("#price-topping").parent().parent().attr("class","d-none") : $("#price-topping").text(priceTopping.topping);
        $("#price-seat").text(price.seat);
        price.vat = price.seat * 0.05;
        price.total = price.seat + price.vat;
        $("#vat").text(price.vat);
        $("#total").text(price.total + priceTopping.topping);
    })


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
        $("#temp").load("/js/empl/receipt.html", function (response, status, request) {
            Object.entries(movie).forEach(([key, value]) => {
                $(`#bill-movie-${key}`)?.text(value);
            })
            $(`#bill-seat-name`).text(seat.map(s => s.name).reduce((a, b) => a + ", " + b));
            let nameTopping = topping?.map(s => s.name).reduce((a, b) => a + ", " + b) || "";
            nameTopping == "" ? $(`#bill-topping-name`).parent().attr("class","d-none") : $(`#bill-topping-name`).text(nameTopping);
            $(`#bill-st-starttime`).text(showtime.startTime);
            priceTopping.topping == null ? $("#bill-topping-price").parent().attr("class","d-none") : $("#bill-topping-price").text(priceTopping.topping);
            $("#bill-seat-price").text(price.seat);
            $("#bill-vat").text(price.vat);
            $("#bill-total").text(price.total + priceTopping.topping);
            $("#bill-address").text(showtime.branchAddress);
            $("#bill-room").text(showtime.roomName);
            $.each($("#temp [data=money]"), (index, element) => {
                $(element).text(formatCurrency($(element).text()));
            });
            window.print();
        });

    });
});




