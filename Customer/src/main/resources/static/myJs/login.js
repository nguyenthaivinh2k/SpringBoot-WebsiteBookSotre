$(document).ready(function () {
    // lấy object input có name là email
    let emailInput = $('input[name="username"]');
    let emailStatus = $("#email-status");

    // Kiểm tra email khi người dùng rời khỏi trường email hoặc thay đổi nội dung trường
    /*
     * blur : bỏ chọn 1 trường input hoặc click vào một phần tử khác trên trang web sự kiện này sẽ được gọi
     * keyup: khi người dùng nhấn phím trên bàn phím sự kiện này sẽ được gọi
     * có thể sử dụng kết hợp 2 cái này cùng lúc "blur keyup"
     */
    emailInput.on("blur", function () {
        let email = $(this).val(); // lấy giá trị email được nhập
        if (email !== "") {
            $.ajax({
                url: '/shop/check-email',
                type: "GET",
                data: {email: email},
                success: function (data) {
                    console.log(email);
                    if (data === "OK") {
                        emailStatus.html("");
                        emailStatus.removeClass();
                    } else {
                        emailStatus.addClass('alert alert-danger')
                        emailStatus.html('Email đã tồn tại');
                    }
                }, error: function () {
                    console.log("failed");
                }
            });
        } else {
            //  khi trường email rỗng, đoạn mã sẽ xóa bỏ thông báo lỗi trùng email nếu có và hiển thị trường email trống
            emailStatus.empty();
            emailStatus.removeClass();
        }
    });
});
$(document).ready(function () {
    let input = $('input[name="firstName"]');
    let status = $("#firstName-status");
    input.on("blur", function () {
        let value1 = $(this).val(); // lấy giá trị email được nhập
        let charCount = value1.length;
        if (value1 !== "") {
            $.ajax({
                url: '/shop/check-size',
                type: "GET",
                data: {size: charCount},
                success: function (data) {
                    console.log(value1);
                    if (data === "OK") {
                        status.html("");
                        status.removeClass();
                    } else {
                        status.addClass('alert alert-danger')
                        status.html('Độ dài ký tự phải từ 3 đến 10');
                    }
                }, error: function () {
                    console.log("failed");
                }
            });
        } else {
            //  khi trường email rỗng, đoạn mã sẽ xóa bỏ thông báo lỗi trùng email nếu có và hiển thị trường email trống
            status.empty();
            status.removeClass();
        }
    });
});
$(document).ready(function () {
    let input = $('input[name="lastName"]');
    let status = $("#lastName-status");
    input.on("blur", function () {
        let value1 = $(this).val(); // lấy giá trị email được nhập
        let charCount = value1.length;
        if (value1 !== "") {
            $.ajax({
                url: '/shop/check-size',
                type: "GET",
                data: {size: charCount},
                success: function (data) {
                    console.log(value1);
                    if (data === "OK") {
                        status.html("");
                        status.removeClass();
                    } else {
                        status.addClass('alert alert-danger')
                        status.html('Độ dài ký tự phải từ 3 đến 10');
                    }
                }, error: function () {
                    console.log("failed");
                }
            });
        } else {
            //  khi trường email rỗng, đoạn mã sẽ xóa bỏ thông báo lỗi trùng email nếu có và hiển thị trường email trống
            status.empty();
            status.removeClass();
        }
    });
});
$(document).ready(function () {
    let input = $('input[name="password"]');
    let status = $("#password-status");
    input.on("blur", function () {
        let value1 = $(this).val(); // lấy giá trị email được nhập
        let charCount = value1.length;
        if (value1 !== "") {
            $.ajax({
                url: '/shop/check-size',
                type: "GET",
                data: {size: charCount},
                success: function (data) {
                    console.log(value1);
                    if (data === "OK") {
                        status.html("");
                        status.removeClass();
                    } else {
                        status.addClass('alert alert-danger')
                        status.html('Độ dài ký tự phải từ 3 đến 10');
                    }
                }, error: function () {
                    console.log("failed");
                }
            });
        } else {
            //  khi trường email rỗng, đoạn mã sẽ xóa bỏ thông báo lỗi trùng email nếu có và hiển thị trường email trống
            status.empty();
            status.removeClass();
        }
    });
});

// checkbox hiện/ẩn mật khẩu
const showPasswordCheckbox = document.getElementById('showPassword');
const passwordInput = document.getElementById('exampleInputPassword');
const passwordInput2 = document.getElementById('exampleRepeatPassword');

showPasswordCheckbox.addEventListener('change', function () {
    if (showPasswordCheckbox.checked) {
        passwordInput.type = 'text';
        passwordInput2.type = 'text';
    } else {
        passwordInput.type = 'password';
        passwordInput2.type = 'password';
    }
});