DROP DATABASE IF EXISTS Hotel;

CREATE DATABASE Hotel;

USE Hotel;

CREATE TABLE Room (
	RoomNumber INT PRIMARY KEY NOT NULL,
    RoomType VARCHAR(10) NOT NULL,
    StandardOccupancy INT NOT NULL,
    MaximumOccupancy INT NOT NULL,
    BasePrice DECIMAL(5,2) NOT NULL,
    PricePerExtraPerson DECIMAL(2,0),
    ADAaccessible TINYINT(1) NOT NULL
);

CREATE TABLE Amenities (
	AmenityId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    AmenityType VARCHAR(45) NOT NULL,
    ExtraCost DECIMAL(5,2)
);

CREATE TABLE RoomAmenities (
	RoomNumber INT NOT NULL,
    AmenityId INT NOT NULL,
		FOREIGN KEY fk_RoomAmenities_Room (RoomNumber)
			REFERENCES Room (RoomNumber),
		FOREIGN KEY fk_RoomAmenities_Amenities (AmenityId)
			REFERENCES Amenities (AmenityId)
);

CREATE TABLE Reservation (
	ReservationId INT PRIMARY KEY AUTO_INCREMENT,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Adults INT NOT NULL,
    Kids INT NOT NULL,
    TotalPrice DECIMAL(7,2) NOT NULL
);

CREATE TABLE RoomReservation (
	RoomNumber INT NOT NULL,
    ReservationId INT NOT NULL,
		FOREIGN KEY fk_RoomReservation_Room (RoomNumber)
			REFERENCES Room (RoomNumber),
		FOREIGN KEY fk_RoomReservation_Reservation (ReservationId)
			REFERENCES Reservation (ReservationId)
);  

CREATE TABLE Guest (
	GuestId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(45) NOT NULL,
    LastName VARCHAR(45) NOT NULL,
    Street VARCHAR(100) NOT NULL,
    City VARCHAR(45) NOT NULL,
    State CHAR(2) NOT NULL,
    ZipCode CHAR(5) NOT NULL,
    PhoneNumber CHAR(10)
);

CREATE TABLE ReservationGuest (
    ReservationId INT NOT NULL,
    GuestId INT NOT NULL,
		FOREIGN KEY fk_ReservationGuest_Reservation (ReservationId)
			REFERENCES Reservation (ReservationId),
		FOREIGN KEY fk_ReservationGuest_Guest (GuestId)
			REFERENCES Guest (GuestId)
); 