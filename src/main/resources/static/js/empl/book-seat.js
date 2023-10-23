import { fetch } from "../axios.js";
import { formatCurrency } from "../../common/NumberUtil.js";


let seat = [];
let showtime = {};

let seatToChoose = [];

const fillSeat = (row, seats) => {
    let result = "";
    seats.forEach((s, i) => {
        let state_item = s.booked ? 'btn-danger' : 'btn-primary';
        let ready_item = state_item == 'btn-primary' ? 'btn-success' : 'btn-primary';
        result += `<button class="col-1 btn ${state_item}" state-button="${ready_item}" id="seat-${row}-${i}" ${s.booked ? 'disabled' : ''}>${s.name}</button>`;
    });
    return result;
}

const fillMovie = () => {
    $(".movie-name").text(showtime.movieName);
    $(".movie-information").text(`Giờ chiếu: ${showtime.startTime}`)
}

const fillTablePrice = () => {
    $("#table-price").empty();
    const total_price = seatToChoose.map(s => s.total).reduce((a, b) => a + b, 0);
    seatToChoose.forEach((s, i) => {
        $("#table-price").append(`
        <tr class="">
            <td scope="row" class="text-center">${i + 1}</td>
            <td class="text-center">${s.name}</td>
            <td class="text-center">${formatCurrency(s.total)}</td>
        </tr>
    `);
    })
    $("#price-total").text(formatCurrency(total_price));
}

const fillRow = () => {
    seat.forEach(s => {
        $(".seat-container").append(`
            <div class="row gap-1 mb-2">
                ${fillSeat(s.row, s.seats)}
            </div>
        `);
    })
}

$(document).ready(async function () {

    const { data: result } = await fetch.get("/seat/getSeatHasCheckTicket?id=" + showtimeid);
    const { data: result2 } = await fetch.get("/showtime/" + showtimeid);
    showtime = result2;
    seat = result;
    console.log(showtime);
    fillRow();
    fillMovie();
    chooseSeat();
});
function chooseSeat() {
    $("button").on("click", async function () {
        let seat_name = $(this).text();
        let currentClass = $(this).attr('class').split(" ")[2];
        let setClass = $(this).attr('state-button');
        $(this).attr("class", "col-1 btn " + setClass);
        $(this).attr("state-button", currentClass);
        if (setClass == "btn-success") {
            const { data: prices } = await fetch.get("/seat/getTotalPrice", { params: { showtimeid: showtimeid, name: seat_name } });
            seatToChoose.push({
                name: seat_name,
                total: prices.total
            });
        } else {
            seatToChoose = seatToChoose.filter(s => s.name != seat_name);
        }
        localStorage.setItem(`seat_${showtimeid}_${emplId}`, JSON.stringify(seatToChoose));
        fillTablePrice();
    });
}