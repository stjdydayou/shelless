<div class="bjui-pageContent">
    <form method="post" action="/system/oauth/role/authority.ajax" class="pageForm" data-toggle="validate">
        <input type="hidden" name="roleId" value="${oauthRoleInfo.id}"/>
<#list listPlugins as plugin>
    <#list plugin.listModules as module>
        <div class="panel panel-default">
            <div class="panel-heading"><h3 class="panel-title">${plugin.name}/${module.name}</h3></div>
            <div class="panel-body bjui-doc" style="padding:0;">
        <#list module.listAuthorities as authority>
            <#assign authorityId = plugin.key+"."+module.key+"."+authority.key>
            <div style="display: inline-block;margin: 10px;">
                <input type="checkbox" name="authorities" id="${authorityId}"
                       <#if listAuthorities?seq_contains(authorityId)>checked='checked'</#if>
                       value="${authorityId}" data-toggle="icheck" data-label="${authority.name}">
            </div>
        </#list>
            </div>
        </div>
    </#list>
</#list>
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