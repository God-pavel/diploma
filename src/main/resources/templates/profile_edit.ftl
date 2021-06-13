<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.user"/> ${user.username}</h3>
    <#if message??>
        <div class="alert alert-danger" role="alert">${message}</div>
    </#if>
    <#if user.getPhotosImagePath()??>
        <img width="150" height="225" src="${user.getPhotosImagePath()}"/>
    </#if>
    <form action="/profile/${user.id}/edit" enctype="multipart/form-data" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.username"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="username" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" required value="${user.username}"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.name"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="name" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" required value="${user.name}"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.surname"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="surname" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" required value="${user.surname}"/>
            </div>
        </div>
        <div>
            <label style="color: #ffcd30;"><@spring.message "users.photo"/> : </label>
            <input type="file" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="image" accept="image/png, image/jpeg"/>
        </div>

        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn btn-outline-warning" type="submit"><@spring.message "users.save"/></button>
    </form>
</@c.page>