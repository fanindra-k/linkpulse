Alter table bookmarks
Add column user_id BIGINT REFERENCES auth_user(id);

create index idx_bookmarks_user_id on bookmarks(user_id);