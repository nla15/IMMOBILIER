create database if not exists immodb;
use immodb;

create table if not exists Region (
	id int(11) not null AUTO_INCREMENT,
	nomRegion varchar(100) not null,
	
	primary key(id)
) engine=innodb;

create table if not exists Adresse (
	id int(11) not null AUTO_INCREMENT,
	libelle varchar(300) not null,
	codePostal varchar(5) not null,
	ville varchar(100) not null,
	regionAdr int(11),
	
	foreign key (regionAdr) references Region(id),
	primary key(id)
) engine=innodb;

create table if not exists UserLogin(
	id int(11) not null AUTO_INCREMENT,
	mail varchar(150) not null,
	mdp varchar(50) not null,
	
	primary key(id )
) engine=innodb;

create table if not exists Utilisateur (
	id int(11) not null AUTO_INCREMENT,
	admin boolean not null default 0,
	nom varchar(50) not null,
	prenom varchar(50) not null,
	mobile varchar(10),
	adressePostale int(11) not null,
	idRefUserLogin int(11) not null,
	
	foreign key (adressePostale) references Adresse(id),
	foreign key (idRefUserLogin) references UserLogin(id),
	primary key(id)
) engine=innodb;


create table if not exists Annonce (
	id int(11) not null AUTO_INCREMENT,
	designation varchar(50) not null,
	prix int(12) not null,
	surface int(12) not null,
	description varchar(500),
	adresseAnn int(11),
	idRefUser int(11),
	
	foreign key (idRefUser) references Utilisateur(id),
	foreign key (adresseAnn) references Adresse(id),
	primary key(id)
) engine=innodb;

create table if not exists Image (
	id int(11) not null AUTO_INCREMENT,
	url varchar(300) not null,
	annonceImg int(11) not null,
	
	foreign key (annonceImg) references Annonce(id),
	primary key(id)
) engine=innodb;


insert into Region values (0, 'Aquitaine');
insert into Region values (0, 'Limousin');

insert into Adresse values(0, '340 cours de la libération', '33400', 'Talence', 1);
insert into Adresse values(0, '10 rue Inconnu', '87000', 'Limoges', 2);

insert into UserLogin values (0,'clovis.nobile@etu.u-bordeaux.fr', 'mdp');
insert into UserLogin values (0,'netty.larisse@etu.u-bordeaux.fr', 'mdp');

insert into Utilisateur values (0, 1, 'Nobile', 'Clovis', '0665308576', 1,1);
insert into Utilisateur values (0, 1, 'Moimeme', 'Moimeme', null ,2,2);

insert into Annonce values(0,'Studio 30m²', 500, 30, 'Tres grand et enorme studio', 1,  2);
insert into Annonce values(0,'Appart 9m²', 500, 30, 'Petit appartement posé au calme', 2,  1);

insert into Image values(0,'/home/tity/Bureau/IMMOBILIER/imageImmo/image1.jpg', 1 );
insert into Image values(0,'/home/tity/Bureau/IMMOBILIER/imageImmo/image2.jpg',2);