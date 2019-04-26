<div class="bjui-pageContent">
    <form method="post" action="/fortGateway/auth/insertOrUpdate.ajax" class="pageForm" data-toggle="validate">
        <#if hostAuth.id??><input type="hidden" name="id" value="${hostAuth.id}"></#if>
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">密钥名称：</label>
                    <input type="text" value="${hostAuth.name!''}" name="name" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">用户名：</label>
                    <input type="text" value="${hostAuth.userName!''}" name="userName" class="required" data-rule="required" style="width: 200px;"/>
                </td>
            </tr>
            <td>
                <label class="control-label x85">认证类型：</label>
                <input type="radio" name="authType" id="authType_password" data-toggle="icheck" value="password" data-label="用户名密码" <#if hostAuth.authType.code==0>checked</#if>>
                <input type="radio" name="authType" id="authType_sshkey" data-toggle="icheck" value="sshkey" data-label="SSH密钥"  <#if hostAuth.authType.code==1>checked</#if>>
            </td>
            <tr <#if hostAuth.authType.code==1>style="display: none"</#if> class="authType authType-password">
                <td>
                    <label class="control-label x85">密码：</label>
                    <input type="text" value="${password!''}" name="password" style="width: 200px;"/>
                </td>
            </tr>
            <tr <#if hostAuth.authType.code==0>style="display: none"</#if> class="authType authType-sshkey">
                <td>
                    <label class="control-label x85">密钥密码：</label>
                    <input type="text" value="${passphrase!''}" name="passphrase" style="width: 200px;"/>
                </td>
            </tr>
            <tr <#if hostAuth.authType.code==0>style="display: none"</#if> class="authType authType-sshkey">
                <td>
                    <label class="control-label x85">SSH公钥：</label>
                    <textarea name="publicKey" rows="2" style="width: 450px;">${publicKey!''}</textarea>

                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">备注：</label>
                    <textarea name="remark" rows="2" style="width: 450px;">${hostAuth.remark!''}</textarea>
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
<script type="text/javascript">
    $('input').on('ifChecked', function (event) {
        var authType = $(this).val();
        $(".authType").hide();
        $(".authType-" + authType).show();
    });
</script>