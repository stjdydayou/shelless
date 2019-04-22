<div class="bjui-pageHeader">
    <form id="pagerForm" class="pull-left" data-toggle="ajaxsearch" action="${__url__}" method="post">
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
        <a class="btn btn-danger" href="/oauth/user/disable.ajax" data-icon="lock" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要禁用所选择的用户吗？" data-idname="ids" data-group="userInfos">
            禁用
        </a>
        <a class="btn btn-success" href="/oauth/user/enable.ajax" data-icon="unlock" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要启用所选择的用户吗？" data-idname="ids" data-group="userInfos">
            启用
        </a>
        <a class="btn btn-info" href="/oauth/user/resetpwd.ajax" data-icon="undo" data-toggle="doajaxchecked"
           data-confirm-msg="您确定要此用户重置登录密码吗？" data-idname="ids" data-group="userInfos">
            重置登录密码
        </a>
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
            <th width="200">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr target="row_id" rel="${row.id}">
            <td>
                <input type="checkbox" name="roles" data-toggle="icheck" value="${row.id}">
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td><#if row.hostGroup??>${row.hostGroup.name}<#else>-</#if></td>
            <td><#if row.hostAuth??>${row.hostAuth.name}<#else>-</#if></td>
            <td>${row.hostAddress}</td>
            <td>${row.portNumber}</td>
            <td>${row.os}</td>
            <td>${row.remark!''}</td>
            <td class="td-manage">
                <a href="/webshell/hostInfo/terminal/${row.id}.htm" data-id="terminal-${row.id}"
                   data-mask="false" data-width="800" data-toggle="dialog" data-max="true"
                   class="btn btn-default btn-sm" data-icon="terminal" data-title="${row.name}(${row.hostAddress})">
                    打开终端
                </a>

                <a href="/login.htm?id=${row.id}"
                   data-mask="true" data-width="600" data-toggle="dialog"
                   class="btn btn-blue" data-icon="edit" title="修改${row.name}">
                    修改
                </a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<#include "/paginator.ftl" />