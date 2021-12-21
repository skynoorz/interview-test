INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('SUPERVISOR');
INSERT INTO users (username, password, birthday, joining, balance) VALUES ('admin', 'admin', '1994-01-12', '2009-04-26', 1000);
INSERT INTO users (username, password, birthday, joining, balance) VALUES ('ron', '$2a$12$U8EIsOY1VOx2Q1nfZXVt/u.cDXGndC8We2CSZZuXkIj793zOj1HRK', '1995-03-18', '2021-12-20', 400);
INSERT INTO user_authorities (user_id, rol_id) VALUES (1,1);
INSERT INTO user_authorities (user_id, rol_id) VALUES (1,2);
INSERT INTO user_authorities (user_id, rol_id) VALUES (2,2);