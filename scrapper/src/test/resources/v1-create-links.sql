create table links
(
    id         bigint generated always as identity,
    name       text                     not null,
    value      text                     not null,
    primary key (id)
)

