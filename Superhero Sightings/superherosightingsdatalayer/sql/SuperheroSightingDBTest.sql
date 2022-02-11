drop database if exists SuperheroSightingDBTest;

create database SuperheroSightingDBTest;

use SuperheroSightingDBTest;

create table superhero(
	id int primary key auto_increment,
    isSuperhero boolean not null,
    name varchar(50) not null,
    description varchar(255)
);

create table superPower(
	id int primary key auto_increment,
    name varchar(50) not null,
    description varchar(255)
);

create table hero_superpower(
	superheroId int not null,
    superpowerId int not null,
    primary key pk_superheroSuperpower (superheroId, superpowerId),
    foreign key fk_superheroSuperpower_superheroId (superheroId) references superhero(id),
    foreign key fk_heroSuperpower_superpowerId (superpowerId) references superpower(id)
);

create table organization(
	id int primary key auto_increment,
    name varchar(50) not null,
    isHero boolean not null,
    description varchar(255),
    address varchar(255),
    contact varchar(255)
);

create table hero_organization(
	superheroId int not null,
    organizationId int not null,
    primary key pk_heroOrganization (superheroId, organizationId),
    foreign key fk_heroOrganization_superheroId (superheroId) references superhero(id),
    foreign key fk_heroOrganization_organizationId (organizationId) references organization(id)
);

create table location(
	id int primary key auto_increment,
    name varchar(50) not null,
    latitude decimal(10,8) not null,
    longitude decimal(11,8) not null,
    description varchar(255),
    addressInfo varchar(255)
);

create table sighting(
	id int primary key auto_increment,
    superheroId int not null,
    locationId int not null,
    sightingDate datetime not null,
    -- primary key pk_sighting (superheroId, locationId),
    foreign key fk_sighting_superheroId (superheroId) references superhero(id),
    foreign key fk_sighting_locationId (locationId) references location(id)
);
