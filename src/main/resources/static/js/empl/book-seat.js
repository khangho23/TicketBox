import { fetch } from "../axios.js";

let seat = [];
let showtime = {};

let seatToChoose = [];

const fillSeat = (row, seats) => {
    let result = "";
    seats.forEach((s, i) => {
        let state_item = s.booked ? 'btn-light' : 'btn-light';
        let ready_item = state_item == 'btn-light' ? 'btn-outline-light' : 'btn-light';
        let state_bg = s.booked ? 'background-image: url(/images/icon/ItemHasBooked.svg)' : 'background-image: url(/images/icon/ItemDefault.svg)';
        let ready_bg = state_bg == 'background-image: url(/images/icon/ItemDefault.svg)' ? 'background-image: url(/images/icon/ItemChoose.svg)' : 'background-image: url(/images/icon/ItemDefault.svg)';
        result += `<button class="col-1 btn ${state_item}" state-button="${ready_item}" id="seat-${row}-${i}" ${s.booked ? 'disabled' : ''}  style="height: 50px; background-position: center;background-repeat: no-repeat;${state_bg};" state-bg="${ready_bg}">${s.booked ? '' : s.name}</button>`;
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
            <td class="text-center">${s.total.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</td>
        </tr>
    `);
    })
    $("#price-total").text(total_price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }));
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
    $(".seat").hide();
    const { data: result } = await fetch.get("/seat/getSeatHasCheckTicket?id=" + showtimeid);
    const { data: result2 } = await fetch.get("/showtime/" + showtimeid);
    $.when(showtime, seat).done(() => {
        $(".loading").fadeToggle(500, () => {
            $(".seat").fadeIn(500);
        })
    })
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
        let currentStyle = $(this).attr('style').split(";")[3];
        let setStyle = $(this).attr('state-bg');
        $(this).attr("style", "height: 50px; background-position: center;background-repeat: no-repeat;" + setStyle);
        $(this).attr("state-bg", currentStyle);
        if (setClass == "btn-outline-light") {
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
    // $().removeClass("btn*");
}