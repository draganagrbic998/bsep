-- ----------------------------ROLES------------------------------
insert into role (name) values ('SUPER_ADMIN');
insert into role (name) values ('ADMIN');
insert into role (name) values ('DOCTOR');

-- ----------------------------PRIVILEGES------------------------------
insert into privilege (name) values ('SUPER_ADMIN');
insert into privilege (name) values ('ADMIN');
insert into privilege (name) values ('DOCTOR');

-- ----------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled)
values ('superadmin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('doctor@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);

-- ----------------------------USER ROLE------------------------------
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 2);
insert into user_role (user_id, role_id) values (3, 3);

-- ----------------------------ROLE PRIVILEGE------------------------------
insert into role_privilege (role_id, privilege_id) values (1, 1);
insert into role_privilege (role_id, privilege_id) values (2, 2);
insert into role_privilege (role_id, privilege_id) values (3, 3);
