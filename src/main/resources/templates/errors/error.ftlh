<#import "../layout.ftlh" as layout>
<@layout.layout title="Error">
    <div class="container-fluid px-0 bg-dark text-white min-vh-100" style="background-color: #000; color: #fff;">
        <div class="container py-4">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10">
                    <h2 class="mb-4" style="color: #fff;">Oops! Something Went Wrong</h2>
                    <div class="alert alert-danger" style="background-color: #dc3545; color: #fff; border: none;">
                        <#if message??>
                            ${message}
                        <#else>
                            An unexpected error occurred. Please try again later.
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        .alert-danger {
            background-color: #dc3545 !important;
            color: #fff !important;
            border: none !important;
        }
        .btn-primary {
            font-weight: 600;
        }
        a {
            color: #fff !important;
        }
        a:hover {
            color: #e6e6e6 !important;
        }
    </style>
</@layout.layout>