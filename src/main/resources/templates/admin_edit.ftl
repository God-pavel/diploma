<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.user"/> ${user.username}</h3>

    <form action="/users/${user.id}" method="post">
        <#list roles as role>
            <div>
                <label style="color: #ffcd30;"><input type="checkbox"
                              name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn btn-outline-warning" type="submit"><@spring.message "users.save"/></button>
    </form>
</@c.page>