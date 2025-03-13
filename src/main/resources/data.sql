USE kino;

-- Clear existing data (preserving movies)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE seat_reservation;
TRUNCATE TABLE reservation_model;
TRUNCATE TABLE showing_model;
TRUNCATE TABLE customer_model;
TRUNCATE TABLE seat_model;
TRUNCATE TABLE screen_model;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen1;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen2;
DROP PROCEDURE IF EXISTS CreateShowingsForMovies;
DROP PROCEDURE IF EXISTS CreateReservations;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert screens
INSERT INTO screen_model (screen_id, screen_number, max_rows, seats_per_row)
VALUES
    (1, 1, 20, 12), -- Large screen
    (2, 2, 25, 16); -- Premium screen

-- Create a procedure to insert seats for screen 1
DELIMITER //
CREATE PROCEDURE InsertSeatsForScreen1()
BEGIN
    DECLARE seat_row INT DEFAULT 1;
    DECLARE seat INT DEFAULT 1;

    WHILE seat_row <= 20 DO
        WHILE seat <= 12 DO
            INSERT INTO seat_model (seat_no, seat_row, is_reserved, screen_id)
            VALUES (seat, seat_row, false, 1);

            SET seat = seat + 1;
END WHILE;

        SET seat = 1;
        SET seat_row = seat_row + 1;
END WHILE;
END //
DELIMITER ;

-- Create a procedure to insert seats for screen 2
DELIMITER //
CREATE PROCEDURE InsertSeatsForScreen2()
BEGIN
    DECLARE seat_row INT DEFAULT 1;
    DECLARE seat INT DEFAULT 1;

    WHILE seat_row <= 25 DO
        WHILE seat <= 16 DO
            INSERT INTO seat_model (seat_no, seat_row, is_reserved, screen_id)
            VALUES (seat, seat_row, false, 2);

            SET seat = seat + 1;
END WHILE;

        SET seat = 1;
        SET seat_row = seat_row + 1;
END WHILE;
END //
DELIMITER ;

-- Call the procedures to insert seats for both screens
CALL InsertSeatsForScreen1();
CALL InsertSeatsForScreen2();

-- Insert customers
INSERT INTO customer_model (username, password)
VALUES
    ('john.doe@example.com', '1234'),
    ('jane.doe@example.com', '1234'),
    ('alice.johnson@example.com', '1234'),
    ('bob.smith@example.com', '1234'),
    ('emma.wilson@example.com', '1234');

-- Create a procedure to create showings for all movies
DELIMITER //
CREATE PROCEDURE CreateShowingsForMovies()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE movie_id_var INT;
    DECLARE release_date_var DATE;

    -- Cursor to fetch all movie IDs and release dates
    DECLARE movie_cursor CURSOR FOR
SELECT movie_id, release_date FROM movie_model;

-- Handler for when no more rows to fetch
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN movie_cursor;

read_loop: LOOP
        FETCH movie_cursor INTO movie_id_var, release_date_var;

        IF done THEN
            LEAVE read_loop;
END IF;

        -- Create 3 showings for each movie
        -- First showing: 12:30 on release date, screen based on movie_id
INSERT INTO showing_model (start_time, date, screen_model_screen_id, movie_id)
VALUES ('12:30:00', release_date_var, IF(MOD(movie_id_var, 2) = 0, 2, 1), movie_id_var);

-- Second showing: 15:30 on day after release date
INSERT INTO showing_model (start_time, date, screen_model_screen_id, movie_id)
VALUES ('15:30:00', DATE_ADD(release_date_var, INTERVAL 1 DAY), IF(MOD(movie_id_var, 2) = 0, 2, 1), movie_id_var);

-- Third showing: 18:30 two days after release date
INSERT INTO showing_model (start_time, date, screen_model_screen_id, movie_id)
VALUES ('18:30:00', DATE_ADD(release_date_var, INTERVAL 2 DAY), IF(MOD(movie_id_var, 2) = 0, 2, 1), movie_id_var);
END LOOP;

CLOSE movie_cursor;
END //
DELIMITER ;

-- Create a procedure to create reservations
DELIMITER //
CREATE PROCEDURE CreateReservations()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE showing_id_var INT;
    DECLARE customer_id_var INT;
    DECLARE reservation_id_var INT;
    DECLARE seat_id_var INT;
    DECLARE seat_count INT;
    DECLARE i INT;

    -- Cursor to fetch all showing IDs
    DECLARE showing_cursor CURSOR FOR
SELECT showing_id FROM showing_model LIMIT 15; -- Limit to first 15 showings

-- Handler for when no more rows to fetch
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN showing_cursor;

read_loop: LOOP
        FETCH showing_cursor INTO showing_id_var;

        IF done THEN
            LEAVE read_loop;
END IF;

        -- Select a random customer (1-5)
        SET customer_id_var = FLOOR(1 + RAND() * 5);

        -- Create a reservation
INSERT INTO reservation_model (showing_id, customer_id)
VALUES (showing_id_var, customer_id_var);

-- Get the inserted reservation ID
SET reservation_id_var = LAST_INSERT_ID();

        -- Determine how many seats to reserve (1-4)
        SET seat_count = FLOOR(1 + RAND() * 4);

        -- Reserve random seats
        SET i = 1;
        WHILE i <= seat_count DO
            -- Get a random seat ID for the screen of this showing
SELECT s.seat_id INTO seat_id_var
FROM seat_model s
         JOIN showing_model sh ON s.screen_id = sh.screen_model_screen_id
WHERE sh.showing_id = showing_id_var
  AND s.seat_id NOT IN (
    SELECT sr.seat_id
    FROM seat_reservation sr
             JOIN reservation_model r ON sr.reservation_id = r.reservation_id
    WHERE r.showing_id = showing_id_var
)
ORDER BY RAND()
    LIMIT 1;

-- Insert into seat_reservation if a seat was found
IF seat_id_var IS NOT NULL THEN
                INSERT INTO seat_reservation (reservation_id, seat_id)
                VALUES (reservation_id_var, seat_id_var);

                -- Update the seat as reserved
UPDATE seat_model SET is_reserved = true WHERE seat_id = seat_id_var;

SET i = i + 1;
ELSE
                -- No more available seats
                LEAVE read_loop;
END IF;
END WHILE;
END LOOP;

CLOSE showing_cursor;
END //
DELIMITER ;

-- Call the procedure to create showings for all movies
CALL CreateShowingsForMovies();

-- Call the procedure to create reservations
CALL CreateReservations();

-- Drop the procedures as they're no longer needed
DROP PROCEDURE IF EXISTS InsertSeatsForScreen1;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen2;
DROP PROCEDURE IF EXISTS CreateShowingsForMovies;
DROP PROCEDURE IF EXISTS CreateReservations;
