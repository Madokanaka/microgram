INSERT INTO posts (user_id, image_url, description, created_at, likes_count, comments_count)
VALUES
(
    (SELECT id FROM users WHERE username = 'asdf'),
    'mexican.jpg',
    'Delicious Mexican cuisine!',
    '2025-05-23 14:06:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'asdf'),
    'steak.jpg',
    'Juicy steak dinner!',
    '2025-05-23 14:07:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'asdf'),
    'french.jpg',
    'Authentic French dish!',
    '2025-05-23 14:08:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'asdf'),
    'vegan.jpg',
    'Healthy vegan meal!',
    '2025-05-23 14:09:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'asdf'),
    'chinese.jpg',
    'Tasty Chinese food!',
    '2025-05-23 14:10:00.000000 +06',
    0,
    0
),

(
    (SELECT id FROM users WHERE username = 'john_doe'),
    'mexican.jpg',
    'Spicy Mexican dish!',
    '2025-05-23 14:11:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'john_doe'),
    'steak.jpg',
    'Grilled steak delight!',
    '2025-05-23 14:12:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'john_doe'),
    'french.jpg',
    'Elegant French meal!',
    '2025-05-23 14:13:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'john_doe'),
    'vegan.jpg',
    'Fresh vegan recipe!',
    '2025-05-23 14:14:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'john_doe'),
    'chinese.jpg',
    'Flavorful Chinese cuisine!',
    '2025-05-23 14:15:00.000000 +06',
    0,
    0
),

(
    (SELECT id FROM users WHERE username = 'jane_smith'),
    'mexican.jpg',
    'Tasty Mexican tacos!',
    '2025-05-23 14:16:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'jane_smith'),
    'steak.jpg',
    'Perfect steak night!',
    '2025-05-23 14:17:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'jane_smith'),
    'french.jpg',
    'Classic French dessert!',
    '2025-05-23 14:18:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'jane_smith'),
    'vegan.jpg',
    'Vegan delight!',
    '2025-05-23 14:19:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'jane_smith'),
    'chinese.jpg',
    'Chinese noodle dish!',
    '2025-05-23 14:20:00.000000 +06',
    0,
    0
),

(
    (SELECT id FROM users WHERE username = 'bob_jones'),
    'mexican.jpg',
    'Spicy Mexican salsa!',
    '2025-05-23 14:21:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'bob_jones'),
    'steak.jpg',
    'Tender steak meal!',
    '2025-05-23 14:22:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'bob_jones'),
    'french.jpg',
    'French pastry treat!',
    '2025-05-23 14:23:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'bob_jones'),
    'vegan.jpg',
    'Vegan salad bowl!',
    '2025-05-23 14:24:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'bob_jones'),
    'chinese.jpg',
    'Chinese fried rice!',
    '2025-05-23 14:25:00.000000 +06',
    0,
    0
),

(
    (SELECT id FROM users WHERE username = 'alice_brown'),
    'mexican.jpg',
    'Mexican fiesta food!',
    '2025-05-23 14:26:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'alice_brown'),
    'steak.jpg',
    'Steak with veggies!',
    '2025-05-23 14:27:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'alice_brown'),
    'french.jpg',
    'French wine pairing!',
    '2025-05-23 14:28:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'alice_brown'),
    'vegan.jpg',
    'Vegan smoothie bowl!',
    '2025-05-23 14:29:00.000000 +06',
    0,
    0
),
(
    (SELECT id FROM users WHERE username = 'alice_brown'),
    'chinese.jpg',
    'Chinese dim sum!',
    '2025-05-23 14:30:00.000000 +06',
    0,
    0
);