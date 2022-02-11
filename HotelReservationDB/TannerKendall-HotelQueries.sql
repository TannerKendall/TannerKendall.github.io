USE hotel;

-- Query 1, Write a query that returns a list of reservations that end in July 2023, including the name of the guest, the room number(s), and the reservation dates.

SELECT
	Guest.FirstName,
    Guest.LastName,
    Room.RoomNumber,
    Reservation.StartDate,
    Reservation.EndDate
FROM Guest
INNER JOIN ReservationGuest ON Guest.GuestId = ReservationGuest.GuestId
INNER JOIN Reservation ON ReservationGuest.ReservationId = Reservation.ReservationId
INNER JOIN RoomReservation ON Reservation.ReservationId = RoomReservation.ReservationId
INNER JOIN Room ON RoomReservation.RoomNumber = Room.RoomNumber
WHERE EndDate BETWEEN '2023-07-01' AND '2023-07-31';

-- 4 rows
-- ----------------------------------------------------------------------------------

-- Query 2, Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the guest's name, the room number, and the dates of the reservation.

SELECT
	Guest.FirstName,
    Guest.LastName,
    Room.RoomNumber,
    Reservation.StartDate,
    Reservation.EndDate
FROM Room
INNER JOIN RoomAmenities ON Room.RoomNumber = RoomAmenities.RoomNumber
INNER JOIN RoomReservation ON Room.RoomNumber = RoomReservation.RoomNumber
INNER JOIN Reservation ON RoomReservation.ReservationId = Reservation.ReservationId
INNER JOIN ReservationGuest ON Reservation.ReservationId = ReservationGuest.ReservationId
INNER JOIN Guest ON ReservationGuest.GuestId = Guest.GuestId
WHERE AmenityId = 4;

-- 11 rows
-- ----------------------------------------------------------------------------------

-- Query 3, Write a query that returns all the rooms reserved for a specific guest, including the guest's name, 
-- the room(s) reserved, the starting date of the reservation, and how many people were included in the reservation. (Choose a guest's name from the existing data.)

SELECT
	Guest.FirstName,
    Guest.LastName,
    Room.RoomNumber,
    Reservation.StartDate,
    Reservation.EndDate,
    Reservation.Adults + Reservation.Kids AS TotalGuests
FROM Guest
INNER JOIN ReservationGuest ON Guest.GuestId = ReservationGuest.GuestId
INNER JOIN Reservation ON ReservationGuest.ReservationId = Reservation.ReservationId
INNER JOIN RoomReservation ON Reservation.ReservationId = RoomReservation.ReservationId
INNER JOIN Room ON RoomReservation.RoomNumber = Room.RoomNumber
WHERE Guest.GuestId = 1;

-- 2 rows
-- ----------------------------------------------------------------------------------

-- Query 4, Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation.
-- The results should include all rooms, whether or not there is a reservation associated with the room.

SELECT
	Room.RoomNumber,
    Reservation.ReservationId,
    CASE
		WHEN (((Room.RoomNumber BETWEEN 201 AND 204) OR (Room.RoomNumber BETWEEN 301 AND 304)) AND Reservation.Adults <= 2)
			THEN ((Room.BasePrice) * DATEDIFF(Reservation.EndDate, Reservation.StartDate))
		WHEN (((Room.RoomNumber BETWEEN 201 AND 204) OR (Room.RoomNumber BETWEEN 301 AND 304)) AND Reservation.Adults > 2)
			THEN ((Room.BasePrice + ((Reservation.Adults - Room.StandardOccupancy) * 10) * DATEDIFF(Reservation.EndDate, Reservation.StartDate)))
		WHEN ((Room.RoomNumber BETWEEN 205 AND 208) OR (Room.RoomNumber BETWEEN 305 AND 308))
			THEN ((Room.BasePrice) * DATEDIFF(Reservation.EndDate, Reservation.StartDate))
		WHEN (((Room.RoomNumber BETWEEN 401 AND 402)) AND Reservation.Adults <= 3)
			THEN ((Room.BasePrice) * DATEDIFF(Reservation.EndDate, Reservation.StartDate))
		WHEN (((Room.RoomNumber BETWEEN 401 AND 402)) AND Reservation.Adults > 3)
			THEN ((Room.BasePrice + ((Reservation.Adults - Room.StandardOccupancy) * 20) * DATEDIFF(Reservation.EndDate, Reservation.StartDate)))
	END AS Total
FROM Guest
RIGHT OUTER JOIN ReservationGuest ON Guest.GuestId = ReservationGuest.GuestId
RIGHT OUTER JOIN Reservation ON ReservationGuest.ReservationId = Reservation.ReservationId
RIGHT OUTER JOIN RoomReservation ON Reservation.ReservationId = RoomReservation.ReservationId
RIGHT OUTER JOIN Room ON RoomReservation.RoomNumber = Room.RoomNumber
ORDER BY Reservation.ReservationId;

-- 26 rows

-- ----------------------------------------------------------------------------------

-- Query 5, Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.

SELECT
	Room.RoomNumber
FROM Reservation
INNER JOIN RoomReservation ON Reservation.ReservationId = RoomReservation.ReservationId
INNER JOIN Room ON RoomReservation.RoomNumber = Room.RoomNumber
WHERE (Reservation.Adults + Reservation.Kids) > 2
	AND ((Reservation.StartDate BETWEEN '2023-04-01' AND '2023-04-30') OR (Reservation.EndDate BETWEEN '2023-04-01' AND '2023-04-30'));
    
-- 0 rows
-- ----------------------------------------------------------------------------------

-- Query 6, Write a query that returns a list of all guest names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations and then by the guest's name.

SELECT
	Guest.FirstName,
    Guest.LastName,
    COUNT(ReservationGuest.GuestId) AS TotalReservations
FROM Reservation
INNER JOIN ReservationGuest ON Reservation.ReservationId = ReservationGuest.ReservationId
INNER JOIN Guest ON ReservationGuest.GuestId = Guest.GuestId
GROUP BY Guest.FirstName
ORDER BY TotalReservations DESC, Guest.LastName;

-- 11 rows
-- ----------------------------------------------------------------------------------

-- Query 7, Write a query that displays the name, address, and phone number of a guest based on their phone number. (Choose a phone number from the existing data.)

SELECT
	Guest.FirstName,
    Guest.LastName,
    Guest.Street,
    Guest.City,
    Guest.State,
    Guest.ZipCode,
    Guest.PhoneNumber
FROM Guest
WHERE Guest.PhoneNumber = '3308675309'

-- 1 row