CREATE TABLE gift_certificate (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR (100)   NOT NULL,
    description      VARCHAR (500)  NOT NULL,
    price            DECIMAL (10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      DATETIME       NOT NULL,
    last_update_date DATETIME       NOT NULL
) ENGINE = InnoDB;

CREATE TABLE tag (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (100) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE gift_certificate_has_tag (
    gift_certificate_id_fk BIGINT NOT NULL,
    tag_id_fk              BIGINT NOT NULL,
    PRIMARY KEY (gift_certificate_id_fk, tag_id_fk)
) ENGINE = InnoDB;

CREATE TABLE user (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR (50) UNIQUE
) ENGINE = InnoDB;

CREATE TABLE gift_certificate_order (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id_fk              BIGINT NOT NULL,
    gift_certificate_id_fk  BIGINT NOT NULL,
    cost                    DECIMAL (10, 2) NOT NULL,
    purchase_date           DATETIME       NOT NULL
) ENGINE = InnoDB;






