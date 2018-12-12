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

-- Demo Data

INSERT INTO project(name, deleted)
VALUES ('Picture Database', false);
-- with Role Admin - should be able to read and write and delete
INSERT INTO project_members(project_id, members_id)
VALUES ((SELECT p.id from project p where p.name = 'Picture Database'),
        (SELECT l.id from login l where l.name = 'Marcus'));
-- with Role Developer - should be able to read and write
INSERT INTO project_members(project_id, members_id)
VALUES ((SELECT p.id from project p where p.name = 'Picture Database'),
        (SELECT l.id from login l where l.name = 'Sven'));
-- with Role User - should be able to read
INSERT INTO project_members(project_id, members_id)
VALUES ((SELECT p.id from project p where p.name = 'Picture Database'),
        (SELECT l.id from login l where l.name = 'John'));

INSERT INTO issue(description, title, date, status_id, reporter_id, owner_id, project_id)
VALUES ('a description for 001',
        'title 001', current_date,
        (select s.id from issuestatus s where s.order_nr = 5),
        (select r.id from login r where r.name = 'Marcus'),
        (select o.id from login o where o.name = 'Marcus'),
        (select p.id from project p where p.name = 'Picture Database'));

INSERT INTO project(name, deleted)
VALUES ('Personal Project SRU', false);
