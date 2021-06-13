<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftl">

<@c.page>
    <h3 style="color: #ffcd30;"><@spring.message "page.recipe"/></h3>
    <#if error??>
        <div class="alert alert-danger" role="alert"><@spring.message "recipe.error.noing"/></div>
    </#if>

    <div>
        <form action="/createRecipe/addIngredient/${recipe.id}" method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" style="color: #ffcd30;"><@spring.message "recipe.productName"/> : </label>
                <div class="col-sm-2">
                    <select style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" name="productName">
                        <#list products as product >
                            <div class="col-sm-5">
                                <option style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" value="${product.name}">${product.name}</option>
                            </div>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label" style="color: #ffcd30;"><@spring.message "recipe.weight"/> : </label>
                <div class="col-sm-3">
                    <input type="number" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" step="any" class="form-control" name="weight" required/>
                </div>
            </div>
            <input type="hidden" name="recipeId" value="${recipe.id}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn btn-outline-warning btn-sm"
                    type="submit"><@spring.message "recipe.add"/></button>
        </form>
    </div>

    <form action="/createRecipe/${recipe.id}" enctype="multipart/form-data" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"><@spring.message "recipe.name"/> : </label>
            <div class="col-sm-4">
                <input type="text" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" class="form-control" name="name" required/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"><@spring.message "recipe.time"/> : </label>
            <div class="col-sm-4">
                <input type="number" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" class="form-control" name="time" required/>
            </div>
        </div>
        <#if recipe.ingredients??>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "recipe.ingredients"/> : </label>
                <div class="col-sm-4">
                    <#list recipe.ingredients as ingredient>
                        <p style="color: #ffcd30;">${ingredient.product.name} ${ingredient.weight} <@spring.message "main.gram"/></p>
                    </#list>
                </div>
            </div>
        </#if>
        <label class="col-sm-2 col-form-label" style="color: #ffcd30;"><@spring.message "recipe.text"/> : </label>
        <p><textarea style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" name="text" rows="4" cols="70" required> </textarea></p>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" style="color: #ffcd30;"> <@spring.message "criteria.category"/></label>
            <div class="col-sm-4">
                <#list categories as category>
                    <div>
                        <label style="color: #ffcd30;"><input type="radio" class="form-check-input" name="category" required value=${category}>${category}</label>
                    </div>
                </#list>
            </div>
        </div>
        <div>
            <label style="color: #ffcd30;"><@spring.message "users.photo"/>: </label>
            <input type="file" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" name="image" accept="image/png, image/jpeg"/>
        </div>
        <div>
            <label style="color: #ffcd30;"><@spring.message "recipe.video"/>: </label>
            <input type="file" style="color: #ffcd30; background-color: #393d3f; border-color: #ffcd30;" name="video" accept="video/mp4"/>
        </div>
        <input type="hidden" name="recipeId" value="${recipe.id}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn btn-outline-warning" type="submit"><@spring.message "recipe.close"/></button>
    </form>
</@c.page>