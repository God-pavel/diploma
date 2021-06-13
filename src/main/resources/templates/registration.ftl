<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.registration"/></h3>

    <#if message??>
        <div class="alert alert-danger" role="alert">${message}</div>
    </#if>

    <form action="/registration" method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.username"/></label>
            <div class="col-sm-6">
                <input type="text" class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="username" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.password"/></label>
            <div class="col-sm-6">
                <input type="password" class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="password" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.name"/></label>
            <div class="col-sm-6">
                <input type="text" class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="name" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "registration.surname"/></label>
            <div class="col-sm-6">
                <input type="text" class="form-control" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="surname" required/>
            </div>
        </div>


        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn btn-outline-warning" type="submit"><@spring.message "registration.register"/></button>
    </form>
</@c.page>