-- ----------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('superadmin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'EcQOrvgyeLxlG/n/7Ul2eA==', '7QTj41tvPJi/S4dmwuBoWQ==', true, '2012-12-12');
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'EcQOrvgyeLxlG/n/7Ul2eA==', '7QTj41tvPJi/S4dmwuBoWQ==', true, '2012-12-12');
insert into user_table (email, password, first_name, last_name, enabled, created_date)
values ('doctor@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'EcQOrvgyeLxlG/n/7Ul2eA==', '7QTj41tvPJi/S4dmwuBoWQ==', true, '2012-12-12');

-- ----------------------------ROLES------------------------------
insert into role (name) values ('SUPER_ADMIN');
insert into role (name) values ('ADMIN');
insert into role (name) values ('DOCTOR');

-- ----------------------------SUPER ADMIN PRIVILEGES------------------------------
insert into privilege (name) values ('READ_USERS');
insert into privilege (name) values ('SAVE_USERS');
insert into privilege (name) values ('DELETE_USERS');
insert into privilege (name) values ('READ_CONFIGURATION');
insert into privilege (name) values ('SAVE_CONFIGURATION');
insert into privilege (name) values ('READ_CERTIFICATES');
insert into privilege (name) values ('SAVE_CERTIFICATES');
insert into privilege (name) values ('REVOKE_CERTIFICATES');

-- ----------------------------ADMIN PRIVILEGES------------------------------
insert into privilege (name) values ('READ_LOGS');
insert into privilege (name) values ('READ_LOG_ALARMS');
insert into privilege (name) values ('SAVE_LOG_ALARMS');
insert into privilege (name) values ('READ_REPORT');
insert into privilege (name) values ('REQUEST_CERTIFICATES');

-- ----------------------------DOCTOR PRIVILEGES------------------------------
insert into privilege (name) values ('READ_PATIENTS');
insert into privilege (name) values ('SAVE_PATIENTS');
insert into privilege (name) values ('DELETE_PATIENTS');
insert into privilege (name) values ('READ_MESSAGES');
insert into privilege (name) values ('READ_MESSAGE_ALARMS');
insert into privilege (name) values ('SAVE_MESSAGE_ALARMS');

-- ----------------------------ADMIN DOCTOR PRIVILEGES------------------------------
insert into privilege (name) values ('READ_ALARMS');
insert into privilege (name) values ('DELETE_ALARMS');

-- ----------------------------USER ROLE------------------------------
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 2);
insert into user_role (user_id, role_id) values (3, 3);

-- ----------------------------SUPER ADMIN ROLE PRIVILEGE------------------------------
insert into role_privilege (role_id, privilege_id) values (1, 1);
insert into role_privilege (role_id, privilege_id) values (1, 2);
insert into role_privilege (role_id, privilege_id) values (1, 3);
insert into role_privilege (role_id, privilege_id) values (1, 4);
insert into role_privilege (role_id, privilege_id) values (1, 5);
insert into role_privilege (role_id, privilege_id) values (1, 6);
insert into role_privilege (role_id, privilege_id) values (1, 7);
insert into role_privilege (role_id, privilege_id) values (1, 8);

-- ----------------------------ADMIN ROLE PRIVILEGE------------------------------
insert into role_privilege (role_id, privilege_id) values (2, 9);
insert into role_privilege (role_id, privilege_id) values (2, 10);
insert into role_privilege (role_id, privilege_id) values (2, 11);
insert into role_privilege (role_id, privilege_id) values (2, 12);
insert into role_privilege (role_id, privilege_id) values (2, 13);
insert into role_privilege (role_id, privilege_id) values (2, 20);
insert into role_privilege (role_id, privilege_id) values (2, 21);

-- ----------------------------DOCTOR ROLE PRIVILEGE------------------------------
insert into role_privilege (role_id, privilege_id) values (3, 14);
insert into role_privilege (role_id, privilege_id) values (3, 15);
insert into role_privilege (role_id, privilege_id) values (3, 16);
insert into role_privilege (role_id, privilege_id) values (3, 17);
insert into role_privilege (role_id, privilege_id) values (3, 18);
insert into role_privilege (role_id, privilege_id) values (3, 19);
insert into role_privilege (role_id, privilege_id) values (3, 20);
insert into role_privilege (role_id, privilege_id) values (3, 21);
