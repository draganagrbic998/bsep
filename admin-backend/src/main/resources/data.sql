insert into extensions(basic_constraints, key_usage) values (true, 1 | 7 | 2);
insert into extensions(basic_constraints, key_usage) values (true, 1 | 7 | 2);
insert into extensions(basic_constraints, key_usage) values (true, 1 | 7 | 2);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (null, 1, 'root', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (1, 2, 'hospital1', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);

insert into certificate_info (
issuer_id, extensions_id, alias, common_name, organization, organization_unit, 
country, email, template, start_date, end_date, revoked)
values (1, 3, 'device1', 'asd', 'asd', 'asd', 
'AS', 'draganaasd@gmail.com', 'SUB_CA', '2021-04-07', '2031-04-07', false);
