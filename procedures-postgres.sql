create or replace function insertarRestaurante(
	_name varchar(50),
	_latitudepos double precision,
	_longitudepos double precision,
	_foodtype varchar(50),
	_open time,
	_close time,
	_price varchar(50),
	_codigodistrito numeric(5)
) returns refcursor
language plpgsql as $$
begin
	insert into Restaurant (Name, Latitudepos, Longitudepos, Foodtype, Open, Close, Price, Codigodistrito)
		values(_name, _latitudepos, _longitudepos, _foodtype, _open, _close, _price, _codigodistrito);
	insert into Score(totalScore, realScore, countScore, idRestaurant)
		values(0, 0, 0, (select idRestaurant from Restaurant where longitudepos = _longitudepos and latitudepos = _latitudepos));
	return (select 1);
exception
	when unique_violation then
		raise exception 'Error: restaurante ya existe';
	when others then
		raise exception 'Error al ejecutar la funcion';
end$$;

create or replace function calificarRestaurante(
	_idRestaurant int,
	_score double precision,
	_idUser int
) returns refcursor
language plpgsql as $$
declare
	_idScore int;
	_yaExisteUser int;
	_previousScore double precision;
begin
	select idScore into _idScore from Score where idRestaurant = _idRestaurant;
	select count(*) into _yaExisteUser from linescore where (idScore = _idScore) and (idUser = _idUser);
	if _yaExisteUser = 0 then
		insert into linescore (idScore, score, idUser) values(_idScore, _score, _idUser);
		update Score set totalScore = (totalScore + _score), realScore = ((totalScore + _score) / (countScore + 1)),
			countScore = (countScore + 1) where idRestaurant = _idRestaurant;
	else
		select score into _previousScore from LineScore where idUser = _idUser;
		update linescore set score = _score where idUser = _idUser;
		update Score set totalScore = (totalScore + _score - _previousScore),
			realScore = ((totalScore + _score - _previousScore) / countScore)
			where idRestaurant = _idRestaurant;
	end if;
	return (select 1);
exception
	when others then
		raise exception 'Error al ejecutar la funcion';	
end$$;
/*
create or replace function eliminarUsuario(
	_username varchar(50)
) returns refcursor
language plpgsql as $$
declare
	_idUser int;
begin
	select idUser into _idUser from Usertable where username = _username;
	delete from Comment where idUser = _idUser;
	delete from 
	return (select 1);
exception
	when others then
		raise exception 'Error al ejecutar la funcion';	
end $$;*/

create or replace function eliminarRestaurante(
	_idRestaurant int
) returns refcursor
language plpgsql as $$
begin
	delete from Comment where idRestaurant = _idRestaurant;
	delete from Picture where idRestaurant = _idRestaurant;
	delete from LineScore l where l.idScore in (select s.idScore from Score s where idRestaurant = _idRestaurant);
	delete from Score where idRestaurant = _idRestaurant;
	delete from Restaurant where idRestaurant = _idRestaurant;
	return (select 1);
exception
	when others then
		raise exception 'Error al ejecutar la funcion';	
end $$;