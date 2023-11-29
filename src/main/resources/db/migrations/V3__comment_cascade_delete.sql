ALTER TABLE comment
    DROP CONSTRAINT "comment_articleId_fkey";

ALTER TABLE comment
    ADD FOREIGN KEY ("articleId") REFERENCES article
        ON DELETE CASCADE;
