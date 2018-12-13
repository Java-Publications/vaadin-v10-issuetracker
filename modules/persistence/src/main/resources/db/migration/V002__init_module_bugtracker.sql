CREATE TABLE project
(
  id      serial NOT NULL,
  name    varchar(255) DEFAULT NULL,
  deleted boolean      DEFAULT FALSE,
  PRIMARY KEY (id)
);

CREATE TABLE project_members
(
  project_id integer NOT NULL,
  members_id integer NOT NULL,
  PRIMARY KEY (project_id, members_id),
  FOREIGN KEY (members_id) REFERENCES login (id),
  FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE issuestatus
(
  id       serial NOT NULL,
  order_nr int          DEFAULT NULL,
  name     varchar(255) DEFAULT NULL,
  deleted  boolean      DEFAULT FALSE,
  PRIMARY KEY (id)
);


CREATE TABLE issue
(
  id          serial       NOT NULL,
  description text    DEFAULT NULL,
  title       varchar(255) NOT NULL,
  date        date NOT NULL,
  status_id   integer      NOT NULL,
  reporter_id integer      NOT NULL,
  owner_id    integer DEFAULT NULL,
  project_id  integer      NOT NULL,
  deleted     boolean DEFAULT FALSE,
  PRIMARY KEY (id),
  FOREIGN KEY (status_id) REFERENCES issuestatus (id),
  FOREIGN KEY (reporter_id) REFERENCES login (id),
  FOREIGN KEY (owner_id) REFERENCES login (id),
  FOREIGN KEY (project_id) REFERENCES project (id)
);


-- Default Data
-- Have in sync with enum IssueStatus
INSERT INTO issuestatus(order_nr, name)
VALUES (5, 'com.vaadin.tutorial.issuetrackerracker.submitted');
INSERT INTO issuestatus(order_nr, name)
VALUES (10, 'com.vaadin.tutorial.issuetrackerracker.open');
INSERT INTO issuestatus(order_nr, name)
VALUES (15, 'com.vaadin.tutorial.issuetrackerracker.in-progress');
INSERT INTO issuestatus(order_nr, name)
VALUES (20, 'com.vaadin.tutorial.issuetrackerracker.on-hold');
INSERT INTO issuestatus(order_nr, name)
VALUES (25, 'com.vaadin.tutorial.issuetrackerracker.done');
INSERT INTO issuestatus(order_nr, name)
VALUES (30, 'com.vaadin.tutorial.issuetrackerracker.obsolete');
