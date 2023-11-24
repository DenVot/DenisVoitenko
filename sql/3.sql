SELECT post_id FROM post
WHERE (SELECT COUNT(*) FROM comment WHERE post_id = post.post_id) <= 1
ORDER BY post_id ASC 
LIMIT 10
