SELECT COUNT(*) FROM (profile LEFT JOIN public.post p on profile.profile_id = p.profile_id) AS joined
WHERE joined.post_id IS NULL
