create table users
(
    id    bigint generated always as identity,
    chat_id bigint not null,
    primary key (id)
)
