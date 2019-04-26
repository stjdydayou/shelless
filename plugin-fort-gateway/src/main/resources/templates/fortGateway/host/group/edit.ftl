<div class="bjui-pageContent">
    <form method="post" action="/fortGateway/group/insertOrUpdate.ajax" class="pageForm" data-toggle="validate">
        <#if hostGroup.id??><input type="hidden" name="id" value="${hostGroup.id}"></#if>
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">名称：</label>
                    <input type="text" value="${hostGroup.name!''}" name="name" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">备注：</label>
                    <textarea name="remark" rows="2" style="width: 350px;">${hostGroup.remark!''}</textarea>
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