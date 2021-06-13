<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">
<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.recommendations"/></h3>
    <div class="row justify-content-start">
        <div class="col-3">
            <@p.pager url page/>
        </div>
        <div class="col-9">
            <div class="row justify-content-end">
                <div class="col-3">
                    <form action="/recommendation/filterRecipes" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-outline-warning" type="submit"><@spring.message "main.find.recipe"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="card-columns">
        <#list page.content as recipe>
            <div class="card border-warning" style="width: 18rem; background-color: #2b2b2b">
                <#if recipe.getPhotosImagePath()??>
                    <img class="card-img-top" src="${recipe.getPhotosImagePath()}" alt="Recipe image cap">
                </#if>
                <div class="card-body" >
                    <h5 class="card-title" style="color: #ffcd30;">${recipe.name}</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item" style="color: #ffcd30; background-color: #2b2b2b;"><@spring.message "main.creator"/> : ${recipe.creator.username}</li>
                        <li class="list-group-item" style="color: #ffcd30; background-color: #2b2b2b;"><@spring.message "main.rate"/> : ${recipe.rate}</li>
                        <li class="list-group-item" style="color: #ffcd30; background-color: #2b2b2b;"><@spring.message "main.category"/>
                            : ${recipe.recipeCategory.name()}</li>
                        <li class="list-group-item" style="color: #ffcd30; background-color: #2b2b2b;"><@spring.message "recipe.time"/> : ${recipe.time}</li>
                        <#--                        <li class="list-group-item">-->
                        <#--                            <#list recipe.ingredients as ingredient>-->
                        <#--                                <p>${ingredient.product.name} - ${ingredient.weight} <@spring.message "main.gram"/></p>-->
                        <#--                            </#list>-->
                        <#--                        </li>-->
                    </ul>
                </div>
                <div class="card-footer" style="background-color: #000000;">
                    <p><b style="color: #ffcd30;"><@spring.message "main.total"/>
                            : ${recipe.totalEnergy} <@spring.message "recipe.cal"/></b></p>
                    <br/>
                    <a style="background-color: transparent; color: #ffcd30"
                       href="/rateRecipe/${recipe.id}"><@spring.message "main.more"/></a>
                </div>
            </div>
        </#list>
    </div>
</@c.page>