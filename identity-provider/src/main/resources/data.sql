-- ----------------------------AUTHORITIES------------------------------
insert into authority_table (name) values ('SUPER_ADMIN');
insert into authority_table (name) values ('ADMIN');
insert into authority_table (name) values ('DOCTOR');

-- ----------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled)
values ('superadmin@gmail.com', '$2a$10$CeM9k3C7C0o2gcY830HSguBd2fWb1nBVbTGu25rdDnz19BlMEShp2', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('doctor@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);

-- ----------------------------USER AUTHORITY------------------------------
insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 2);
insert into user_authority (user_id, authority_id) values (3, 3);