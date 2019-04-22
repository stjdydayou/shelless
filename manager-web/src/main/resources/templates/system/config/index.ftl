<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-nowrap="true" data-width="100%">
        <thead>
        <tr>
            <th>所属于插件</th>
            <th>所属模块</th>
            <th>配置KEY</th>
            <th>描述</th>
            <th>类型</th>
        </tr>
        </thead>
        <tbody>
        <#list listPlugins as plugin>
        <#list plugin.listModules as module>
            <#list module.listConfigs as config>
        <tr>
            <td>${plugin.name?html}(${plugin.key?html})</td>
            <td>${module.name?html}(${module.key?html})</td>
            <td>${config.key?html}</td>
            <td>${config.desc?html}</td>
            <td>${config.type.text}</td>
        </tr>
            </#list>
        </#list>
        </#list>
        </tbody>
    </table>
</div>