<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 xmlns="http://www.w3.org/1999/html"><@spring.message "page.profile"/> ${user.username}</h3>

    <#if user.getPhotosImagePath()??>
        <img width="200" height="300" src="${user.getPhotosImagePath()}"/>
    </#if>

    <div class="form-group row">
        <label class="col-sm-1 col-form-label"> <@spring.message "users.username"/></label>
        <div class="col-sm-3">
            <label class="form-control">${user.username}</label>
        </div>
    </div>
    <div class="form-group row">

        <label class="col-sm-1 col-form-label"> <@spring.message "users.name"/></label>
        <div class="col-sm-3">
            <label class="form-control">${user.name}</label>
        </div>
    </div>
    <div class="form-group row">

        <label class="col-sm-1 col-form-label"> <@spring.message "users.surname"/></label>
        <div class="col-sm-3">
            <label class="form-control">${user.surname}</label>
        </div>
    </div>
    <a href="/profile/${user.id}/edit"> <@spring.message "users.edit"/> </a>

    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

</@c.page>