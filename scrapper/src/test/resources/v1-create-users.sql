create table users
(
    id         bigint generated always as identity,
    name       text                     not null,
    primary key (id)
)
