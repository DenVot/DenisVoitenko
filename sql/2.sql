-- Результат: 10

SELECT COUNT(*) FROM post
WHERE ((SELECT COUNT(*) FROM comment WHERE post_id = post.post_id) = 2) AND
    LEFT(title, 1) ~ '^[0-9]+$' AND
    LENGTH(content) > 20
