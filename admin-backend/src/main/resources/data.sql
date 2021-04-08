insert into certificate_info (issuer_id, alias, common_name, organization, organization_unit, country, email, template, 
basic_constraints, key_usage, extended_key_usage, start_date, end_date, revoked)
values (null, 'root', 'asd', 'asd', 'asd', 'AS', 'draganaasd@gmail.com', 'SUB_CA', 
true, 'cRLSign, digitalSignature, keyCertSign', null, '2021-04-07', '2031-04-07', false);

insert into certificate_info (issuer_id, alias, common_name, organization, organization_unit, country, email, template, 
basic_constraints, key_usage, extended_key_usage, start_date, end_date, revoked)
values (1, 'hospital1', 'asd', 'asd', 'asd', 'AS', 'draganaasd@gmail.com', 'SUB_CA', 
true, 'cRLSign, digitalSignature, keyCertSign', null, '2021-04-07', '2031-04-07', false);


insert into certificate_info (issuer_id, alias, common_name, organization, organization_unit, country, email, template, 
basic_constraints, key_usage, extended_key_usage, start_date, end_date, revoked)
values (2, 'device1', 'asd', 'asd', 'asd', 'AS', 'draganaasd@gmail.com', 'SUB_CA', 
true, 'cRLSign, digitalSignature, keyCertSign', null, '2021-04-07', '2031-04-07', false);
