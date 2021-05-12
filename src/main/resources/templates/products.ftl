<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<#import "/spring.ftl" as spring/>


<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <table class="table table-striped">
                <h3><@spring.message "page.products"/></h3>
                <thead>
                <tr>
                    <th><@spring.message "users.id"/></th>
                    <th><@spring.message "product.name"/></th>
                    <th><@spring.message "product.energy"/></th>
                    <th><@spring.message "users.edit"/></th>
                </tr>
                </thead>
                <tbody>
                <#list products as product>
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.energy} Cal</td>
                        <td><a href="/products/${product.id}"><@spring.message "users.edit"/></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <a href="/products/add"> <@spring.message "product.add"/> </a>
    </div>
    <#import "parts/common.ftl" as c>
    <#import "/spring.ftl" as spring/>
</@c.page>

