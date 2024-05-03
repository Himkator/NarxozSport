<#import "temp.ftlh" as t>
<@t.templ user>
    <h1 style="text-align: center">Регистрация</h1>
    <hr>
    <form action="/2/registration" method="post">
        <div class="form-group">
            <label for="email">Электронная почта</label>
            <input type="text" class="form-control" id="email" required name="skFk">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="text" class="form-control" id="password" required name="FIO">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="email" class="form-control" id="password" required name="mail">
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" required name="password">
        </div>
<#--        <input type="hidden" name="_csrf" value="${_csrf.token}">-->
        <button type="submit" style="width: 100%" class="btn btn-dark">Зарегистрироваться</button>
    </form>
</@t.templ>