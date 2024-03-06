create table user_links
(
    user_id    bigint,
    link_id    bigint,
    created_at timestamp with time zone not null,
    primary key (user_id, link_id)
)
