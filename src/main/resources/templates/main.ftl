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
            <form action="/main/fastFindRecipe" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit"><@spring.message "main.fastFind"/></button>
            </form>
        </div>
        <div class="col-sm-2">
            <form action="/main/sort" method="get">
                <div>
                    <label><input type="radio" name="sort" required value="rate">rate</label>
                </div>
                <div>
                    <label><input type="radio" name="sort" required value="time">time</label>
                </div>
                <button class="btn btn-primary" type="submit"><@spring.message "main.sort"/></button>
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
                    <p><b style="float: left;"><@spring.message "main.total"/>
                            : ${recipe.totalEnergy} <@spring.message "recipe.cal"/></b></p>
                    <br/>
                    <a style="background-color: transparent;"
                       href="/rateRecipe/${recipe.id}"><@spring.message "main.more"/></a>
                </div>
            </div>
        </#list>
    </div>

</@c.page>