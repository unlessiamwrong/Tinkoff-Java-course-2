create table links
(
    id    bigint generated always as identity,
    name  varchar(200) not null,
    value jsonb not null,
    created_at      timestamp with time zone not null,
    primary key (id)
)

