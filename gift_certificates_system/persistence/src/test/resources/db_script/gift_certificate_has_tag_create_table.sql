create table gift_certificate_has_tag
(
    gift_certificate_id_fk bigint not null,
    tag_id_fk              bigint not null,
    primary key (gift_certificate_id_fk, tag_id_fk)
);

