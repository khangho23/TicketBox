import { fetch } from "../axios.js";
const toppingToChoose = [];
let toppingName = "";
var oldValue = 0;
let toppingPrice = "";
let toppingOfBranchId = "";
var products = {};
var toppingId;
const fillTopping = (topping) => {
    let result = "";
    topping.forEach((topping) => {
        result +=
            `<div class="col-lg-4 my-2">
                <div class="card shadow-0 border rounded-3 ">
                    <div class="card-body p-0">
                        <div class="row" style="height:110px">
                            <div class="col-lg-5 mb-lg-0 mt-1">
                                <div class="bg-image hover-zoom ripple rounded ripple-surface">
                                    <img src="https://zuhot-cinema-images.s3.amazonaws.com/topping/${topping.logo}"
                                        class="w-100" style="height:90px" />
                                    <a href="#!">
                                        <div class="hover-overlay">
                                            <div class="mask" style="background-color: rgba(253, 253, 253, 0.15);">
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-7">
                                <p class="my-1 fs-6 fw-bolder">${topping.name}</p>
                                <div class="mb-2 text-muted small">
                                    <p class="my-1 fst-italic" style="font-size:12px">${topping.price.toLocaleString('it-IT', {
                style:
                    'currency', currency: 'VND'
            })}</p>
                                </div>
                                <div class="input-group quantity" style="width:90px">
                                    <div class="input-group-btn">
                                        <button class="btn btn-dark btn-minus rounded-0 d-flex justify-content-center" style="width:12px; height:25px"
                                            id="topping_${topping.id}_${topping.name}_${topping.price}">
                                            <i class="fa fa-minus text-danger" style="font-size:12px"></i>
                                        </button>
                                    </div>
                                    <input type="text" class="form-control bg-light text-center" value="0" min="0" max="${topping.quantity}" style="height:25.5px"
                                        readonly>
                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-dark btn-plus_${topping.id} rounded-0 d-flex justify-content-center" style="width:12px; height:25px"
                                                id="topping_${topping.id}_${topping.name}_${topping.price}_${topping.toppingofbranchid}_${topping.quantity}">
                                                <i class="fa fa-plus text-danger" style="font-size:12px"></i>
                                            </button>
                                        </div>
                                </div>
                                <p class="message_${topping.id} text-danger mt-1 " style="font-size:14px"></p>
                                <p class="test"></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
    });
    return result;
}

const fillTablePrice = () => {
    $("#table-price").empty();
    const total_price = toppingToChoose.map(s => s.total).reduce((a, b) => a + b, 0);
    toppingToChoose.forEach((s, i) => {
        $("#table-price").append(`
        <tr class="item_${s.id}">
            <td scope="row" class="text-center">${i + 1}</td>
            <td class="text-center">${s.name}</td>
            <td class="text-center">${s.quantity}</td>
            <td class="text-center">${s.total.toLocaleString('it-IT', { style: 'currency', currency: 'VND' })}</td>
        </tr>
    `);
    })
    $("#price-total").text(total_price.toLocaleString('it-IT', { style: 'currency', currency: 'VND' }));
}

const checkTopping = (newVal) => {
    let found = false;
    $.each(toppingToChoose, function (index, item) {
        if (item.name === toppingName) {
            item.quantity = newVal;
            item.total = newVal * toppingPrice;
            found = true;
            return false;
        }
    });
    if (!found) {
        toppingToChoose.push({
            id: toppingId,
            toppingOfBranchId: toppingOfBranchId,
            name: toppingName,
            total: newVal * toppingPrice,
            quantity: newVal
        });
    }
}
const quantity = () => {
    $('.quantity button').on('click', function () {
        var button = $(this);
        oldValue = button.parent().parent().find('input').val();
        toppingName = button.attr('id').split('_')[2]
        toppingPrice = button.attr('id').split('_')[3]
        toppingOfBranchId = button.attr("id").split('_')[4]
        var toppingQuantity = button.attr('id').split('_')[5]
        toppingId = button.attr('id').split('_')[1]
        if (!products[toppingId]) {
            products[toppingId] = {
                newVal: 0
            };
        }
        var product = products[toppingId];
        if (button.hasClass('btn-plus_' + toppingId)) {
            if (product.newVal < toppingQuantity) {
                product.newVal = parseFloat(oldValue) + 1;
                checkTopping(product.newVal);
            } else {
                $('.message_' + toppingId).text("Số lượng giới hạn");
            }
        } else {
            $('.message_' + toppingId).text("");
            if (oldValue > 1) {
                product.newVal = parseFloat(oldValue) - 1;
                checkTopping(product.newVal);
            } else {
                product.newVal = 0;
                for (var i = 0; i < toppingToChoose.length; i++) {
                    if (toppingToChoose[i].name === toppingName) {
                        toppingToChoose.splice(i, 1);
                        break;
                    }
                }
            }
        }
        $('#table-price').fadeOut(0)
        $('#price-total').fadeOut(0)
        $.when(fillTablePrice())
            .done(function (result1) {
                $('.spinner-2').css('display', 'block');
                $('.spinner-2').fadeToggle(1000, function () {
                    $('#table-price').fadeIn(1000)
                    $('#price-total').fadeIn(1000)
                })

            });
        localStorage.setItem(`topping_${showtimeid}_${emplId}`, JSON.stringify(toppingToChoose));
        button.parent().parent().find('input').val(product.newVal);
    });
}

const fillContainerTopping = (result) => {
    $(".topping-container").append(`
        <section style="background-color: #eee;">
            <div class="row">
                ${fillTopping(result)}
            </div>
        </section>`);
}

$(document).ready(async function () {
    const { data: result } = await fetch.get("/topping/toppingofbranch", { params: { branchid: "cn1" } });
    fillContainerTopping(result);
    quantity();
    $.when(result)
        .done(function (result1) {
            $('.spinner-3').fadeToggle(1000, function () {
                $('.body').fadeIn(1000)
                $('.body').css('display', 'flex');
            })

        })
        .fail(function () {
        });
});