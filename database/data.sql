INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date, deleted)
VALUES ('rest in hotel', 'rest in good place', 150.45, 4, '2021-01-01 10:00:00', '2021-01-02 10:00:00', 0);
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date, deleted)
VALUES ('snowboarding', 'snowboard extreme', 250, 12, '2020-12-02 10:00:00', '2021-01-02 10:00:00', 0);
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date, deleted)
VALUES ('dinner in cafe', 'New Year dinner or breakfast', 50.99, 10, '2020-12-31 23:59:00','2021-12-31 23:59:59', 0);
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date, deleted)
VALUES ('sport tourism', 'Forest live', 399.99, 20, '2021-01-03 09:59:00', '2021-01-05 12:00:00', 0);
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date, deleted)
VALUES ('massage', 'massage in Minsk', 75.5, 36, '2021-01-03 09:59:00', '2021-01-05 12:00:00', 0);

INSERT INTO tag(name)
VALUES ('rest');
INSERT INTO tag(name)
VALUES ('extreme');
INSERT INTO tag(name)
VALUES ('food');
INSERT INTO tag(name)
VALUES ('sport');
INSERT INTO tag(name)
VALUES ('romantic');

INSERT INTO gift_certificate_has_tag VALUES (1, 1);
INSERT INTO gift_certificate_has_tag VALUES (1, 3);
INSERT INTO gift_certificate_has_tag VALUES (3, 4);
INSERT INTO gift_certificate_has_tag VALUES (4, 4);
INSERT INTO gift_certificate_has_tag VALUES (5, 1);

INSERT INTO user(username, first_name, last_name)
VALUES ('shitikov.egor@gmail.com', 'Egor', 'Shitikov');
INSERT INTO user(username, first_name, last_name)
VALUES ('user_1@epam.com', 'Name', 'Surname');
INSERT INTO user(username, first_name, last_name)
VALUES ('name_surname@tut.by', 'Ivan', 'Ivanov');

INSERT INTO gift_certificate_order(user_id_fk, gift_certificate_id_fk, cost, purchase_date)
VALUES(1, 1, 150.45, '2021-02-01 10:00:00');
INSERT INTO gift_certificate_order(user_id_fk, gift_certificate_id_fk, cost, purchase_date)
VALUES(1, 5, 250, '2021-02-03 10:00:00');
INSERT INTO gift_certificate_order(user_id_fk, gift_certificate_id_fk, cost, purchase_date)
VALUES(2, 3, 50.99, '2021-01-30 10:00:00');
INSERT INTO gift_certificate_order(user_id_fk, gift_certificate_id_fk, cost, purchase_date)
VALUES(3, 4, 399.99, '2021-02-02 10:00:00');
INSERT INTO gift_certificate_order(user_id_fk, gift_certificate_id_fk, cost, purchase_date)
VALUES(3, 2, 250, '2021-01-31 15:00:00');

