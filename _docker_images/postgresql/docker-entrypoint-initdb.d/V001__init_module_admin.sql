CREATE TABLE userrole
(
  id      serial       NOT NULL,
  name    varchar(255) NOT NULL,
  deleted boolean DEFAULT FALSE,
  PRIMARY KEY (id)
);

comment on table userrole
  is 'Roles that are used for permissions';

CREATE TABLE login
(
  id       serial              NOT NULL,
  email    varchar(255)        NOT NULL,
  name     varchar(255) UNIQUE NOT NULL,
  password varchar(255)        NOT NULL,
  deleted  boolean DEFAULT FALSE,
  PRIMARY KEY (id)
);

comment on table login
  is 'Users/Logins that are able to login into the webapp';

create index login_email_index
  on login (email);




CREATE TABLE login_roles
(
  login_id    integer NOT NULL,
  userrole_id integer NOT NULL,
  PRIMARY KEY (login_id, userrole_id),
  FOREIGN KEY (login_id) REFERENCES login (id),
  FOREIGN KEY (userrole_id) REFERENCES userrole (id)
);

comment on table login_roles
  is 'A Users/Login can have 1..n UserRoles';


CREATE VIEW v_active_logins AS
SELECT *
FROM login l
WHERE l.deleted = false;

comment on view v_active_logins
  is 'Active Logins, that are able to log into the app';


-- Default Data
INSERT INTO userrole(name)
VALUES ('ADMIN');
INSERT INTO userrole(name)
VALUES ('DEVELOPER');
INSERT INTO userrole(name)
VALUES ('USER');






-- DemoData

INSERT INTO login(name, email, password)
VALUES ('Marcus', 'marcus@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('Alejandro', 'alejandro@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('Sven', 'sven@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('John', 'john@vaadin.com', 'demo');


INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Marcus'), (SELECT id from userrole r where r.name = 'ADMIN'));
INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Marcus'), (SELECT id from userrole r where r.name = 'DEVELOPER'));
INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Marcus'), (SELECT id from userrole r where r.name = 'USER'));

INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Alejandro'), (SELECT id from userrole r where r.name = 'DEVELOPER'));
INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Alejandro'), (SELECT id from userrole r where r.name = 'USER'));

INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Sven'), (SELECT id from userrole r where r.name = 'DEVELOPER'));
INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Sven'), (SELECT id from userrole r where r.name = 'USER'));

INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'John'), (SELECT id from userrole r where r.name = 'USER'));



-- A Deactivated User to test the system
INSERT INTO login(name, email, password, deleted)
VALUES ('Don Joe', 'd.joe@vaadin.com', 'demo', true);
INSERT INTO login_roles(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Don Joe'), (SELECT id from userrole r where r.name = 'USER'));
