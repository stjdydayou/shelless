<div class="bjui-pageContent">
    <form method="post" action="/fortGateway/group/authority.ajax" class="pageForm" data-toggle="validate">
        <input type="hidden" name="groupId" value="${hostGroup.id}">
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">名称：</label>
                    <input type="text" value="${hostGroup.name!''}" disabled class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">备注：</label>
                    <textarea disabled rows="2" style="width: 350px;">${hostGroup.remark!''}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">用户ID：</label>
                    <textarea name="userIds" rows="2" style="width: 350px;">${userIds!''}</textarea>
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