<#import "../layout.ftlh" as layout>
<@layout.layout title="Create Post">
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <div class="container-fluid px-0 bg-dark text-white min-vh-100 d-flex">
        <div class="container py-4">
            <h2 class="mb-4">Create New Post</h2>

            <#if error??>
                <div class="alert alert-danger">${error}</div>
            </#if>

            <form action="/post/create" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <label for="image" class="form-label">Upload Image</label>
                    <input type="file" class="form-control" id="image" name="image" accept="image/*" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control bg-dark text-white" id="description" name="description" rows="3" maxlength="255"></textarea>
                </div>
                <button type="submit" class="btn btn-primary" style="background-color: #0095f6; border: none; font-weight: 600;">Create Post</button>
            </form>
        </div>
    </div>

    <style>
        .form-control {
            border-color: #e6e6e6;
        }
        .btn-primary {
            font-weight: 600;
        }
    </style>
</@layout.layout>