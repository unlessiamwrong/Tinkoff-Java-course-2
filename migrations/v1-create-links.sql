create table links
(
    id    bigint generated always as identity,
    name  varchar(200) not null unique,
    value varchar(200) not null,
    primary key (id)
);

