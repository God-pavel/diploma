<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p>
<#include "parts/security.ftl">
<@c.page>
    <h3><@spring.message "page.recommendations"/></h3>
    <@p.pager url page/>

    <div class="card-columns">
        <#list page.content as recipe>
            <div class="card my-3" style="width: 18rem;">
                <b>${recipe.name}</b>
                <p class="m-2">
                <p><@spring.message "main.creator"/> : ${recipe.creator.username}</p>
                <p><@spring.message "main.rate"/> : ${recipe.rate}</p>
                <p><@spring.message "main.category"/> : ${recipe.recipeCategory.name()}</p>
                <p><@spring.message "recipe.time"/> : ${recipe.time}</p>
                <#list recipe.ingredients as ingredient>
                    <p>${ingredient.product.name} - ${ingredient.weight} <@spring.message "main.gram"/></p>
                </#list>

                <div class="card-footer" style="background-color: whitesmoke;">
                    <p><b style="float: left;"><@spring.message "main.total"/>: ${recipe.totalEnergy} <@spring.message "recipe.cal"/></b></p>
                    <br/>
                    <a style="background-color: transparent;" href="/rateRecipe/${recipe.id}"><@spring.message "main.more"/></a>
                </div>
            </div>
        </#list>
    </div>

</@c.page>