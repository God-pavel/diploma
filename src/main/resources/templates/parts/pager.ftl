<#macro pager url page>
    <div class="mt-3">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;"><@spring.message "pager.pages"/></a>
            </li>
            <#list 1..page.getTotalPages() as p>
                <#if (p-1) ==page.getNumber()>
                    <li class="page-item active">
                        <a class="btn btn btn-outline-warning" href="#" tabindex="-1">${p}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="btn btn btn-outline-warning" href="${url}?page=${p-1}&size=${page.getSize()}"
                           tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;"><@spring.message "pager.elems"/></a>
            </li>
            <#list [3,6] as c>
                <#if c ==page.getSize()>
                    <li class="page-item active">
                        <a class="btn btn btn-outline-warning" href="#" tabindex="-1">${c}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="btn btn btn-outline-warning" href="${url}?page=${page.getNumber()}&size=${c}"
                           tabindex="-1">${c}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</#macro>