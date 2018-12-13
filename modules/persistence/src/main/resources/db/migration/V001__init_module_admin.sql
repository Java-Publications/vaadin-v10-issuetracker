CREATE TABLE userrole
(
  id      serial       NOT NULL,
  name    varchar(255) NOT NULL UNIQUE ,
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


CREATE TABLE login_userrole
(
  login_id    integer NOT NULL,
  userrole_id integer NOT NULL,
  PRIMARY KEY (login_id, userrole_id),
  FOREIGN KEY (login_id) REFERENCES login (id),
  FOREIGN KEY (userrole_id) REFERENCES userrole (id)
);

comment on table login_userrole
  is 'A Users/Login can have 1..n UserRoles';


CREATE VIEW v_active_logins AS
SELECT *
FROM login l
WHERE l.deleted = false;

comment on view v_active_logins
  is 'Active Logins, that are able to log into the app';

-- Default Data
INSERT INTO userrole(name)
VALUES ('com.vaadin.tutorial.role.admin');
INSERT INTO userrole(name)
VALUES ('com.vaadin.tutorial.role.developer');
INSERT INTO userrole(name)
VALUES ('com.vaadin.tutorial.role.user');

