DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (userid, dateTime, description, calories) VALUES
  (100000, '2015-05-01 10:00', 'завтрак', 500),
  (100000, '2015-05-01 14:00', 'обед', 1000),
  (100000, '2015-05-01 20:00', 'ужин', 500),
  (100000, '2015-05-02 10:00', 'завтрак', 500),
  (100001, '2015-05-01 10:00', 'завтрак', 500),
  (100001, '2015-05-01 14:00', 'обед', 1000),
  (100001, '2015-05-01 20:00', 'ужин', 600),
  (100001, '2015-05-02 20:00', 'ужин', 600);
