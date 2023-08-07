// doing change quantity
$(document).ready(function () {
    let id = $('input[name="id"]');
    let quantity = $('input[name="quantity"]');
    quantity.on("change", function () {
        let quantityValue = $(this).val()
        let idValue = $(this).closest('tr').find('input[name="id"]').val();
        let print = $(this).closest('tr').find('#totalPriceItem');
        console.log(typeof quantityValue);
        $.ajax({
            url: '/shop/update-cart1',
            type: "GET",
            data: {quantityValue: quantityValue, idValue: idValue},
            success: function (data) {
                console.log(data);
                print.html("")
                print.html(data + ' đ')
            }, error: function () {
                console.log("failed");
            }
        });
    });
});
// done changed quantity
$(document).ready(function () {
    let quantity = $('input[name="quantity"]');
    let subTotal = document.getElementById("subTotal")
    let grandTotal =document.getElementById("grandTotal")
    quantity.on("blur", function () {
        $.ajax({
            url: '/shop/update-cart2',
            type: "GET",
            success: function (data) {
                subTotal.innerHTML = data+'đ';
                grandTotal.innerHTML=data+"đ"
            }, error: function () {
                console.log("failed");
            }
        });
    });
});
function addToCart() {
    var selectElement = document.getElementById("acc")
    let giaTri = selectElement.options[0].text;
    if (giaTri === "Tài khoản") {
        alert("Vui lòng đăng nhập để sử dụng chức năng này!")
    } else alert("Thêm vào giỏ hàng thành công!")
}
