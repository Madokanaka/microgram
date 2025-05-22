<#import "layout.ftlh" as layout>
<@layout.layout title="Search">
    <div class="bg-dark text-white min-vh-100 p-4">
        <h2 class="mb-4">Search Users</h2>
        <div class="mb-4">
            <div class="input-group">
                <input type="text" class="form-control bg-dark text-white border-secondary"
                       id="searchQuery"
                       placeholder="Search by name, username, or email"
                       value="${query!''}">
            </div>
        </div>

        <div id="searchResults">
            <p class="text-muted">Enter a name, username, or email to search.</p>
        </div>
    </div>

    <script src="../static/js/search.js"></script>
</@layout.layout>
