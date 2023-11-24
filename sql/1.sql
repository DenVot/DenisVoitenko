SELECT COUNT(*) FROM
    (SELECT profile_id FROM post
    INTERSECT SELECT profile_id FROM profile) as intersected
