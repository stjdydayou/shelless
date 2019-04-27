<div class="bjui-pageContent">
    <form method="post" action="/changeMyLoginPassowd.ajax" data-toggle="validate">
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label>旧的登录密码：</label>
                    <input type="password" name="oldPassword" class="required" data-rule="required"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>新的登录密码：</label>
                    <input type="password" name="newPassword" class="required" data-rule="required"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>确认登录密码：</label>
                    <input type="password" name="reNewPassword" class="required" data-rule="required"/>
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
