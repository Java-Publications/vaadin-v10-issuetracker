-- DemoData

INSERT INTO login(name, email, password)
VALUES ('Marcus', 'marcus@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('Alejandro', 'alejandro@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('Sven', 'sven@vaadin.com', 'demo');
INSERT INTO login(name, email, password)
VALUES ('John', 'john@vaadin.com', 'demo');


INSERT INTO login_userrole(login_id, userrole_id)
VALUES (
(SELECT id from login u where u.name = 'Marcus'),
(SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.admin'));

INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Marcus'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.developer'));
INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Marcus'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.user'));

INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Alejandro'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.developer'));
INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Alejandro'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.user'));

INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Sven'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.developer'));
INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Sven'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.user'));

INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'John'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.user'));



-- A Deactivated User to test the system
INSERT INTO login(name, email, password, deleted)
VALUES ('Don Joe', 'd.joe@vaadin.com', 'demo', true);
INSERT INTO login_userrole(login_id, userrole_id)
VALUES ((SELECT id from login u where u.name = 'Don Joe'), (SELECT id from userrole r where r.name = 'com.vaadin.tutorial.role.user'));
