create table Provincia(
	idProvincia numeric(1) not null,
	nombre varchar(10) not null,
	constraint Provincia_idProvincia_pk primary key (idProvincia)
);

create table Canton(
	idCanton numeric(2) not null,
	nombre varchar(50) not null,
	idProvincia numeric(1) not null,
	constraint Canton_idCanton_idProvincia_pk primary key (idCanton, idProvincia),
	constraint Canton_idProvincia_fk foreign key (idProvincia) references Provincia(idProvincia)
);

create table Distrito(
	idDistrito numeric(3) not null,
	nombre varchar(50) not null,
	idCanton numeric(2) not null,
	idProvincia numeric(1) not null,
	codigo numeric(5) not null,
	constraint Distrito_codigo_pk primary key (codigo),
	constraint Distrito_idDistrito_idCanton_idProvincia_uk unique (idDistrito, idCanton, idProvincia),
	constraint Distrito_idCanton_idProvincia_fk foreign key (idCanton, idProvincia) references Canton(idCanton, idProvincia)
);

create table Usertable(
	idUser int generated always as identity,
	username varchar(50),
	password varchar(50),
	type varchar(13),
	email varchar(100),
	constraint User_idUser_pk primary key (idUser),
	constraint User_type_ck check (type = 'normal' or type = 'administrator'),
	constraint User_username unique (username),
	constraint User_email_un unique (email)
);

create table Restaurant(
	idRestaurant int generated always as identity,
	name varchar(50),
	latitudePos double precision,
	longitudePos double precision,
	foodType varchar(50),
	open time,
	close time,
	price varchar(50),
	codigoDistrito numeric(5),
	constraint Restaurant_idRestaurant_pk primary key (idRestaurant),
	constraint Restaurant_latitudePos_longitudePos_un unique (latitudePos, longitudePos),
	constraint Restaurant_codigoDistrito_fk foreign key (codigoDistrito) references Distrito(codigo)
);

create table Score(
	idScore int generated always as identity,
	totalScore double precision,
	realScore double precision,
	countScore int,
	idRestaurant int,
	constraint Score_idScore_pk primary key (idScore),
	constraint Score_idRestaurant_fk foreign key (idRestaurant) references Restaurant(idRestaurant)
);

create table LineScore(
	idLineScore int generated always as identity,
	idScore int,
	score double precision,
	idUser int,
	constraint LineScore_idLineScore primary key (idLineScore),
	constraint LineScore_idScore_fk foreign key (idScore) references Score(idScore),
	constraint LineScore_idUser_fk foreign key (idUser) references Usertable(idUser)
);

create table Picture(
	idPicture int generated always as identity,
	idRestaurant int,
	picture varchar,
	constraint Picture_idPicture_pk primary key (idPicture),
	constraint Picture_idRestaurant_fk foreign key (idRestaurant) references Restaurant(idRestaurant)
);

create table Comment(
	idComment int generated always as identity,
	content varchar(100),
	dateCreated date,
	timeCreated time,
	idRestaurant int,
	idUser int,
	constraint Comment_idComment_pk primary key (idComment),
	constraint Comment_idRestaurant_fk foreign key (idRestaurant) references Restaurant(idRestaurant),
	constraint Comment_idUser_fk foreign key (idUser) references Usertable(idUser)
);