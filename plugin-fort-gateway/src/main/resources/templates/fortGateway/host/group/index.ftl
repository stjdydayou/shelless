<div class="bjui-pageHeader">
    <div class="pull-right">
        <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="group.add">
            <a href="/fortGateway/group/edit.htm"
               data-mask="true" data-width="500" data-toggle="dialog"
               class="btn btn-default" data-icon="plus" data-title="添加分组">
                添加
            </a>
        </@s.oauth>
        <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="group.delete">
            <a type="button" class="btn btn-default" href="/fortGateway/group/delete.ajax" data-icon="remove"
               data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="ids" data-group="ids">
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
                <input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck">
            </th>
            <th width="150">操作</th>
            <th>编号</th>
            <th>名称</th>
            <th>修建时间</th>
            <th>备注说明</th>
            <th>主机数</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr target="row_id" rel="${row.id}">
            <td>
                <input type="checkbox" name="ids" data-toggle="icheck" value="${row.id}">
            </td>
            <td>
                <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="group.edit">
                    <a href="/fortGateway/group/edit.htm?id=${row.id}"
                       data-mask="true" data-width="500" data-toggle="dialog"
                       class="btn btn-default btn-sm" data-icon="edit" data-title="修改[${row.name}]">
                        修改
                    </a>
                </@s.oauth>
                <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="group.authority">
                    <a href="/fortGateway/group/authority.htm?id=${row.id}"
                       data-mask="true" data-width="500" data-toggle="dialog"
                       class="btn btn-default btn-sm" data-icon="cogs" data-title="修改[${row.name}]">
                        授权用户
                    </a>
                </@s.oauth>
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td>${row.createdTime?datetime}</td>
            <td>${row.remark!''}</td>
            <td>${row.hostCount!'0'}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>