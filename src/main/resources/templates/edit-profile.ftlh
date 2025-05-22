<#import "layout.ftlh" as layout>
<@layout.layout title="Edit Profile - ${user.username}">
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()>
    </#if>
    <div class="container-fluid px-0 bg-dark text-white min-vh-100 d-flex">
        <div class="container py-4">
            <h2 class="mb-4">Edit Profile</h2>

            <#if error??>
                <div class="alert alert-danger">${error}</div>
            </#if>

            <div class="mb-4">
                <h5>Change Avatar</h5>
                <form action="/user/${user.username}/avatar" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="mb-3">
                        <label for="file" class="form-label">Select Image</label>
                        <input type="file" class="form-control" id="file" name="file" accept="image/*">
                    </div>
                    <button type="submit" class="btn btn-primary" style="background-color: #0095f6; border: none;">Upload Avatar</button>
                </form>
            </div>

            <div class="mb-4">
                <h5>Edit Bio</h5>
                <form action="/user/edit" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="mb-3">
                        <label for="bio" class="form-label">Bio</label>
                        <textarea class="form-control bg-dark text-white" id="bio" name="bio" rows="3" maxlength="255">${user.bio!''}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" style="background-color: #0095f6; border: none;">Save Bio</button>
                </form>
            </div>
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