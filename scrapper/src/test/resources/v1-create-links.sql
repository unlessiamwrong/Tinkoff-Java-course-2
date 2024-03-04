create table links
(
    id         bigint generated always as identity,
    name       text                     not null,
    value      text                     not null,
    created_at timestamp with time zone not null,
    created_by text                     not null,
    primary key (id)
)

