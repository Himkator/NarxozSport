<#macro templ user>
    <#if user.email??>
        <div class="dropdown text-end">
            asas
        </div>
    <#else>
        <button class="btn btn-light my-2 my-sm-0" type="button"
                onclick="window.location.href = '/login'">
            Войти
        </button>
    </#if>
    <#nested/>
</#macro>