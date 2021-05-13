<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">

<@c.page>
    <h3>${recipe.name}</h3>
    <#if message?? && message == "error">
        <div class="alert alert-danger" role="alert"><@spring.message "recipe.error"/></div>
    </#if>
    <p><@spring.message "recipe.time"/> : ${recipe.time}</p>
    <p><@spring.message "main.category"/> : ${recipe.recipeCategory}</p>
    <p><@spring.message "main.creator"/> : ${recipe.creator.username}</p>
    <p><@spring.message "main.rate"/> : ${recipe.rate}</p>
    <p><@spring.message "recipe.text"/> : ${recipe.text}</p>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "recipe.ingredients"/> : </label>
        <div class="col-sm-4">
            <#list recipe.ingredients as ingredient>
                <p>${ingredient.product.name} ${ingredient.weight} <@spring.message "main.gram"/></p>
            </#list>
        </div>
    </div>
    <p><@spring.message "main.total"/> : ${recipe.totalEnergy}</p>

    <#if name != "unknown">
        <form action="/rateRecipe/${recipe.id}" method="post">
            <div>
                <input type="range" step="1" list="tickmarks" name="rate" min="1" max="10" required>
                <datalist id="tickmarks">
                    <option value="1" label="1">
                    <option value="2">
                    <option value="3">
                    <option value="4">
                    <option value="5" label="5">
                    <option value="6">
                    <option value="7">
                    <option value="8">
                    <option value="9">
                    <option value="10" label="10">
                </datalist>
            </div>
            <input type="hidden" name="recipeId" value="${recipe.id}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary m-3" type="submit"><@spring.message "recipe.rate"/></button>
        </form>
    </#if>
</@c.page>