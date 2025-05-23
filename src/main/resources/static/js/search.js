document.addEventListener('DOMContentLoaded', function () {
    const searchQuery = document.getElementById('searchQuery');
    const searchResults = document.getElementById('searchResults');

    function formatNumber(num) {
        if (num >= 1000000) {
            return (num / 1000000).toFixed(1) + ' млн';
        }
        if (num >= 1000) {
            return (num / 1000).toFixed(1) + ' тыс';
        }
        return num.toString();
    }

    function performSearch(query) {
        fetch(`/api/search?query=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                searchResults.innerHTML = '';

                if (data.length > 0) {
                    const heading = document.createElement('h4');
                    heading.className = 'mb-3';
                    heading.textContent = `Results for "${query}"`;
                    searchResults.appendChild(heading);

                    const list = document.createElement('div');
                    list.className = 'list-group';

                    data.forEach(user => {
                        const item = document.createElement('a');
                        item.href = `/user/${user.username}`;
                        item.className = 'list-group-item bg-dark text-white border-secondary p-3 d-flex align-items-center text-decoration-none';

                        const avatar = document.createElement('img');
                        if (user.avatar && user.avatar.trim() !== '') {
                            avatar.src = `/api/images/${user.avatar}`;
                        } else {
                            avatar.src = 'https://www.shutterstock.com/image-vector/vector-flat-illustration-grayscale-avatar-600nw-2281862025.jpg';
                        }                        avatar.className = 'rounded-circle me-3';
                        avatar.style.width = '44px';
                        avatar.style.height = '44px';
                        avatar.style.objectFit = 'cover';
                        avatar.alt = 'Profile picture';

                        const userInfo = document.createElement('div');
                        userInfo.className = 'flex-grow-1';

                        const username = document.createElement('div');
                        username.className = 'd-flex align-items-center';

                        const usernameSpan = document.createElement('span');
                        usernameSpan.className = 'fw-bold me-2';
                        usernameSpan.textContent = user.username;

                        const nameSpan = document.createElement('span');
                        nameSpan.className = 'text-muted';
                        nameSpan.textContent = user.name ? '• ' + user.name : '';

                        username.appendChild(usernameSpan);
                        username.appendChild(nameSpan);

                        const stats = document.createElement('div');
                        stats.className = 'small text-muted';

                        if (user.followersCount > 0) {
                            const followers = document.createElement('div');
                            followers.textContent = `Подписчики: ${formatNumber(user.followersCount)}`;
                            stats.appendChild(followers);
                        }

                        if (user.followingCount > 0) {
                            const following = document.createElement('div');
                            following.textContent = `Подписаны: ${user.followingCount} пользователям`;
                            stats.appendChild(following);
                        }

                        userInfo.appendChild(username);
                        userInfo.appendChild(stats);

                        item.appendChild(avatar);
                        item.appendChild(userInfo);


                        list.appendChild(item);
                    });

                    searchResults.appendChild(list);
                } else {
                    const p = document.createElement('p');
                    p.className = 'text-muted';
                    p.textContent = `No users found for "${query}".`;
                    searchResults.appendChild(p);
                }
            })
            .catch(error => {
                console.error('Error during search:', error);
                searchResults.innerHTML = '<p class="text-danger">An error occurred while searching. Please try again.</p>';
            });
    }

    let debounceTimeout;
    searchQuery.addEventListener('input', function (e) {
        const query = e.target.value.trim();

        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(() => {
            if (query.length > 0) {
                performSearch(query);
            } else {
                searchResults.innerHTML = '<p class="text-muted">Enter a name, username, or email to search.</p>';
            }
        }, 300);
    });
});