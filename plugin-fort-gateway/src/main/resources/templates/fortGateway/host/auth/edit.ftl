<!DOCTYPE html>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script type="text/javascript" src="/js/cookie.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="/js/html5.min.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="x-body">
    <form class="layui-form">
        <#if hostAuth.id??><input type="hidden" name="id" value="${hostAuth.id}"></#if>
        <input type="hidden" name="authType" value="password">
        <div class="layui-form-item">
            <label for="name" class="layui-form-label">
                <span class="x-red">*</span>密钥名称
            </label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required"
                       value="${hostAuth.name!''}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="hostAddress" class="layui-form-label">
                <span class="x-red">*</span>用户名
            </label>
            <div class="layui-input-inline">
                <input type="text" id="userName" name="userName" required="" lay-verify="required"
                       value="${hostAuth.userName!''}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label for="portNumber" class="layui-form-label">
                <span class="x-red">*</span>密码
            </label>
            <div class="layui-input-inline">
                <input type="text" id="password" name="password" required="" lay-verify="required"
                       value="${hostAuth.password!''}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label for="remark" class="layui-form-label">
                备注
            </label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" id="remark" name="remark"
                          class="layui-textarea">${hostAuth.remark!''}</textarea>
            </div>
        </div>

        <div class="layui-form-item ">
            <label for="L_repass" class="layui-form-label">
            </label>
            <button class="layui-btn" lay-filter="insertOrUpdate" lay-submit="">
                增加
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form, layer = layui.layer;

        //监听提交
        form.on('submit(insertOrUpdate)', function (data) {
            $.post("/host/auth/insertOrUpdate.ajax", data.field, function (result) {
                if (result.code === 'SUCCESS') {
                    parent.location.reload();
                } else {
                    layer.msg(data.message);
                }
            });
            return false;
        });
    });
</script>
</body>

</html>