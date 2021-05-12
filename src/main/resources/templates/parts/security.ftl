<#import "/spring.ftl" as spring/>

<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    curUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = curUser.getUsername()
    isAdmin = curUser.isAdmin()
    isUser = curUser.isUser()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    isUser = false
    >
</#if>