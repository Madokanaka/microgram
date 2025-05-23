<#import "../layout.ftlh" as layout>
<@layout.layout title="Post">
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()>
    </#if>
    <div class="row p-0 m-0">
        <div class="col-8 p-0">
            <div class="post-image-container">
                <img src="/api/posts/${post.imageUrl}" alt="Post image" class="w-100"
                     style="max-height: 100vh; object-fit: contain; background-color: #000;">
            </div>
        </div>

        <div class="col-4 p-0">
            <div class="post-details bg-dark text-white h-100 d-flex flex-column">
                <div class="d-flex align-items-center p-3 border-bottom border-secondary">
                    <img src="<#if user.avatar?? && user.avatar != ''>/api/images/${user.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>"
                         alt="${user.username}'s avatar"
                         class="rounded-circle me-2"
                         style="width: 32px; height: 32px; object-fit: cover; border: 1px solid #e6e6e6;">
                    <a href="/user/${user.username}" class="text-white text-decoration-none">${user.username}</a>
                    <#if known && name == user.username>
                        <form action="/post/${post.id}/delete" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-outline-danger btn-sm" style="border: none;">
                                <i class="bi bi-trash"></i>
                            </button>
                        </form>
                    </#if>
                </div>

                <div class="p-3 flex-grow-1" style="overflow-y: auto;">
                    <div class="d-flex align-items-start mb-3">
                        <img src="<#if user.avatar?? && user.avatar != ''>/api/images/${user.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>"
                             alt="${user.username}'s avatar"
                             class="rounded-circle me-2" style="width: 32px; height: 32px; object-fit: cover;">
                        <div>
                            <strong class="text-white">${user.username}</strong>
                            <p class="text-white mb-0">${post.description}</p>
                        </div>
                    </div>

                    <div class="comments-list" style="max-height: 500px; overflow-y: auto;">
                        <#list comments as comment>
                            <a href="/user/${comment.username}" class="text-white text-decoration-none">
                                <div class="d-flex align-items-start mb-3 justify-content-between">
                                    <div class="d-flex align-items-start">
                                        <img src="<#if comment.avatar?? && comment.avatar != ''>/api/images/${comment.avatar}<#else>https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg</#if>"
                                             alt="${comment.username}'s avatar"
                                             class="rounded-circle me-2"
                                             style="width: 32px; height: 32px; object-fit: cover;">
                                        <div>
                                            <strong class="text-white">${comment.username}</strong>
                                            <p class="text-white mb-0">${comment.text}</p>
                                            <small class="text-muted">${comment.createdAt.toLocalDate()}</small>
                                        </div>
                                    </div>
                                    <#if known && name == user.username>
                                        <form action="/post/comment/${comment.id}/delete" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                    style="border: none;">
                                                <i class="bi bi-trash"></i>
                                            </button>
                                        </form>
                                    </#if>
                                </div>
                            </a>
                        </#list>
                    </div>

                </div>

                <div class="p-3 border-top border-secondary">
                    <div class="d-flex justify-content-between mb-2">
                        <#if known>
                            <form action="/post/${post.id}/like" method="post" class="d-inline">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-outline-secondary btn-sm me-2"
                                        style="border: none;">
                                    <i class="bi bi-heart<#if hasLiked>-fill text-danger</#if>"> ${post.likesCount}</i>
                                </button>
                            </form>
                        <#else>
                            <span><i class="bi bi-heart me-1"></i> ${post.likesCount}</span>
                        </#if>
                        <span><i class="bi bi-chat me-1"></i> ${post.commentsCount}</span>
                    </div>

                </div>

                <#if known>
                    <div class="p-3 border-top border-secondary">
                        <form action="/post/${post.id}/comment" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="input-group">
                                <input type="text" class="form-control bg-dark text-white" name="comment"
                                       placeholder="Add a comment..." required>
                                <button type="submit" class="btn btn-link text-primary p-0" style="font-weight: 600;">
                                    Post
                                </button>
                            </div>
                        </form>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <style>
        .post-details {
            background-color: #000 !important;
        }

        .form-control {
            border: none;
            background-color: #000 !important;
        }

        .form-control:focus {
            box-shadow: none;
            border: none;
            background-color: #000 !important;
        }

        .btn-outline-secondary {
            border: none !important;
        }

        .text-muted {
            color: #8e8e8e !important;
        }

        .post-image-container {
            background-color: #000;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        p {
            word-wrap: break-word;
            overflow-wrap: break-word;
        }

    </style>
</@layout.layout>