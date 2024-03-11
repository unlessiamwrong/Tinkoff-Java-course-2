create table links
(
    id    bigint generated always as identity,
    name  varchar(200) not null unique,
    last_update     timestamp with time zone not null,
    primary key (id)
);

