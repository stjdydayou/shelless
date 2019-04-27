<div class="bjui-pageHeader">
    <form id="pagerForm" class="pull-left" data-toggle="ajaxsearch" action="/fortGateway/hostInfo/index.htm" method="post">
    <#include "/pagerForm.ftl" />
        <div class="bjui-searchBar">
            <label>主机名：</label>
            <input type="text" name="name" value="${vo.name!''}" name="id" class="form-control">&nbsp;
            <label>主机分组:</label>
            <select name="groupId" data-toggle="selectpicker">
                <option value="">全部</option>
            <#list listGroups as group>
                <option value="${group.id}"
                        <#if vo.groupId?? && vo.groupId==group.id>selected</#if>>${group.name}</option>
            </#list>
            </select>
            <label>认证密钥:</label>
            <select name="authId" data-toggle="selectpicker">
                <option value="">全部</option>
            <#list listAuths as auth>
                        <option value="${auth.id}"
                                <#if vo.authId?? && vo.authId==auth.id>selected</#if>>${auth.name}</option>
            </#list>
            </select>
            &nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true"
               data-icon="undo">清空查询</a>
        </div>
    </form>
    <div class="pull-right">
        <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="host.add">
            <a href="/fortGateway/hostInfo/edit.htm"
               data-mask="true" data-width="600" data-toggle="dialog"
               class="btn btn-default" data-icon="plus" title="添加主机">
                添加
            </a>
        </@s.oauth>
        <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="host.delete">
            <a type="button" class="btn btn-default" href="/fortGateway/hostInfo/delete.ajax" data-icon="remove"
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
                <input type="checkbox" class="checkboxCtrl" data-group="roles" data-toggle="icheck">
            </th>
            <th>编号</th>
            <th>主机名</th>
            <th>分组</th>
            <th>认证密钥</th>
            <th>主机地址</th>
            <th>端口</th>
            <th>操作系统</th>
            <th>备注说明</th>
            <th width="150">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr target="row_id" rel="${row.id}">
            <td>
                <input type="checkbox" name="ids" data-toggle="icheck" value="${row.id}">
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td><#if row.hostGroup??>${row.hostGroup.name}<#else>-</#if></td>
            <td><#if row.hostAuth??>${row.hostAuth.name}<#else>-</#if></td>
            <td>${row.hostAddress}</td>
            <td>${row.portNumber}</td>
            <td>${row.os}</td>
            <td>${row.remark!''}</td>
            <td>
                <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="host.edit">
                    <a href="/fortGateway/hostInfo/edit.htm?id=${row.id}"
                       data-mask="true" data-width="600" data-toggle="dialog"
                       class="btn btn-default btn-sm" data-icon="edit" title="修改[${row.name}]">
                        修改
                    </a>
                </@s.oauth>

                <@s.oauth pluginKey="fortGateway" moduleKey="hostManager" authorities="host.terminal">
                <a href="javascript:;" onclick="openTerminalDialog(this,'${row.id}','终端:${row.name}')" class="btn btn-default btn-sm open-terminal" data-icon="terminal">
                    打开终端
                </a>
                </@s.oauth>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<#include "/paginator.ftl" />

<script type="text/javascript">
    function openTerminalDialog(object, id, title) {
        $(object).navtab({
            id: 'terminal-' + id + Math.random(),
            url: '/fortGateway/hostInfo/terminal/' + id + '.htm',
            title: title
        });
    }
</script>
