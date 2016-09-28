/**
 * 后台用户管理操作
 */

$(document).ready(function () {

    var performFellowshipOperation = function (url,name,created_date,display_name,enable,owner,owner_username) {
        new Dialog("确定要添加团契吗？", function () {
            var formData = new FormData();
            formData.append("name",$(".ui.admin.fellowship.form input[fellowship_name='name']").val());
            formData.append("created_date",$(".ui.admin.fellowship.form input[fellowship_name='created_date']").val());
            formData.append("display_name",$(".ui.admin.fellowship.form input[fellowship_name='display_name']").val());
            formData.append("enable",$(".ui.admin.fellowship.form input[fellowship_name='enable']").val());
            formData.append("owner",$(".ui.admin.fellowship.form input[fellowship_name='owner']").val());
            formData.append("owner_username",$(".ui.admin.fellowship.form input[fellowship_name='owner_username']").val());
            formData.append("_csrf", $(".ui.admin.fellowship.form input[fellowship_name='_csrf']").val());
            $.ajax({
                url: "/api/fellowship/add",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if (status) {
                        new Dialog("消息", "添加团契成功", function () {
                            location.reload();
                        }).message();
                    } else {
                        new Dialog("消息", "添加团契失败").error();
                    }
                },
                error: function () {
                    new Dialog("消息", "添加团契失败").error();
                }
            });
        }).confirm();
    };

    $(".ui.admin.fellowship.add.modal").modal({
        closeable: false,
        onApprove: function () {
            addFellowship($(".ui.admin.fellowship.add.modal").modal("show"));
        },
        onDeny: function () {
            return true;
        }
    });

    $(".ui.admin.fellowship.add.fellowship.disable.button").on("click", function () {
        performFellowshipOperation("disable", $(this).data("name"),$(this).data("created_date"),$(this).data("enable"),$(this).data("owner"),$(this).data("owner_username"));
    });

    $(".ui.admin.fellowship.add.fellowship.enable.button").on("click", function () {
        performUserOperation("enable", $(this).data("name"),$(this).data("created_date"),$(this).data("enable"),$(this).data("owner"),$(this).data("owner_username"));
    });
    $(".ui.admin.fellowship.add.fellowship.enable.button").on("click",function () {
        $(".ui.admin.fellowship.fellowship.primary.fluid.add.modal").modal("show");
    })
    $(".ui.admin.fellowship.change.owner.secondary.button").on("click",function () {
        $(".ui.admin.fellowship.change.owner.secondary.fluid.modal").modal("show");
    })

});
