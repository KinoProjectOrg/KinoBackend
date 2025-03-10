USE kino;
INSERT INTO movie_model (movie_id, title, genre_ids, min_age, runtime, start_date, end_date, overview, poster_path, release_date, status)
VALUES (1, 'Inception', '14,878', 12, 148, '2023-10-01', '2023-10-15', 'A thief who steals corporate secrets...', '/inception.jpg', '2010-07-16', true),
       (2, 'The Dark Knight', '18,28', 12, 152, '2023-10-05', '2023-10-20', 'When the menace known as the Joker emerges...', '/dark_knight.jpg', '2008-07-18', true),
       (3, 'Interstellar', '12,878', 12, 169, '2023-10-10', '2023-10-25', 'A team of explorers travel through a wormhole...', '/interstellar.jpg', '2014-11-07', true);

INSERT INTO screen_model (screen_id, screen_number, max_rows, seats_per_row)
VALUES
    (1, 1, 20, 12),
    (2, 2, 25, 16);

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

-- Call the procedure to insert seats for screen_id = 1
CALL InsertSeatsForScreen1();

-- Insert seats for screen_id = 2 (25 rows, 16 seats per row)
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

-- Call the procedure to insert seats for screen_id = 2
CALL InsertSeatsForScreen2();

INSERT INTO showing_model (showing_id, start_time, end_time, screen_model_screen_id)
VALUES
    (1, '14:00:00', '16:30:00', 1),
    (2, '17:00:00', '19:30:00', 2),
    (3, '20:00:00', '22:30:00', 1);

INSERT INTO customer_model (customer_id, name, email, phone)
VALUES (1, 'John Doe', 'john.doe@example.com', '12345678'),
       (2, 'Jane Smith', 'jane.smith@example.com', '87654321'),
       (3, 'Alice Johnson', 'alice.johnson@example.com', '55555555');

INSERT INTO reservation_model (reservation_id, showing_id, customer_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3);

INSERT INTO seat_reservation (reservation_id, seat_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 6),
    (2, 7);