create table user_links
(
    user_id    bigint,
    link_id    bigint,
    foreign key (user_id) references users (id),
    foreign key (link_id) references links (id),
    primary key (user_id, link_id)
)
