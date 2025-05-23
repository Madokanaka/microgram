<#import "layout.ftlh" as layout>
<@layout.layout title="${user.username} - Profile">
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()>
    </#if>
    <div class="container-fluid px-0 bg-dark text-white min-vh-100 d-flex">
        <div class="container py-4">
            <div class="row align-items-center">
                <div class="col-4 col-md-3 text-center">
                    <img src="<#if user.avatar?? && user.avatar != ''>/api/images/${user.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>" alt="${user.username}'s avatar"
                         class="rounded-circle"
                         style="width: 120px; height: 120px; object-fit: cover; border: 2px solid #e6e6e6;">
                </div>
                <div class="col-8 col-md-9">
                    <div class="d-flex align-items-center mb-2">
                        <h1 class="fs-4 fw-light mb-0 me-3">${user.username}</h1>
                        <#if known && name != user.username>
                            <button class="btn btn-primary btn-sm me-2"
                                    style="background-color: #0095f6; border: none; font-weight: 600;">Подписаться
                            </button>
                        </#if>
                        <#if known && name == user.username>
                            <a href="/user/edit" class="btn btn-outline-secondary btn-sm me-2" style="border-color: #dbdbdb; color: #ffffff; font-weight: 600;">Edit Profile</a>
                        </#if>
                    </div>
                    <div class="d-flex mb-3">
                        <div class="me-4"><strong>${posts?size}</strong> <span class="text-muted">публикаций</span>
                        </div>
                        <div class="me-4"><strong>${user.followersCount}</strong> <span
                                    class="text-muted">подписчиков</span></div>
                        <div><strong>${user.followingCount}</strong> <span class="text-muted">подписок</span></div>
                    </div>
                    <div>
                        <p class="fw-bold mb-1">${user.name!'No name'}</p>
                        <p class="text-muted" style="white-space: pre-wrap;">${user.bio!'No bio available'}</p>
                    </div>
                </div>
            </div>

            <div class="row mt-4 border-top">
                <ul class="nav nav-tabs justify-content-center border-0">
                    <li class="nav-item">
                        <a class="nav-link active border-0 text-center" style="color: #262626; font-size: 0.85rem;"
                           href="#posts"><i class="bi bi-grid-3x3 me-1"></i> ПУБЛИКАЦИИ</a>
                    </li>
                </ul>
            </div>

            <div class="row row-cols-3 g-1 mt-2">
                <#list posts as post>
                    <div class="col">
                        <a href="/post/${post.id}" class="d-block position-relative">
                            <img src="/api/posts/${post.imageUrl}" class="w-100" alt="Post image"
                                 style="aspect-ratio: 1/1; object-fit: cover;">
                            <div class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center text-white bg-black bg-opacity-50 opacity-0 hover-opacity-100"
                                 style="transition: opacity 0.3s;">
                                <span class="me-3"><i class="bi bi-heart me-1"></i> ${post.likesCount}</span>
                                <span><i class="bi bi-chat me-1"></i> ${post.commentsCount}</span>
                            </div>
                        </a>
                    </div>
                </#list>
                <#if !posts?? || posts?size == 0>
                    <div class="col-12 text-center text-muted mt-3">Публикации отсутствуют</div>
                </#if>
            </div>
        </div>
    </div>

    <style>

        .hover-opacity-100 {
            opacity: 0;
        }

        .hover-opacity-100:hover {
            opacity: 1;
        }
    </style>
</@layout.layout>