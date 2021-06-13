<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 xmlns="http://www.w3.org/1999/html" style="color: #ffcd30;"><@spring.message "page.profile"/> ${user.username}</h3>

    <#if user.getPhotosImagePath()??>
        <img width="150" height="225" src="${user.getPhotosImagePath()}"/>
    </#if>

    <div class="form-group row">
        <label class="col-sm-1 col-form-label" style="color: #ffcd30;"> <@spring.message "users.username"/></label>
        <div class="col-sm-3">
            <label class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;">${user.username}</label>
        </div>
    </div>
    <div class="form-group row">

        <label class="col-sm-1 col-form-label" style="color: #ffcd30;"> <@spring.message "users.name"/></label>
        <div class="col-sm-3">
            <label class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;">${user.name}</label>
        </div>
    </div>
    <div class="form-group row">

        <label class="col-sm-1 col-form-label" style="color: #ffcd30;"> <@spring.message "users.surname"/></label>
        <div class="col-sm-3">
            <label class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;">${user.surname}</label>
        </div>
    </div>
    <a href="/profile/${user.id}/edit" class="btn btn btn-outline-warning"> <@spring.message "users.edit"/> </a>

    <input type="hidden" name="userId" value="${user.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>

</@c.page>