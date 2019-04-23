<div class="bjui-pageHeader">
    <form id="pagerForm" class="pull-left" data-toggle="ajaxsearch" action="${__url__}" method="post">
    <#include "/pagerForm.ftl" />
        <div class="bjui-searchBar">
            <label>邮箱/手机号：</label>
            <input type="text" name="loginAccount" value="${vo.loginAccount!''}" name="id" class="form-control">&nbsp;
            <label>状态:</label>
            <select name="state" data-toggle="selectpicker">
                <option value="">全部</option>
            <#list userStateArray as string>
                <option value="${string}" <#if vo.state?? && vo.state.code == string.code>selected</#if>>${string.text}</option>
            </#list>
            </select>
            &nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
        </div>
    </form>
    <div class="pull-right">
    <@s.oauth pluginKey="system" moduleKey="oauth" authorities="user.add">
        <a href="/system/oauth/user/add.htm" data-icon="plus"
           data-mask="true" data-toggle="dialog" class="btn btn-default" title="设置用户角色">
            添加
        </a>
    </@s.oauth>
    <@s.oauth pluginKey="system" moduleKey="oauth" authorities="user.disable">
        <a class="btn btn-default" href="/system/oauth/user/disable.ajax" data-icon="lock" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要禁用所选择的用户吗？" data-idname="ids" data-group="userInfos">
            禁用
        </a>
    </@s.oauth>
    <@s.oauth pluginKey="system" moduleKey="oauth" authorities="user.enable">
        <a class="btn btn-default" href="/system/oauth/user/enable.ajax" data-icon="unlock" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要启用所选择的用户吗？" data-idname="ids" data-group="userInfos">
            启用
        </a>
    </@s.oauth>
    <@s.oauth pluginKey="system" moduleKey="oauth" authorities="user.resetPassword">
        <a class="btn btn-default" href="/system/oauth/user/resetPassword.ajax" data-icon="undo" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要此用户重置登录密码吗？" data-idname="ids" data-group="userInfos">
            重置登录密码
        </a>
    </@s.oauth>
    </div>
</div>

<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed">
        <thead>
        <tr>
            <th width="26">
                <input type="checkbox" class="checkboxCtrl" data-group="userInfos" data-toggle="icheck">
            </th>
            <th>操作</th>
            <th data-order-field="id" width="50">ID</th>
            <th>账号</th>
            <th>昵称</th>
            <th data-order-field="state">状态</th>
            <th data-order-field="register_time">注册时间</th>
            <th>注册IP</th>
            <th>角色</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr rel="${row.userBaseInfo.id}">
            <td>
                <input type="checkbox" name="userInfos" data-toggle="icheck" value="${row.userBaseInfo.id}">
            </td>
            <td>
                <@s.oauth pluginKey="system" moduleKey="oauth" authorities="user.roles">
                    <a href="/system/oauth/user/roles.htm?id=${row.userBaseInfo.id}" data-icon="cog"
                       data-mask="true" data-toggle="dialog" class="btn btn-default" title="设置用户角色">设置角色</a>
                </@s.oauth>
            </td>
            <td>${row.userBaseInfo.id}</td>
            <td>
                <#list row.loginAccounts as loginAccount>
                    [${loginAccount.loginAccount}]
                </#list>
            </td>
            <td>${row.userBaseInfo.nickName!'-'}</td>
            <td>
                <#if row.userBaseInfo.state.code==1>
                    <span class="label label-success">${row.userBaseInfo.state.text}</span>
                <#else>
                    <span class="label label-danger">${row.userBaseInfo.state.text}</span>
                </#if>
            </td>
            <td>${row.userBaseInfo.registerTime?datetime}</td>
            <td>${row.userBaseInfo.registerIp?html}</td>
            <td>
                <#if row.listRoleIds?size gt 0>
                    <#list listAllRoleData as role>
                        <#if row.listRoleIds?seq_contains(role.id)>
                            [${role.name}]
                        </#if>
                    </#list>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<#include "/paginator.ftl" />