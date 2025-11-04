INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (1, 'Conference A', 10, TRUE, TRUE, TRUE, 'Main Building');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (2, 'Conference B', 8, FALSE, TRUE, FALSE, 'Annex');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (3, 'Small Meeting', 4, FALSE, FALSE, TRUE, 'Main Building');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (4, 'Large Hall', 50, TRUE, TRUE, TRUE, 'Auditorium');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (5, 'Board Room', 12, TRUE, TRUE, FALSE, 'Main Building');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (6, 'Training Room 1', 20, TRUE, FALSE, TRUE, 'Annex');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (7, 'Training Room 2', 25, TRUE, TRUE, TRUE, 'Annex');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (8, 'Huddle Space', 6, FALSE, TRUE, TRUE, 'Main Building');
INSERT OR IGNORE INTO room (id, name, capacity, has_projector, has_whiteboard, has_windows, building)
VALUES (10, 'Creative Lab', 30, TRUE, TRUE, TRUE, 'Innovation Center');

INSERT OR IGNORE INTO users (id, username, password, first_name)
VALUES (1, 'conthom', '$2a$10$hneRb3eeD/ZrHIqPX9ixWe0sAHq.pB8uN5GYgrb5JHNVbzv6K59Ju', 'Connor');
INSERT OR IGNORE INTO users (id, username, password, first_name)
VALUES (2, 'admin', '$2a$10$.SfjDdzGUOV1f6Hxi8/bdewwR2upRhSmbOfY9PbC81a51eCiytB1e', 'Admin');