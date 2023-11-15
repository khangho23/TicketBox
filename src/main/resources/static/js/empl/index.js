import { fetch } from "../axios.js";
const URL_IMAGE = "https://zuhot-cinema-images.s3.amazonaws.com/poster-movie/";
let showtime = [];

const getCurrentDay = () => {
	return new Date().toLocaleDateString("vi-VI");
}
const getCurrentTime = () => {
	return new Date().getHours().toLocaleString("vi-VI");
}
const convertData = (result) => {
	return result.reduce((total, valueCurrent, index, arr) => {
		if (total.starttime == valueCurrent.starttime) {
			total.movies.push(valueCurrent.movie);
		} else if (total.starttime != valueCurrent.starttime) {
			let obj = {
				id: valueCurrent.id,
				starttime: valueCurrent.starttime,
				movieid: valueCurrent.movieid,
				ticketNumber: valueCurrent.ticketNumber,
				movies: [
					valueCurrent.movie
				]
			};
			return [...total, obj];
		}
		return [...valueCurrent];
	}, []);
}

const fillElement = (staffId) => {
	const groupedShowtime = showtime.reduce((acc, s) => {
		const key = s.starttime;
		if (!acc[key]) {
			acc[key] = [];
		}
		acc[key].push(s);
		return acc;
	}, {});

	Object.values(groupedShowtime).forEach((group) => {
		$(".show-time").append(`
            <section class="showtime-item mb-4">
            <p class="showtime-title text-danger fs-3">${group[0].starttime}</p>
                <div class="row row-showtime">
                    ${group.map((s) => `
                        <div class="card col-2 m-2 p-0">
                            <img class="card-img-top w-100" src="${URL_IMAGE}${s.movieid}.png" alt="Card image cap" style="height:305px">
                            <div class="card-body">
                                <p onclick="window.location.href = '/empl/book-seat_${s.id}_${staffId}'" style="font-size:small;cursor:pointer;">
                                ${s.movies}</p>
                            </div>
                            <span class='badge bg-${s.ticketNumber < 96 ? 'success' : 'danger'} rounded-pill m-1'>${s.ticketNumber}/96</span>
                        </div>
                    `).join("")}
                </div>
            </section>
        `);
	});

}
const cookieStaff = document.cookie;
function getCookie(name) {
	return cookieStaff.split(`${name}=`).pop();
}

$(document).ready(async function () {
	const branchId = getCookie("branchId").split("_").shift();
	const staffId = getCookie("branchId").split("_").pop();
	console.log(branchId, staffId);
	$(".title").text(`${$(".title").text()} ${getCurrentDay()}`);
	const { data: result } = await fetch.get("/showtime/cd", { params: { branchid: branchId } });
	showtime = convertData(result);
	$.when(showtime).done(() => {
		$(".loading").fadeToggle(500, () => {
			$(".show-time").fadeIn(500);
		})
	})
	fillElement(staffId);
});
