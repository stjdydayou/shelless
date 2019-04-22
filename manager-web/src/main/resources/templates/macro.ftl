<#ftl strip_whitespace=true>

<#--
<@s.auth />
-->

<#macro oauth pluginKey='' moduleKey='' authorities=''>
    <@authorityDirective pluginKey=pluginKey moduleKey=moduleKey authorities=authorities>
        <#if hasAuthority><#nested/></#if>
    </@authorityDirective>
</#macro>