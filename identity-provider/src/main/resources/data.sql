-- ----------------------------ROLES------------------------------
insert into role (name) values ('SUPER_ADMIN');
insert into role (name) values ('ADMIN');
insert into role (name) values ('DOCTOR');

-- ----------------------------PRIVILEGES------------------------------
insert into privilege (name) values ('READ_USERS');
insert into privilege (name) values ('SAVE_USERS');
insert into privilege (name) values ('DELETE_USERS');
insert into privilege (name) values ('READ_CONFIGURATION');
insert into privilege (name) values ('SAVE_CONFIGURATION');
insert into privilege (name) values ('READ_CERTIFICATES');
insert into privilege (name) values ('SAVE_CERTIFICATES');
insert into privilege (name) values ('REVOKE_CERTIFICATES');
insert into privilege (name) values ('ADMIN');
insert into privilege (name) values ('DOCTOR');

-- ----------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('superadmin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true, '2012-12-12');
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true, '2012-12-12');
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('doctor@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true, '2012-12-12');

-- ----------------------------USER ROLE------------------------------
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 2);
insert into user_role (user_id, role_id) values (3, 3);

-- ----------------------------ROLE PRIVILEGE------------------------------
insert into role_privilege (role_id, privilege_id) values (1, 1);
insert into role_privilege (role_id, privilege_id) values (1, 2);
insert into role_privilege (role_id, privilege_id) values (1, 3);
insert into role_privilege (role_id, privilege_id) values (1, 4);
insert into role_privilege (role_id, privilege_id) values (1, 5);
insert into role_privilege (role_id, privilege_id) values (1, 6);
insert into role_privilege (role_id, privilege_id) values (1, 7);
insert into role_privilege (role_id, privilege_id) values (1, 8);

insert into role_privilege (role_id, privilege_id) values (2, 9);
insert into role_privilege (role_id, privilege_id) values (3, 10);
