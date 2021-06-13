<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.login"/></h3>
    <#if error>
        <div class="alert alert-danger" role="alert"><@spring.message "message.invalid"/></div>
    </#if>
    <form action="/login" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"
                   style="color: #ffcd30;"> <@spring.message "registration.username"/></label>
            <div class="col-sm-6">
                <input type="text" class="form-control"
                       style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="username"
                       required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"
                   style="color: #ffcd30;"> <@spring.message "registration.password"/></label>
            <div class="col-sm-6">
                <input type="password" class="form-control"
                       style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" name="password"
                       required/>
            </div>
        </div>


        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn btn-outline-warning" type="submit"><@spring.message "login.enter"/></button>
    </form>
    <label style="color: #ffcd30;"> <@spring.message "login.ask"/> <a
                href="/registration"> <@spring.message "login.link"/> </a> </label>
</@c.page>
