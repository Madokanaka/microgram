INSERT INTO users (username, email, password, name, created_at) VALUES
('asdf', '1@ma.ru', '$2y$10$b./SBOXQhI0QunD4LIiirOs.1qUxQEEN2W22EC23fHZV33s6qiVQS', 'Asdf','2025-05-23 13:54:18.728529'),
('john_doe', 'john.doe@example.com', '$2y$10$b./SBOXQhI0QunD4LIiirOs.1qUxQEEN2W22EC23fHZV33s6qiVQS', 'John Doe','2025-05-23 14:00:00.123456'),
('jane_smith', 'jane.smith@example.com', '$2y$10$b./SBOXQhI0QunD4LIiirOs.1qUxQEEN2W22EC23fHZV33s6qiVQS', 'Jane Smith','2025-05-23 14:05:00.654321'),
('bob_jones', 'bob.jones@example.com', '$2y$10$b./SBOXQhI0QunD4LIiirOs.1qUxQEEN2W22EC23fHZV33s6qiVQS', 'Bob Jones','2025-05-23 14:10:00.987654'),
('alice_brown', 'alice.brown@example.com', '$2y$10$b./SBOXQhI0QunD4LIiirOs.1qUxQEEN2W22EC23fHZV33s6qiVQS', 'Alice Brown','2025-05-23 14:15:00.456789');

INSERT INTO usr_roles (usr_id, role_id)
VALUES
    (
        (SELECT id FROM users WHERE email = '1@ma.ru'),
        (SELECT id FROM roles WHERE role = 'USER')
    ),
    (
        (SELECT id FROM users WHERE email = 'john.doe@example.com'),
        (SELECT id FROM roles WHERE role = 'USER')
    ),
    (
        (SELECT id FROM users WHERE email = 'jane.smith@example.com'),
        (SELECT id FROM roles WHERE role = 'USER')
    ),
    (
        (SELECT id FROM users WHERE email = 'bob.jones@example.com'),
        (SELECT id FROM roles WHERE role = 'USER')
    ),
    (
        (SELECT id FROM users WHERE email = 'alice.brown@example.com'),
        (SELECT id FROM roles WHERE role = 'USER')
    );