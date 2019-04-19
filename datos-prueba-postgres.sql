truncate restaurant restart identity cascade;
select insertarRestaurante('Casita alegre', 9.932057, -84.0479028, 'Casera', '07:00:00', '	21:00:00', 'barato', 1151);
select insertarRestaurante('Mansión alegre', 9.9317188, -84.0663671, 'Casera', '11:00:00', '	21:00:00', 'caro', 1151);
select insertarRestaurante('Casa alegre', 9.9333357, -84.0589106, 'Casera', '11:00:00', '	21:00:00', 'medio', 1151);
insert into Usertable (username, password, type) values ('admin', '1234', 'administrator');
insert into Usertable (username, password, type) values ('mauri', '1234', 'normal');
select calificarRestaurante(1, 4.4, 2);