CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created  TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
    updated  TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
    version  INTEGER      NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS user_detail
(
    user_id   INTEGER      NOT NULL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL,
    phone     VARCHAR(100) NOT NULL,
    address   VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS post
(
    id      SERIAL PRIMARY KEY,
    title   VARCHAR(100) NOT NULL,
    content TEXT         NOT NULL,
    user_id INTEGER      NOT NULL,
    created TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
    version INTEGER      NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS post_comment
(
    id                SERIAL PRIMARY KEY,
    content           TEXT      NOT NULL,
    post_id           INTEGER   NOT NULL,
    user_id           INTEGER   NOT NULL,
    parent_comment_id INTEGER   NULL     DEFAULT NULL,
    created           TIMESTAMP NULL     DEFAULT CURRENT_TIMESTAMP,
    updated           TIMESTAMP NULL     DEFAULT CURRENT_TIMESTAMP,
    version           INTEGER   NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (parent_comment_id) REFERENCES post_comment (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100)   NOT NULL,
    description  VARCHAR(100)   NOT NULL,
    product_code VARCHAR(100)   NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    created      TIMESTAMP      NULL     DEFAULT CURRENT_TIMESTAMP,
    updated      TIMESTAMP      NULL     DEFAULT CURRENT_TIMESTAMP,
    version      INTEGER        NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS category
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    product_code VARCHAR(100) NOT NULL
);

START TRANSACTION;

INSERT INTO product (name, description, product_code, price)
VALUES ('Product 1', 'Description 1', 'P1', 100.00),
       ('Product 2', 'Description 2', 'P2', 200.00),
       ('Product 3', 'Description 3', 'P3', 300.00),
       ('Product 4', 'Description 4', 'P4', 400.00),
       ('Product 5', 'Description 5', 'P5', 500.00)
;

INSERT INTO category (name, product_code)
VALUES ('Category 1', 'P1'),
       ('Category 2', 'P1'),
       ('Category 3', 'P1'),
       ('Category 4', 'P2'),
       ('Category 5', 'P2');

-- CREATE SOME DUMMY POSTS WITH COMMENTS AND CHILD COMMENTS
insert into users (id, username, password)
values  (1, 'samuel', '$2a$10$DKjW7kSpjFePIkkIIZzOTuUgxVp0xkhPtfzJctRebEnMc3sRxsOLu'),
        (2, 'samuel', '$2a$10$mgYNRpnTPSY.79QEXrlIF.pJmGdct6Ifxh9mh/dSavxWAMdeomPLK'),
        (3, 'samuel', '$2a$10$9R8JXMnDYh/n./xALJaqa.EiI16/0LefhrY/6o/WiyKIrUkYtXk.S'),
        (4, 'samuel', '$2a$10$8AUKYhcAm5EOq/ReqHBUc.SY8WPpiEemjJlZBKhmxd.4onNkFUp06'),
        (5, 'samuel', '$2a$10$dffSnbV3B.yPp7LlwsXGVe.5VG4M4LBW3eSBjHDiKHOgZOrJpBMnW');

INSERT INTO post (id, title, content, user_id)
VALUES (1, 'Post 1', 'Content 1', 1),
       (2, 'Post 2', 'Content 2', 1),
       (3, 'Post 3', 'Content 3', 2),
       (4, 'Post 4', 'Content 4', 2),
       (5, 'Post 5', 'Content 5', 3);

INSERT INTO post_comment (id, content, post_id, user_id)
VALUES (1, 'Comment 1', 1, 1),
       (2, 'Comment 2', 1, 2),
       (3, 'Comment 3', 1, 3),
       (4, 'Comment 4', 1, 1),
       (5, 'Comment 5', 1, 2),
       (6, 'Comment 6', 1, 3),
       (7, 'Comment 7', 1, 1),
       (8, 'Comment 8', 1, 2),
       (9, 'Comment 9', 1, 3),
       (10, 'Comment 10', 1, 1),
       (11, 'Comment 11', 1, 2),
       (12, 'Comment 12', 1, 3),
       (13, 'Comment 13', 1, 1),
       (14, 'Comment 14', 1, 2),
       (15, 'Comment 15', 1, 3),
       (16, 'Comment 16', 1, 1),
       (17, 'Comment 17', 1, 2),
       (18, 'Comment 18', 1, 3),
       (19, 'Comment 19', 1, 1),
       (20, 'Comment 20', 1, 2),
       (21, 'Comment 21', 1, 3),
       (22, 'Comment 22', 1, 1),
       (23, 'Comment 23', 1, 2),
       (24, 'Comment 24', 1, 3),
       (25, 'Comment 25', 1, 1),
       (26, 'Comment 26', 1, 2),
       (27, 'Comment 27', 1, 3),
       (28, 'Comment 28', 1, 1),
       (29, 'Comment 29', 1, 2),
       (30, 'Comment 30', 1, 3),
       (31, 'Comment 31', 1, 1);

DO
$$
    DECLARE
        max_id integer;
    BEGIN
        SELECT max(id) INTO max_id FROM users;
        PERFORM setval('users_id_seq', max_id, true);

        SELECT max(id) INTO max_id FROM post;
        PERFORM setval('post_id_seq', max_id, true);

        SELECT max(id) INTO max_id FROM post_comment;
        PERFORM setval('post_comment_id_seq', max_id, true);
    END
$$;

INSERT INTO post_comment (content, post_id, user_id, parent_comment_id)
VALUES ('Child Comment 1', 1, 1, 1),
       ('Child Comment 1', 1, 1, 1),
       ('Child Comment 2', 1, 2, 1),
       ('Child Comment 3', 1, 3, 1),
       ('Child Comment 4', 1, 1, 1),
       ('Child Comment 5', 1, 2, 1),
       ('Child Comment 6', 1, 3, 1),
       ('Child Comment 7', 1, 1, 1),
       ('Child Comment 8', 1, 2, 1),
       ('Child Comment 9', 1, 3, 1),
       ('Child Comment 10', 1, 1, 2),
       ('Child Comment 11', 1, 2, 2),
       ('Child Comment 12', 1, 3, 2),
       ('Child Comment 13', 1, 1, 2),
       ('Child Comment 14', 1, 2, 1),
       ('Child Comment 15', 1, 3, 1),
       ('Child Comment 16', 1, 1, 1),
       ('Child Comment 17', 1, 2, 1),
       ('Child Comment 18', 1, 3, 3),
       ('Child Comment 19', 1, 1, 3),
       ('Child Comment 20', 1, 2, 3),
       ('Child Comment 21', 1, 3, 3),
       ('Child Comment 22', 1, 1, 1),
       ('Child Comment 23', 1, 2, 1),
       ('Child Comment 24', 1, 3, 1),
       ('Child Comment 25', 1, 1, 1),
       ('Child Comment 26', 1, 2, 1),
       ('Child Comment 27', 1, 3, 1),
       ('Child Comment 28', 1, 1, 1),
       ('Child Comment 29', 1, 2, 1),
       ('Child Comment 30', 1, 3, 30),
       ('Child Comment 31', 1, 1, 30);

COMMIT;
