// 登录页面的弹出和关闭


// 登录表单验证
$(document).ready(function () {

    $('.ui.form').form({
        userName: {
            identifier: 'userName',
            rules: [{
                type: 'empty',
                prompt: '请输入用户名'
            }]
        },
        password: {
            identifier: 'password',
            rules: [{
                type: 'empty',
                prompt: '请输入密码'
            }, {
                type: 'length[6]',
                prompt: '密码至少为 6 位'
            }]
        }
    }, {
        inline: true,
        on: 'blur',
        onSuccess: submitForm
    });

    $('.ui.form').submit(function (e) {
        return false;
    });
    //checkbox init
    $('.ui.checkbox').checkbox();

    $('.ui.dropdown').dropdown({on: 'hover'});
});
function submitForm() {
    alert('dd');
    var formData = $('.ui.form input').serializeArray(); //or .serialize();
    $.ajax({
        type: 'POST',
        url: '',
        data: formData
    });
}
