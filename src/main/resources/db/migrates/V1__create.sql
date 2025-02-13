

create table public.worker
(
    id        uuid      not null
        constraint user_pk
            primary key,
    firstname varchar   not null,
    lastname  varchar   not null,
    birthdate timestamp not null,
    email     varchar   not null
        constraint user_email
            unique,
    password  varchar
);

create table public.user_role
(
    id      uuid    not null
        constraint user_role_pk
            primary key,
    name    varchar not null,
    user_id uuid    not null
        constraint user_fk
            references worker
            on update cascade on delete cascade
);

create table public.token
(
    access_token varchar not null,
    user_id      uuid
        constraint user_fk
            references worker
            on update cascade on delete cascade,
    id           uuid    not null
        constraint token_pk
            primary key
);

