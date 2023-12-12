SELECT post.post_id
FROM post
WHERE (SELECT COUNT(*) FROM
                            (SELECT c.post_id FROM post LEFT OUTER JOIN public.comment c on post.post_id = c.post_id) AS p2
                        WHERE post.post_id = p2.post_id) = 2
    AND LEFT(title, 1) ~ '^[0-9]+$' AND
    LENGTH(content) > 20
