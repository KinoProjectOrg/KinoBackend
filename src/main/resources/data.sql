USE kino;
INSERT INTO movie_model (movie_id, title, genre_ids, min_age, runtime, start_date, end_date, overview, poster_path, release_date, status)
VALUES (1, 'Inception', '1', 12, 148, '2023-10-01', '2023-10-15', 'A thief who steals corporate secrets...', 'https://image.tmdb.org/t/p/original/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg', '2010-07-16', true),
       (2, 'The Dark Knight', '2', 12, 152, '2023-10-05', '2023-10-20', 'When the menace known as the Joker emerges...', 'https://static.posters.cz/image/1300/plakater/the-dark-knight-trilogy-batman-i198201.jpg', '2008-07-18', true),
       (3, 'Interstellar', '3', 12, 169, '2023-10-10', '2023-10-25', 'A team of explorers travel through a wormhole...', 'https://static.posters.cz/image/1300/plakater/interstellar-one-sheet-i23157.jpg', '2014-11-07', true);

INSERT INTO screen_model (screen_id, screen_number, max_rows, seats_per_row)
VALUES
    (1, 1, 20, 12),
    (2, 2, 25, 16);

DROP PROCEDURE IF EXISTS InsertSeatsForScreen1;

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

DROP PROCEDURE IF EXISTS InsertSeatsForScreen2;

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

INSERT INTO showing_model (showing_id, start_time, date, screen_id, movie_id)
VALUES
    (1, '14:00:00', '2025-03-11', 1, 1),
    (2, '17:30:00', '2025-03-11', 2, 1),
    (3, '20:45:00', '2025-03-11', 1, 2),
    (4, '15:15:00', '2025-03-11', 2, 2),
    (5, '18:00:00', '2025-03-11', 1, 3),
    (6, '22:30:00', '2025-03-11', 2, 3),

    (7, '13:30:00', '2025-03-12', 1, 1),
    (8, '16:45:00', '2025-03-12', 2, 1),
    (9, '19:00:00', '2025-03-12', 1, 2),
    (10, '14:15:00', '2025-03-12', 2, 2),
    (11, '17:30:00', '2025-03-12', 1, 3),
    (12, '21:45:00', '2025-03-12', 2, 3),

    (13, '12:00:00', '2025-03-13', 1, 1),
    (14, '15:30:00', '2025-03-13', 2, 1),
    (15, '19:15:00', '2025-03-13', 1, 2),
    (16, '14:45:00', '2025-03-13', 2, 2),
    (17, '18:30:00', '2025-03-13', 1, 3),
    (18, '22:00:00', '2025-03-13', 2, 3),

    (19, '11:30:00', '2025-03-14', 1, 1),
    (20, '14:00:00', '2025-03-14', 2, 1),
    (21, '17:00:00', '2025-03-14', 1, 2),
    (22, '15:45:00', '2025-03-14', 2, 2),
    (23, '19:30:00', '2025-03-14', 1, 3),
    (24, '23:00:00', '2025-03-14', 2, 3),

    (25, '10:30:00', '2025-03-15', 1, 1),
    (26, '13:45:00', '2025-03-15', 2, 1),
    (27, '16:30:00', '2025-03-15', 1, 2),
    (28, '12:15:00', '2025-03-15', 2, 2),
    (29, '15:45:00', '2025-03-15', 1, 3),
    (30, '20:00:00', '2025-03-15', 2, 3),

    (31, '11:00:00', '2025-03-16', 1, 1),
    (32, '14:30:00', '2025-03-16', 2, 1),
    (33, '18:15:00', '2025-03-16', 1, 2),
    (34, '13:00:00', '2025-03-16', 2, 2),
    (35, '16:45:00', '2025-03-16', 1, 3),
    (36, '21:30:00', '2025-03-16', 2, 3);

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

INSERT INTO genre_model (genre_id, genre_name)
VALUES
    (1, "Horror"),
    (2, "Action"),
    (3, "Comedy");

INSERT INTO movie_genre (movie_id, genre_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2);