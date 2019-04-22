<div class="bjui-pageContent">
    <form method="post" action="/system/oauth/role/save.ajax" data-toggle="validate">
	<#if oauthRoleInfo.id??>
        <input type="hidden" name="id" value="${oauthRoleInfo.id!''}">
	</#if>
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label>角色名称：</label>
                    <input type="text" value="${oauthRoleInfo.name!''}" name="name" class="required" data-rule="required" style="width:200px"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>角色描述：</label>
                    <textarea name="remark" style="width:400px;" rows="2">${(oauthRoleInfo.remark!'')?html}</textarea>
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
