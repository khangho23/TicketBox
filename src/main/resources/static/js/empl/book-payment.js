import { formatCurrency } from "../../common/NumberUtil.js";

let seat = JSON.parse(localStorage.getItem(`seat_${showtimeid}_${emplId}`));
$(document).ready(function () {
    $("#price-seat").text(formatCurrency(seat[0].total));
});



$("input[type=radio][name=options]").change(function(){
    if(this.value ==1){
        $("#cash").attr("class","d-flex justify-content-center align-items-center w-100")
    }else{
        $("#cash").attr("class","d-none w-100");
    }
})