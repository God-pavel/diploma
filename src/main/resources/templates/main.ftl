<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">
<@c.page>
    <h3><@spring.message "page.main"/></h3>
    <#if message != "">
        <div class="alert alert-danger" role="alert">${message}</div>
    </#if>
        <div class="row">
            <div class="col-sm-2">
                <form action="/main/findRecipe" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary" type="submit"><@spring.message "main.find.recipe"/></button>
                </form>
            </div>
            <div class="col-sm-2">
                <#if name != "unknown">
                    <form action="/main/createRecipe" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-primary" type="submit"><@spring.message "main.create.recipe"/></button>
                    </form>
                </#if>
            </div>
        </div>

    <@p.pager url page/>

    <div class="card-columns">
        <#list page.content as recipe>
            <div class="card my-3" style="width: 18rem;">
                <b><@spring.message "main.recipe"/> ${recipe.name}</b>
                <p class="m-2">
                <p><@spring.message "main.creator"/> : ${recipe.creator.username}</p>
                <p><@spring.message "main.rate"/> : ${recipe.rate}</p>
                <p><@spring.message "main.category"/> : ${recipe.recipeCategory.name()}</p>
                <p><@spring.message "recipe.time"/> : ${recipe.time}</p>
                <#list recipe.ingredients as ingredient>
                    <p>${ingredient.product.name} - ${ingredient.weight} <@spring.message "main.gram"/></p>
                </#list>

                <div class="card-footer text-muted">
                    <b style="float: left;"><@spring.message "main.total"/> : ${recipe.totalEnergy}</b>
                    <#--                    <#if isSeniorCashier>-->
                    <#--                        <form action="/main/deleteCheck" method="post">-->
                    <#--                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    <#--                            <input type="hidden" name="checkId" value="${check.id}"/>-->
                    <#--                            <button type="submit"-->
                    <#--                                    class="btn ml-4 btn-secondary"><@spring.message "main.deleteCheck"/></button>-->
                    <#--                        </form>-->
                    <#--                    </#if>-->
                </div>
            </div>
        </#list>
    </div>

</@c.page>