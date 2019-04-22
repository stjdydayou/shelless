<div class="bjui-pageHeader">
    <div  class="pull-left">
        <a class="btn btn-orange" href="${__url__}" data-toggle="navtab" data-title="角色管理" data-icon="undo">刷新</a>
    </div>
    <div class="pull-right">
    <@s.oauth "oauth/role/add">
        <a href="/oauth/role/add.htm"
           data-mask="true"
           data-toggle="dialog"
           data-width="600"
           class="btn btn-primary"
           data-icon="plus">
            添加
        </a>
    </@s.oauth>
    <@s.oauth "oauth/role/delete">
        <a type="button" class="btn btn-danger" href="/oauth/role/delete.ajax" data-icon="remove"
           data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="ids"
           data-group="roles">
            删除
        </a>
    </@s.oauth>
    </div>
</div>
<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%">
        <thead>
        <tr>
            <th width="26">
                <input type="checkbox" class="checkboxCtrl" data-group="roles" data-toggle="icheck">
            </th>
            <th width="140">操作</th>
            <th>编号</th>
            <th>角色名</th>
            <th>职能描述</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr target="row_id" rel="${row.id}">
            <td>
                <input type="checkbox" name="roles" data-toggle="icheck" value="${row.id}">
            </td>
            <td>
                <@s.oauth "oauth/role/edit">
                    <a href="/oauth/role/edit.htm?id=${row.id}"
                       data-mask="true" data-width="600" data-toggle="dialog"
                       class="btn btn-info" data-icon="edit" title="修改">
                        修改
                    </a>
                </@s.oauth>
                <@s.oauth "oauth/role/set_resource">
                    <a href="/oauth/role/set_resource.htm?id=${row.id}"
                       data-mask="true" data-toggle="dialog" class="btn btn-warning"
                       data-icon="cog">
                        设置权限
                    </a>
                </@s.oauth>
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td>${row.remark!'-'}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>