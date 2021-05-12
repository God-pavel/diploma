<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3><@spring.message "page.user"/> ${user.username}</h3>

    <form action="/users/${user.id}" method="post">
        <#list roles as role>
            <div>
                <label><input type="checkbox"
                              name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><@spring.message "users.save"/></button>
    </form>
</@c.page>