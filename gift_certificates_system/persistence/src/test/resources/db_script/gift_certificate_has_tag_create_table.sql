create table gift_certificate_has_tag
(
    gift_certificate_id_fk bigint not null,
    tag_id_fk              bigint not null,
    primary key (gift_certificate_id_fk, tag_id_fk),
    constraint fk_gift_certificate_has_tag_gift_certificate
        foreign key (gift_certificate_id_fk) references gift_certificate (id),
    constraint fk_gift_certificate_has_tag_tag1
        foreign key (tag_id_fk) references tag (id)
);

create index fk_gift_certificate_has_tag_gift_certificate_idx
    on gift_certificate_has_tag (gift_certificate_id_fk);

create index fk_gift_certificate_has_tag_tag1_idx
    on gift_certificate_has_tag (tag_id_fk);

