<div class="bjui-pageContent">
    <form method="post" action="/changeMyProfile.ajax" data-toggle="validate">
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">UID：</label>
                    <input type="text" disabled value="${baseInfo.id}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">用户昵称：</label>
                    <input type="text" name="nickName" value="${(baseInfo.nickName!'')?html}" class="required" data-rule="required"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">登录账号：</label>
                    <input type="text" disabled value="${(accounts.userName!'')?html}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">手机号：</label>
                    <input type="text" name="mp" value="${(accounts.mp!'')?html}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">邮箱：</label>
                    <input type="text" name="email" value="${(accounts.email!'')?html}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">性别：</label>
                    <input type="radio" name="gender" value="secret" data-toggle="icheck" data-label="保密" <#if baseInfo.gender.code == 0>checked</#if>/>
                    <input type="radio" name="gender" value="male" data-toggle="icheck" data-label="男" <#if baseInfo.gender.code == 1>checked</#if>/>
                    <input type="radio" name="gender" value="female" data-toggle="icheck" data-label="女" <#if baseInfo.gender.code == 2>checked</#if>/>
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
