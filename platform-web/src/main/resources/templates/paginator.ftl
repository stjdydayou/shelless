<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="10" <#if paginator.limit==10>selected="selected"</#if>>10</option>
                <option value="20" <#if paginator.limit==20>selected="selected"</#if>>20</option>
                <option value="30" <#if paginator.limit==30>selected="selected"</#if>>30</option>
                <option value="40" <#if paginator.limit==40>selected="selected"</#if>>40</option>
                <option value="50" <#if paginator.limit==50>selected="selected"</#if>>50</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${paginator.totalCount} 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${paginator.totalCount}" data-page-size="${paginator.limit}" data-page-current="${paginator.page}"></div>
</div>