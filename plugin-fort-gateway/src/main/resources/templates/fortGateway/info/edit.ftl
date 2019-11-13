<div class="bjui-pageContent">
    <form method="post" action="/fortGateway/hostInfo/insertOrUpdate.ajax" class="pageForm" data-toggle="validate">
        <#if hostInfo.id??><input type="hidden" name="id" value="${hostInfo.id}"></#if>
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">名称：</label>
                    <input type="text" value="${hostInfo.name!''}" name="name" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">主机地址：</label>
                    <input type="text" value="${hostInfo.hostAddress!''}" name="hostAddress" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">主机端口：</label>
                    <input type="text" value="${hostInfo.portNumber!''}" name="portNumber" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">操作系统：</label>
                    <select name="os" data-toggle="selectpicker" data-rule="required">
                        <option value="">请选择操作系统</option>
                        <option value="Linux" <#if hostInfo.os?? && hostInfo.os=="Linux">selected</#if>>Linux</option>
                        <option value="Windows" <#if hostInfo.os?? && hostInfo.os=="Windows">selected</#if>>Windows</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">所属分组：</label>
                    <select name="groupId" data-toggle="selectpicker" data-rule="required">
                        <option value="">请选择主机分组</option>
                        <#list listGroups as group>
                            <option value="${group.id}"
                                    <#if hostInfo.groupId?? && hostInfo.groupId==group.id>selected</#if>>${group.name}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">连接密钥：</label>
                    <select name="authId" data-toggle="selectpicker" data-rule="required">
                        <option value="">请选择链接密钥</option>
                        <#list listAuths as auth>
                            <option value="${auth.id}"
                                    <#if hostInfo.authId?? && hostInfo.authId==auth.id>selected</#if>>${auth.name}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">备注：</label>
                    <textarea name="remark" rows="2" style="width: 350px;">${hostInfo.remark!''}</textarea>
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