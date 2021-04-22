--------------------------------------------------EXTENSIONS--------------------------------------------------
insert into extensions(basic_constraints, key_usage) values (true, 1 | 7 | 2);

--------------------------------------------------CERTIFICATES--------------------------------------------------
insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (null, 1, 'root', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (1, 1, 'hospital1', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (2, 1, 'device1', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (1, 1, 'hospital2', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (4, 1, 'device2', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);
