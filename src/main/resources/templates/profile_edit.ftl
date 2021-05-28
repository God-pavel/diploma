<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3><@spring.message "page.user"/> ${user.username}</h3>
    <#if message??>
        <div class="alert alert-danger" role="alert">${message}</div>
    </#if>
    <#if user.getPhotosImagePath()??>
        <img width="200" height="300" src="${user.getPhotosImagePath()}"/>
    </#if>
    <form action="/profile/${user.id}/edit" enctype="multipart/form-data" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "registration.username"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="username" required value="${user.username}"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "registration.name"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="name" required value="${user.name}"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "registration.surname"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="surname" required value="${user.surname}"/>
            </div>
        </div>
        <div>
            <label><@spring.message "users.photo"/>: </label>
            <input type="file" name="image" accept="image/png, image/jpeg"/>
        </div>

        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><@spring.message "users.save"/></button>
    </form>
</@c.page>