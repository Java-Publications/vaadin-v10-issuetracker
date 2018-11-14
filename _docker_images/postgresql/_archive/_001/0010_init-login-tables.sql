SET TIMEZONE = 'Europe/Berlin';
CREATE EXTENSION pgcrypto;


create table T0001_USERS
(
  user_id       serial                   not null,
  login_name    varchar(255)             not null,
  password_hash varchar(255)             not null,
  valid_from    TIMESTAMP WITH TIME ZONE not null,
  valid_until   TIMESTAMP WITH TIME ZONE not null,
  active        boolean                  not null
);

comment on table T0001_USERS
is 'Login and password hashes for the system';

create unique index T0001_USERS_login_name_uindex
  on T0001_USERS (login_name);

create unique index T0001_USERS_user_id_uindex
  on T0001_USERS (user_id);

alter table T0001_USERS
  add constraint T0001_USERS_pk
primary key (user_id);

-- Insert basic users
-- set password hash UPDATE ... SET pswhash = crypt('new password', gen_salt('md5'));
-- check password    SELECT (pswhash = crypt('entered password', pswhash)) AS pswmatch FROM ... ;

INSERT INTO T0001_USERS AS U (user_id, login_name, password_hash, valid_from, valid_until, active)
VALUES (nextval('t0001_users_user_id_seq'),
        'admin',
        crypt('password', gen_salt('bf',8)),
        (SELECT NOW()),
        '2018-12-31 23:59:59',
        true);

INSERT INTO T0001_USERS AS U (user_id, login_name, password_hash, valid_from, valid_until, active)
VALUES (nextval('t0001_users_user_id_seq'),
        'demo',
        crypt('demo', gen_salt('bf',8)),
        (SELECT NOW()),
        '2018-12-31 23:59:59',
        true);

INSERT INTO T0001_USERS AS U (user_id, login_name, password_hash, valid_from, valid_until, active)
VALUES (nextval('t0001_users_user_id_seq'),
        'guest',
        crypt('guest', gen_salt('bf',8)),
        (SELECT NOW()),
        '2018-12-31 23:59:59',
        true);