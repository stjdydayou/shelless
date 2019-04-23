<div class="bjui-pageContent">
    <form method="post" action="/system/oauth/user/roles.ajax" class="pageForm" data-toggle="ajaxform">
        <input type="hidden" name="uid" value="${userBaseInfo.id!''}">
        <table class="table table-condensed">
            <tbody>
			<#list loginAccounts as loginAccount>
            <tr>
                <td>
                    <label for="j_dialog_name" class="control-label x85">${loginAccount.accountType.text}：</label>
                    <input type="text" value="${loginAccount.loginAccount}" disabled="disabled"/>
                </td>
            </tr>
			</#list>
            <tr>
                <td>
                    <label for="j_dialog_tel" class="control-label x85">用户角色：</label>
				<#list listAllRoles as row>
                    <label>
                        <input type="checkbox" value="${row.id}" name="roleIds" data-toggle="icheck" data-label="${row.name}" ${listRoles?seq_contains(row.id)?string('checked', '')}>
                    </label>
				</#list>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li>
            <button type="button" class="btn-close">关闭</button>
        </li>
        <li>
            <button type="submit" class="btn-blue">保存</button>
        </li>
    </ul>
</div>