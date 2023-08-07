// multi delete
$(document).ready(function () {
    $('#firstDeleteButton').click(function () {
        var selectedItems = []; // Danh sách các ID sản phẩm đã chọn
        var checkboxes = document.getElementsByName('selectedItems');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedItems.push(checkboxes[i].value);
            }
        }
        document.getElementById('selectedItemsCount').innerText = selectedItems.length;
    });
});
$(document).ready(function () {
    $('#deleteButton').click(function () {
        var selectedItems = []; // Danh sách các ID sản phẩm đã chọn
        var checkboxes = document.getElementsByName('selectedItems');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedItems.push(checkboxes[i].value);
            }
        }
        var jsonList = JSON.stringify(selectedItems);
        $.ajax({
            url: '/admin/delete', // Đường dẫn đến Controller xử lý yêu cầu
            type: 'GET',
            contentType: "application/json",
            success: function (data) {
                if (data === "Không có sản phẩm nào được chọn") {
                    alert(data)
                } else {
                    alert(data + " " + jsonList)
                    window.location.reload();

                }
            },
            data: {jsonList},
            error: function () {
                alert("failed")
                window.location.reload();
            }
        });
    });
});
$(document).ready(function () {
    $('#selectedAllItems').click(function (event) {
        if (this.checked) {
            $(':checkbox').each(function () {
                this.checked = true;
            });
        } else {
            $(':checkbox').each(function () {
                this.checked = false;
            });
        }
    });
});
// -> end multi delete

// format VND
$(document).ready(function () {
    var priceElements = document.getElementsByClassName('price');
    var formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});

    for (var i = 0; i < priceElements.length; i++) {
        var price = parseFloat(priceElements[i].textContent);
        var formattedPrice = formatter.format(price);
        priceElements[i].textContent = formattedPrice;
    }
});