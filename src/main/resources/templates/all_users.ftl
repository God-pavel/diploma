<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>



<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h3 style="color: #ffcd30;"><@spring.message "page.users"/></h3>
            <table class="table table-striped" style="color: #ffcd30;">
                <thead>
                <tr>
                    <th style="color: #ffcd30;"><@spring.message "users.id"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.username"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.name"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.surname"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.role"/></th>
                    <th style="color: #ffcd30;"><@spring.message "users.edit"/></th>
                </tr>
                </thead>
                <tbody>
                <#list users as user>
                    <tr>
                        <td style="color: #ffcd30;">${user.id}</td>
                        <td style="color: #ffcd30;">${user.username}</td>
                        <td style="color: #ffcd30;">${user.name}</td>
                        <td style="color: #ffcd30;">${user.surname}</td>
                        <td style="color: #ffcd30;"><#list user.roles as role>${role}<#sep>, </#list></td>
                        <td><a href="/users/${user.id}"><@spring.message "users.edit"/></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>