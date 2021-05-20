<#import "/spring.ftl" as spring/>
<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><@spring.message "navbar.name"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/main"><@spring.message "navbar.main"/></a>
            </li>
            <#if name != "unknown">
                <li class="nav-item">
                    <a class="nav-link" href="/recommendation"><@spring.message "navbar.recommendation"/></a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users"><@spring.message "navbar.users"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/products"><@spring.message "navbar.products"/></a>
                </li>
            </#if>
            <#if name == "unknown">
                <li class="nav-item">
                    <a class="nav-link" href="/registration"><@spring.message "navbar.registration"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/login"><@spring.message "navbar.login"/></a>
                </li>
            </#if>
            <#if name != "unknown">
                <li class="nav-item">
                    <a class="nav-link" href="/profile/${curUser.id}"><@spring.message "navbar.profile"/></a>
                </li>
            </#if>
        </ul>


        <span style="float: right">
                        <a href="?lang=en"><@spring.message "language.en"/></a>
                        |
                        <a href="?lang=ua"><@spring.message "language.ua"/></a>
        </span>
        <div class="navbar-text mr-3 ml-3">${name}</div>
        <div>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <#if name != "unknown">
                    <button class="btn btn-primary" type="submit"><@spring.message "logout"/></button>
                </#if>
            </form>
        </div>
    </div>
</nav>