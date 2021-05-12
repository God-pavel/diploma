<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">

<@c.page>
    <h3><@spring.message "page.recipe"/></h3>
    <#if message != "">
        <div class="alert alert-danger" role="alert">${message}</div>
    </#if>

    <div>
        <form action="/createRecipe/addIngredient/${recipe.id}" method="post">
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

    <form action="/createRecipe/${recipe.id}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><@spring.message "recipe.name"/> : </label>
            <div class="col-sm-4">
                <input type="text" class="form-control" name="name" require/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><@spring.message "recipe.time"/> : </label>
            <div class="col-sm-4">
                <input type="number" class="form-control" name="time" required/>
            </div>
        </div>
        <label class="col-sm-2 col-form-label"><@spring.message "recipe.text"/> : </label>
        <p><textarea name="text" rows="4" cols="70" required> </textarea></p>

        <p><@spring.message "main.creator"/> : ${recipe.creator.username}</p>
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
        <p><@spring.message "main.total"/> : ${recipe.totalEnergy}</p>
        <p><@spring.message "main.rate"/> : ${recipe.rate}</p>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "criteria.category"/></label>
            <div class="col-sm-4">
                <#list categories as category>
                    <div>
                        <label><input type="radio" name="category" required value=${category}>${category}</label>
                    </div>
                </#list>
            </div>
        </div>
        <input type="hidden" name="recipeId" value="${recipe.id}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary m-3" type="submit"><@spring.message "recipe.close"/></button>
    </form>
</@c.page>