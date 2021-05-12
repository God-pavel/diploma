<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">
<@c.page>
    <div class="row justify-content-center">
        <div class="col-md-12">
            <table class="table table-striped">
                <h3><@spring.message "page.product.edit"/></h3>
                <#if message??>
                    <div class="alert alert-danger" role="alert">${message}</div>
                </#if>
                <form action="/products" method="post">
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label"> <@spring.message "product.name"/></label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="name" required value="${product.name}"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label"> <@spring.message "product.energy"/></label>
                        <div class="col-sm-4">
                            <input type="number" class="form-control" name="energy" step="any" required value="${product.energy}"/>
                        </div>
                    </div>
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary" type="submit"><@spring.message "users.edit"/></button>
                </form>
            </table>
        </div>
    </div>
</@c.page>