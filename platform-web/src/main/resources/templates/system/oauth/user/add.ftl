<div class="bjui-pageContent">
    <form method="post" action="/system/oauth/user/add.ajax" class="pageForm" data-toggle="ajaxform">
        <table class="table table-condensed">
            <tbody>
            <tr>
                <td>
                    <label class="control-label x85">用户昵称：</label>
                    <input type="text" name="nickName" value="" class="required" data-rule="required"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">登录账号：</label>
                    <input type="text" name="userName" class="required" data-rule="required"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">手机号：</label>
                    <input type="text" name="mp" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">邮箱：</label>
                    <input type="text" name="email" value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="control-label x85">性别：</label>
                    <input type="radio" name="gender" value="secret" data-toggle="icheck" data-label="保密"/>
                    <input type="radio" name="gender" value="male" data-toggle="icheck" data-label="男"/>
                    <input type="radio" name="gender" value="female" data-toggle="icheck" data-label="女"/>
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