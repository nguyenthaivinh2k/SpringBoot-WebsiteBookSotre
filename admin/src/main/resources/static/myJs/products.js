// multi delete - products.html
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
            data: {jsonList},
            success: function (data) {
                if (data === "Không có sản phẩm nào được chọn") {
                    alert(data)
                } else {
                    alert(data + " " + jsonList)
                    window.location.reload();

                }
            },
            error: function () {
                alert("failed")
                window.location.reload();
            }
        });
    });
});
$(document).ready(function() {
    var button = document.getElementById("firstDeleteButton");
    button.disabled= true;
    var checkboxes = document.getElementsByName('selectedItems');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].addEventListener('change', function() {
            var check = false;
            for (var j = 0; j < checkboxes.length; j++) {
                if (checkboxes[j].checked) {
                    check = true;
                    break;
                }
            }
            button.disabled = !check;
        });
    }
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

// format VND, add-product.html, update-product.html, products.html
$(document).ready(function () {
    var priceElements = document.getElementsByClassName('price');
    var formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});

    for (var i = 0; i < priceElements.length; i++) {
        var price = parseFloat(priceElements[i].textContent);
        var formattedPrice = formatter.format(price);
        priceElements[i].textContent = formattedPrice;
    }
});
// load file image show on html - add-product.html
    $(document).ready(function() {
    // Lắng nghe sự kiện khi chọn tệp ảnh
    $('#imageProduct').on('change', function(e) {
        var file = e.target.files[0];

        // Kiểm tra xem người dùng đã chọn ảnh chưa
        if (file) {
            // Tạo FileReader object để đọc tệp ảnh
            var reader = new FileReader();

            // Đặt sự kiện được gọi khi FileReader đọc tệp ảnh thành công
            reader.onload = function(e) {
                // Lấy URL dữ liệu của ảnh
                var imageSrc = e.target.result;

                // Hiển thị ảnh trong trình duyệt
                $('#image-container').html('<img src="' + imageSrc + '">');
            };

            // Đọc tệp ảnh như một URL dữ liệu
            reader.readAsDataURL(file);
        }
    });
});
// load file image show on html - update-product.html
$(document).ready(function() {
    // Lắng nghe sự kiện khi chọn tệp ảnh
    $('#imageProduct').on('change', function(e) {
        var file = e.target.files[0];

        // Kiểm tra xem người dùng đã chọn ảnh chưa
        if (file) {
            // Tạo FileReader object để đọc tệp ảnh
            var reader = new FileReader();

            // Đặt sự kiện được gọi khi FileReader đọc tệp ảnh thành công
            reader.onload = function(e) {
                // Lấy URL dữ liệu của ảnh
                var imageSrc = e.target.result;

                // Hiển thị ảnh trong trình duyệt
                $('#presentImageProduct').attr('src', imageSrc);
            };

            // Đọc tệp ảnh như một URL dữ liệu
            reader.readAsDataURL(file);
        }
    });
});
$(document).ready(function() {
    // Lắng nghe sự kiện khi chọn tệp ảnh
    $('#logo').on('change', function(e) {
        var file = e.target.files[0];

        // Kiểm tra xem người dùng đã chọn ảnh chưa
        if (file) {
            // Tạo FileReader object để đọc tệp ảnh
            var reader = new FileReader();

            // Đặt sự kiện được gọi khi FileReader đọc tệp ảnh thành công
            reader.onload = function(e) {
                // Lấy URL dữ liệu của ảnh
                var imageSrc = e.target.result;

                // Hiển thị ảnh trong trình duyệt
                $('#presentImageProduct2').attr('src', imageSrc);
            };

            // Đọc tệp ảnh như một URL dữ liệu
            reader.readAsDataURL(file);
        }
    });
});