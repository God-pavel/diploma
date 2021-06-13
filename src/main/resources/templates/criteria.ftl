<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">
<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <table class="table table-striped">
                <h3 style="color: #ffcd30;"><@spring.message "page.criteria"/></h3>
                <#if message??>
                    <div class="alert alert-danger" role="alert">${message}</div>
                </#if>
                <form action="${path}/findRecipe" method="post">
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "criteria.time"/></label>
                        <div class="col-sm-4">
                            <input type="number" style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" class="form-control" name="time"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "criteria.category"/>: </label>
                        <div class="col-sm-4">
                            <#list categories as category>
                                <div>
                                    <label style="color: #ffcd30;"><input type="radio" class="form-check-input" name="category" value=${category}> ${category}
                                    </label>
                                </div>
                            </#list>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "criteria.products"/></label>
                        <div class="col-sm-4">
                            <select name="products" multiple style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;">
                                <#list products as product >
                                    <option style="color: #ffcd30; background-color: #393d3f; border-color:  #ffcd30;" value="${product.name}">${product.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn btn-outline-warning" type="submit"><@spring.message "criteria.filter"/></button>
                </form>
            </table>
        </div>
    </div>
</@c.page>