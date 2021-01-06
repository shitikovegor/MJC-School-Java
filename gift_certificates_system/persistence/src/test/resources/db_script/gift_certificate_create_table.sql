create table gift_certificate
(
    id               bigint auto_increment
        primary key,
    name             varchar(100)   not null,
    description      varchar(1000)  null,
    price            decimal(10, 2) not null,
    duration         int            not null,
    create_date      datetime       not null,
    last_update_date datetime       not null
);

