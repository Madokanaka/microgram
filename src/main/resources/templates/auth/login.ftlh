<#import "../layout.ftlh" as main>
<@main.layout title="Login">
    <div class="bg-dark text-white text-center min-vh-100 d-flex flex-column justify-content-center">
        <h2 class="mb-4">Instagram</h2>
        <div class="card bg-dark border-0 text-white w-100" style="max-width: 350px; margin: 0 auto;">
            <div class="card-body p-4">
                <#if error??>
                    <div class="alert alert-danger" role="alert">
                        Invalid username or password
                    </div>
                </#if>
                <#if logout??>
                    <div class="alert alert-success" role="alert">
                        You have been logged out
                    </div>
                </#if>
                <form method="post" action="/auth/login">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="mb-3">
                        <input type="text" class="form-control bg-dark text-white border-secondary" id="username" name="username" placeholder="Phone number, username, or email" required>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control bg-dark text-white border-secondary" id="password" name="password" placeholder="Password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 mb-3">Log In</button>
                </form>
                <p class="text-muted small mb-3">Forgot password?</p>
            </div>
        </div>
        <p class="mt-3">Don't have an account? <a href="/auth/register" class="text-primary">Sign up</a></p>
    </div>
</@main.layout>