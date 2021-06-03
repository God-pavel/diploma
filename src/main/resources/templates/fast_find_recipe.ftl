<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">

<@c.page>
    <h3><@spring.message "page.fastFind"/></h3>
    <#if error??>
        <div class="alert alert-danger" role="alert"><@spring.message "recipe.error.noing"/></div>
    </#if>

    <div>
        <form action="/fastFindRecipe/addIngredient/${recipe.id}" method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"><@spring.message "recipe.productName"/> : </label>
                <div class="col-sm-2">
                    <select name="productName">
                        <#list products as product >
                            <div class="col-sm-5">
                                <option value="${product.name}">${product.name}</option>
                            </div>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label"><@spring.message "recipe.weight"/> : </label>
                <div class="col-sm-3">
                    <input type="number" step="any" class="form-control" name="weight" required/>
                </div>
            </div>
            <input type="hidden" name="recipeId" value="${recipe.id}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-outline-secondary btn-sm"
                    type="submit"><@spring.message "recipe.add"/></button>
        </form>
    </div>

    <form action="/fastFindRecipe/${recipe.id}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><@spring.message "recipe.time"/> : </label>
            <div class="col-sm-4">
                <input type="number" class="form-control" name="time"/>
            </div>
        </div>
        <#if recipe.ingredients??>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> <@spring.message "recipe.ingredients"/> : </label>
                <div class="col-sm-4">
                    <#list recipe.ingredients as ingredient>
                        <p>${ingredient.product.name} ${ingredient.weight} <@spring.message "main.gram"/></p>
                    </#list>
                </div>
            </div>
        </#if>
        <input type="hidden" name="recipeId" value="${recipe.id}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary m-3" type="submit"><@spring.message "main.fastFind"/></button>
    </form>
</@c.page>