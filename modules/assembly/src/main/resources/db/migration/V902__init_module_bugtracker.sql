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
