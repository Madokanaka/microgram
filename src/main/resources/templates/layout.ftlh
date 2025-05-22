<#import "spring.ftl" as spring>
<#macro layout title>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <title>Microgram - ${title}</title>
        <style>
            .vertical-nav {
                min-height: 100vh;
                background-color: #343a40;
                padding-top: 20px;
            }
            .vertical-nav .nav-link {
                color: #fff;
                padding: 10px 20px;
            }
            .vertical-nav .nav-link:hover {
                background-color: #495057;
            }
            .vertical-nav .navbar-brand {
                color: #fff;
                padding: 15px 20px;
                font-size: 1.5rem;
            }
            .error {
                color: #dc3545;
                font-size: 0.9em;
            }
            .success {
                color: #28a745;
                font-size: 0.9em;
            }
        </style>
    </head>
    <body>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()>
    </#if>

    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-3 col-lg-2 vertical-nav">
                <a class="navbar-brand" href="/home">Microgram</a>
                <ul class="nav flex-column">
                    <#if known>
                        <li class="nav-item">
                            <a class="nav-link" href="/home"><i class="bi bi-house me-2"></i>Feed</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/post/create"><i class="bi bi-plus-square me-2"></i>Create Post</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/${name}"><i class="bi bi-person me-2"></i>Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/search"><i class="bi bi-search me-2"></i>Search</a>
                        </li>
                        <li class="nav-item">
                            <form action="/logout" method="post" class="d-inline">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="nav-link btn btn-link text-white"><i class="bi bi-box-arrow-right me-2"></i>Logout</button>
                            </form>
                        </li>
                    <#else>
                        <li class="nav-item">
                            <a class="nav-link" href="/search"><i class="bi bi-search me-2"></i>Search</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/login"><i class="bi bi-box-arrow-in-right me-2"></i>Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/register"><i class="bi bi-person-plus me-2"></i>Register</a>
                        </li>
                    </#if>
                </ul>
            </nav>

            <main class="col-md-9 col-lg-10" style="padding: 0">
                <#nested spring>
            </main>
        </div>
    </div>
    </body>
    </html>
</#macro>