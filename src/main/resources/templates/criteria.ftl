<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">
<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <table class="table table-striped">
                <h3><@spring.message "page.criteria"/></h3>
                <#if message??>
                    <div class="alert alert-danger" role="alert">${message}</div>
                </#if>
                <form action="${path}/findRecipe" method="post">
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label"> <@spring.message "criteria.time"/></label>
                        <div class="col-sm-4">
                            <input type="number" class="form-control" name="time"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label"> <@spring.message "criteria.category"/>: </label>
                        <div class="col-sm-4">
                            <#list categories as category>
                                <div>
                                    <label><input type="radio" name="category" value=${category}> ${category}
                                    </label>
                                </div>
                            </#list>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label"> <@spring.message "criteria.products"/></label>
                        <div class="col-sm-4">
                            <select name="products" multiple>
                                <#list products as product >
                                    <option value="${product.name}">${product.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary" type="submit"><@spring.message "criteria.filter"/></button>
                </form>
            </table>
        </div>
    </div>
</@c.page>