<div class="bjui-pageContent">
    <form method="post" action="/system/config/save.ajax" class="pageForm" data-toggle="validate">
        <input type="hidden" name="pluginKey" value="${config.pluginKey!''}"/>
        <input type="hidden" name="moduleKey" value="${config.moduleKey!''}"/>
        <input type="hidden" name="configKey" value="${config.configKey!''}"/>
        <input type="hidden" name="configType" value="${config.configType!''}"/>
        <#if config.configType.code==1>
            <input type="text" name="dataValue" class="large" value="${(config.dataValue!"")?html}" style="width: 100%"/>
        </#if>
        <#if config.configType.code==2>
            <input type="text" name="dataValue" class="small" value="${(config.dataValue!"")?html}" data-rule="number"/>
        </#if>
        <#if config.configType.code==3>
            <input type="radio" name="dataValue" value="true" <#if config.dataValue?? && config.dataValue=='true'>checked</#if> data-toggle="icheck" data-label="是"/>
            <input type="radio" name="dataValue" value="false" <#if config.dataValue?? && config.dataValue=='false'>checked</#if> data-toggle="icheck" data-label="否"/>
        </#if>
        <#if config.configType.code==4>
            <textarea name="dataValue" style="width: 100%" rows="4">${(config.dataValue!"")?html}</textarea>
        </#if>
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