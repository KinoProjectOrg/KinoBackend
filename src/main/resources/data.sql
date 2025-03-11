USE kino;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE seat_reservation;
TRUNCATE TABLE reservation_model;
TRUNCATE TABLE showing_model;
TRUNCATE TABLE customer_model;
TRUNCATE TABLE seat_model;
TRUNCATE TABLE screen_model;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen1;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen2;
SET FOREIGN_KEY_CHECKS = 1;


-- Insert screens
INSERT INTO screen_model (screen_id, screen_number, max_rows, seats_per_row)
VALUES
    (1, 1, 20, 12), -- Large screen
    (2, 2, 25, 16), -- Premium screen
    (3, 3, 15, 10); -- Small screen

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

-- Create a procedure to insert seats for screen 3
DELIMITER //


-- Call the procedures to insert seats for all screens
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

-- Insert showings WITHOUT specifying showing_id (let JPA generate them)
INSERT INTO showing_model (start_time, date, screen_model_screen_id, movie_id)
VALUES
    -- Kraven the Hunter showings
    ('12:30:00', '2024-12-11', 1, 539972),  -- Opening day
    ('15:30:00', '2024-12-11', 1, 539972),
    ('18:30:00', '2024-12-11', 1, 539972),
    ('12:00:00', '2024-12-13', 1, 539972),  -- First Friday

    -- Gladiator II showings
    ('12:00:00', '2024-11-13', 2, 558449),  -- Opening day
    ('15:30:00', '2024-11-13', 2, 558449),
    ('11:30:00', '2024-11-28', 1, 558449),  -- Thanksgiving

    -- Mickey 17 showings
    ('13:00:00', '2025-02-28', 1, 696506),  -- Opening day
    ('12:30:00', '2025-03-11', 3, 696506),  -- Today
    ('16:00:00', '2025-03-11', 3, 696506),  -- Today
    ('19:30:00', '2025-03-11', 3, 696506);  -- Today

-- Drop the procedures as they're no longer needed
DROP PROCEDURE IF EXISTS InsertSeatsForScreen1;
DROP PROCEDURE IF EXISTS InsertSeatsForScreen2;