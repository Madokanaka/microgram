<#import "../layout.ftlh" as layout>
<@layout.layout title="Feed">
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()>
    </#if>
    <div class="container-fluid px-0 bg-dark text-white min-vh-100" style="background-color: #000; color: #fff;">
        <div class="container py-4">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10">
                    <h2 class="mb-4" style="color: #fff;">Feed</h2>

                    <#if posts?has_content>
                        <#list posts as post>
                            <div class="post-container mb-4 bg-dark text-white p-3" style="border-radius: 10px; background-color: #1a1a1a; border: 1px solid #333;">
                                <div class="d-flex align-items-center mb-3">
                                    <img src="<#if post.avatar?? && post.avatar != ''>/api/images/${post.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>"
                                         alt="${post.username}'s avatar"
                                         class="rounded-circle me-2"
                                         style="width: 40px; height: 40px; object-fit: cover; border: 1px solid #e6e6e6;">
                                    <a href="/user/${post.username}" class="text-white text-decoration-none" style="color: #fff;">${post.username}</a>
                                    <#if known && name == post.username>
                                        <form action="/post/${post.id}/delete" method="post" class="ms-auto">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button type="submit" class="btn btn-outline-danger btn-sm" style="border: none; color: #dc3545;">
                                                <i class="bi bi-trash"></i>
                                            </button>
                                        </form>
                                    </#if>
                                </div>

                                <!-- Изображение поста -->
                                <div class="post-image mb-3">
                                    <img src="/api/posts/${post.imageUrl}" alt="Post image" class="w-100" style="border-radius: 8px; max-height: 500px; object-fit: cover; background-color: #000;">
                                </div>

                                <p class="mb-2" style="color: #fff;">${post.description}</p>

                                <div class="d-flex justify-content-between mb-3">
                                    <div>
                                        <#if known>
                                            <form action="/post/${post.id}/like" method="post" class="d-inline">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <button type="submit" class="btn btn-outline-secondary btn-sm me-2" style="border: none; color: #fff;">
                                                    <i class="bi bi-heart<#if hasLiked?? && hasLiked>-fill text-danger</#if>"></i> ${post.likesCount}
                                                </button>
                                            </form>
                                        <#else>
                                            <span style="color: #fff;"><i class="bi bi-heart me-1"></i> ${post.likesCount}</span>
                                        </#if>
                                        <span style="color: #fff;"><i class="bi bi-chat me-1"></i> ${post.commentsCount}</span>
                                    </div>
                                    <a href="/post/${post.id}" class="btn btn-outline-secondary btn-sm" style="border: none; color: #fff;">
                                        <i class="bi bi-chat"></i> View
                                    </a>
                                </div>

                                <div class="comments-list mb-3" style="max-height: 200px; overflow-y: auto;">
                                    <#if post.comments??>
                                        <#list post.comments as comment>
                                            <div class="d-flex align-items-start mb-2 justify-content-between">
                                                <div class="d-flex align-items-start">
                                                    <img src="<#if comment.avatar?? && comment.avatar != ''>/api/images/${comment.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>"
                                                         alt="${comment.username}'s avatar"
                                                         class="rounded-circle me-2"
                                                         style="width: 28px; height: 28px; object-fit: cover;">
                                                    <div>
                                                        <a href="/user/${comment.username}" class="text-white text-decoration-none" style="color: #fff;">
                                                            <strong>${comment.username}</strong>
                                                        </a>
                                                        <p class="mb-0" style="color: #fff;">${comment.text}</p>
                                                        <small class="text-muted" style="color: #8e8e8e;">${comment.createdAt.toLocalDate()}</small>
                                                    </div>
                                                </div>
                                                <#if known && name == post.username>
                                                    <form action="/post/comment/${comment.id}/delete" method="post">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        <button type="submit" class="btn btn-outline-danger btn-sm" style="border: none; color: #dc3545;">
                                                            <i class="bi bi-trash"></i>
                                                        </button>
                                                    </form>
                                                </#if>
                                            </div>
                                        </#list>
                                    </#if>
                                </div>

                                <#if known>
                                    <div class="add-comment">
                                        <form action="/post/${post.id}/comment" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="comment" placeholder="Add a comment..." required style="background-color: #000; color: #fff; border-color: #e6e6e6;">
                                                <button type="submit" class="btn btn-primary">
                                                    <i class="bi bi-arrow-right"></i>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </#if>
                            </div>
                        </#list>
                    <#else>
                        <p class="text-muted text-center" style="color: #8e8e8e;">No posts to display. Follow some users to see their posts!</p>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <style>
        .post-container {
            background-color: #1a1a1a !important;
            border: 1px solid #333 !important;
        }

        .form-control {
            background-color: #000 !important;
            color: #fff !important;
            border-color: #e6e6e6 !important;
        }

        .form-control:focus {
            background-color: #000 !important;
            color: #fff !important;
            border-color: #e6e6e6 !important;
            box-shadow: none !important;
        }

        .btn-outline-secondary {
            border: none !important;
        }

        .text-muted {
            color: #8e8e8e !important;
        }

        .post-image {
            background-color: #000;
        }

        p {
            word-wrap: break-word;
            overflow-wrap: break-word;
            color: #fff !important;
        }

        a {
            color: #fff !important;
        }

        a:hover {
            color: #e6e6e6 !important;
        }

        .btn-link {
            color: #0095f6 !important;
        }

        .btn-link:hover {
            color: #007bff !important;
        }
    </style>
</@layout.layout>