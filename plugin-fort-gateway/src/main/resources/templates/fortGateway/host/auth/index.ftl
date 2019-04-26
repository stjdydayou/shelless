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
<div class="x-nav">
    <span class="layui-breadcrumb">
        <a href="javascript:;">首页</a>
        <a href="javascript:;">服务器管理</a>
        <a>
          <cite>密钥管理</cite>
        </a>
    </span>
    <span class="x-right">
        <a class="layui-btn layui-btn-small" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i>
        </a>
    </span>

</div>
<div class="x-body">
    <xblock>
        <button class="layui-btn layui-btn-danger" onclick="deleteBatch()"><i class="layui-icon"></i>批量删除</button>
        <button class="layui-btn" onclick="x_admin_show('添加主机','/host/auth/edit.htm',500,500)">
            <i class="layui-icon"></i>添加
        </button>
        <span class="x-right">共有数据：${listData?size} 条</span>
    </xblock>
    <table class="layui-table x-admin">
        <thead>
        <tr>
            <th width="30">
                <div class="layui-unselect header layui-form-checkbox" lay-skin="primary">
                    <i class="layui-icon">&#xe605;</i>
                </div>
            </th>
            <th>编号</th>
            <th>名称</th>
            <th>认证方式</th>
            <th>登录账号</th>
            <th>创建时间</th>
            <th>备注说明</th>
            <th width="70">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr>
            <td>
                <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${row.id}'>
                    <i class="layui-icon">&#xe605;</i>
                </div>
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td>${row.authType.text}</td>
            <td>${row.userName}</td>
            <td>${row.createdTime?datetime}</td>
            <td>${row.remark!''}</td>
            <td class="td-manage">
                <a title="修改密钥" href="javascript:;"
                   onclick="x_admin_show('修改主机','/host/auth/edit.htm?id=${row.id}',500,500)"
                   style="color: blue">
                    <i class="iconfont">&#xe69e;</i>
                    修改
                </a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<script>

    function deleteBatch() {
        var data = tableCheck.getData();
        if (data.length <= 0) {
            layer.msg('请选择要删除的记录');
        }
        layer.confirm('确认要删除吗？', function (index) {
            //捉到所有被选中的，发异步进行删除
            $(".layui-form-checked").not('.header').parents('tr').remove();

            $.post("/host/auth/delete.ajax", {ids: data.join(",")}, function (result) {
                window.location.reload();
            });
        });
    }
</script>
</body>

</html>