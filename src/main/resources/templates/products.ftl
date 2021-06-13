<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<#import "/spring.ftl" as spring/>


<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <table style="color: #ffcd30;" class="table table-striped">
                <h3 style="color: #ffcd30;"><@spring.message "page.products"/></h3>
                <thead>
                <tr>
                    <th style="color: #ffcd30;"><@spring.message "users.id"/></th>
                    <th style="color: #ffcd30;"><@spring.message "product.name"/></th>
                    <th style="color: #ffcd30;"><@spring.message "product.energy"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.edit"/></th>
                </tr>
                </thead>
                <tbody>
                <#list products as product>
                    <tr>
                        <td style="color: #ffcd30;">${product.id}</td>
                        <td style="color: #ffcd30;">${product.name}</td>
                        <td style="color: #ffcd30;">${product.energy} <@spring.message "product.cal"/></td>
                        <td style="color: #ffcd30;"><a href="/products/${product.id}"><@spring.message "users.edit"/></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <a href="/products/add" class="btn btn btn-outline-warning"> <@spring.message "product.add"/> </a>
    </div>
    <#import "parts/common.ftl" as c>
    <#import "/spring.ftl" as spring/>
</@c.page>

