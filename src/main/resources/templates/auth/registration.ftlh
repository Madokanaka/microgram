<#import "../layout.ftlh" as layout>
<@layout.layout title="Register" ; spring>
    <div class="bg-dark text-white text-center min-vh-100 d-flex flex-column justify-content-center">
        <h2 class="mb-4">Instagram</h2>
        <div class="card bg-dark border-0 text-white w-100" style="max-width: 350px; margin: 0 auto;">
            <div class="card-body p-4">
                <#if error??>
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </#if>
                <form method="post" action="/auth/register">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-group mb-3">
                        <label class="form-label" for="email">Email</label>
                        <@spring.formInput "userDto.email" 'id="email" class="form-control bg-dark text-white border-secondary"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label" for="username">Full Name</label>
                        <@spring.formInput "userDto.username" 'id="username" class="form-control bg-dark text-white border-secondary"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label" for="name">Username</label>
                        <@spring.formInput "userDto.name" 'id="name" class="form-control bg-dark text-white border-secondary"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label" for="password">Password</label>
                        <@spring.formInput "userDto.password" 'id="password" class="form-control bg-dark text-white border-secondary" type="password"' />
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <button type="submit" class="btn btn-primary w-100 mb-3">Sign Up</button>
                </form>

                <p class="text-muted small">By signing up, you agree to our Terms, Privacy Policy, and Cookies Policy.</p>
            </div>
        </div>
        <p class="mt-3">Have an account? <a href="/auth/login" class="text-primary">Log in</a></p>
    </div>
</@layout.layout>