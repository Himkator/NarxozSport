<#import "temp.ftlh" as t>
<@t.templ user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <form action="/login" method="post">
        <div class="form-group">
            <label for="email">Электронная почта</label>
            <input type="text" class="form-control" id="text" name="username">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" name="password">

        </div>
        <button type="submit" style="width: 100%" class="btn btn-dark" onclick="window.location.href = '/profile'">Войти</button>
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
    <hr>
    <h2>Нет аккаунта BUYSELL? <a href="/registration">Зарегистрируйся!</a></h2>
</@t.templ>