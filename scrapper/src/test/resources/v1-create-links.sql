create table links
(
    id                    bigint generated always as identity,
    name                  varchar(200)             not null unique,
    last_update           timestamp with time zone not null,
    last_check_for_update timestamp with time zone,
    primary key (id)
);

